import java.awt.Paint;
import java.awt.Stroke;
import java.io.Serializable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class MyShape implements Serializable
{
	private Paint color;
	private int x1, x2;
	private int y1, y2;
	private Stroke stroke;
	
	public MyShape()
	{
		color = Color.BLACK;
		x1 = 0; x2 = 0;
		y1 = 0; y2 = 0;
		this.stroke = new BasicStroke();
	}
	
	public MyShape(Paint paint, int x1, int x2, int y1, int y2, Stroke stroke)
		throws IllegalArgumentException
	{
		if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0)
			throw new IllegalArgumentException();
		
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = paint;
		this.stroke = stroke;
	}
	
	public void setPaint(Paint c){ color = c; }
	
	public void setX1(int x){ x1 = x; }
	
	public void setX2(int x){ x2 = x; }
	
	public void setY1(int y){ y1 = y; }
	
	public void setY2(int y){ y2 = y; }
	
	public void setStroke(Stroke currentStroke) { this.stroke = currentStroke; }
	
	public Paint getPaint() { return color; }
	
	public int getX1() { return x1; }
	
	public int getX2() { return x2; }
	
	public int getY1() { return y1; }
	
	public int getY2() { return y2; }
	
	public Stroke getStroke() { return stroke; }
	
	public abstract void draw(Graphics2D g);
	
}
