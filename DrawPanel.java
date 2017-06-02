import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel
{
	private static final long serialVersionUID = 8591048410995990484L;
	private MyShape[] shapes;
	private int shapeCount;
	private int shapeType;
	private MyShape currentShape;
	private Paint currentPaint;
	private Stroke currentStroke;
	private boolean filledShape;
	private JLabel statusLabel;

	public DrawPanel(JLabel label)
	{
		shapes = new MyShape[100];
		statusLabel = label;
		statusLabel.setFont(new Font("Serif", Font.PLAIN, 15));
		shapeCount = 0;
		currentPaint = Color.BLACK;
		currentStroke = new BasicStrokeSer(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		
		setBackground(Color.WHITE);
		
		MouseHandler handler = new MouseHandler();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}
	
	public void setShapes(MyShape[] array)
	{
		shapes = array;
	}
	
	public MyShape[] getShapes()
	{
		return shapes;
	}
	
	public void setShapeCount(int num)
	{
		shapeCount = num;
	}
	
	public int getShapeCount()
	{
		return shapeCount;
	}
	
	public void setShapeType(int shapeType)
	{
		this.shapeType = shapeType;
	}
	
	public void setFilledShape(boolean filledShape)
	{
		this.filledShape = filledShape;
	}
	
	public void setCurrentPaint(Paint currentPaint)
	{
		this.currentPaint = currentPaint;
	}
	
	public void setCurrentStroke(BasicStrokeSer currentStroke)
	{
		this.currentStroke = currentStroke;
	}
	
	public Stroke getCurrentStroke()
	{
		return currentStroke;
	}
	
	public MyShape getCurrentShape()
	{
		return currentShape;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		for (int i = 0; i < shapeCount && shapeCount < shapes.length; )
		{	
			g2d.setPaint(shapes[i].getPaint());
			g2d.setStroke(shapes[i].getStroke());
			shapes[i++].draw(g2d);
		}
		
		if (currentShape != null)
		{
			g2d.setPaint(currentPaint);
			g2d.setStroke(currentStroke);
			currentShape.setPaint(currentPaint);
			currentShape.setStroke(currentStroke);
			currentShape.draw(g2d);
		}
			
	}
	
	public void clearLastShape()
	{
		if (shapeCount > 0)
			shapes[shapeCount--] = null;
		repaint();
	}
	
	public void clearDesign()
	{
		setBackground(Color.WHITE);
		shapeCount = 0;
		repaint();
	}
	
	private class MouseHandler extends MouseAdapter implements MouseMotionListener
	{
		@Override
		public void mousePressed(MouseEvent event)
		{
			if (shapeType == 1)
				currentShape = new MyOval();
			else if(shapeType == 2)
				currentShape = new MyRectangle();
			else
				currentShape = new MyLine();
			
			if (currentShape instanceof MyBoundedShape)
				((MyBoundedShape) currentShape).setFilled(filledShape);
			
			currentShape.setX1(event.getX());
			currentShape.setY1(event.getY());
		}
		
		@Override
		public void mouseDragged(MouseEvent event)
		{
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
			statusLabel.setText(String.format("(%d,%d)",
					event.getX(), event.getY()));
			repaint();
		}
		
		@Override
		public void mouseReleased(MouseEvent event)
		{
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
			shapes[shapeCount++] = currentShape;
			currentShape = null;
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent event)
		{
			statusLabel.setText(String.format("(%d,%d)",
					event.getX(), event.getY()));
		}
		
		@Override
		public void mouseExited(MouseEvent event)
		{
			statusLabel.setText(String.format("(%c,%c)",
					'x', 'y'));
		}
		
	}
}
