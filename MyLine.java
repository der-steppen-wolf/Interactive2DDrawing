import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public class MyLine extends MyShape
{
	public MyLine(){ }
	
	public MyLine(Paint paint, int x1, int x2, int y1, int y2, BasicStrokeSer stroke)
	{
		super(paint, x1, x2, y1, y2, stroke);
	}

	@Override
	public void draw(Graphics2D g)
	{
		g.setPaint(getPaint());
		g.drawLine(getX1(), getY1(), getX2(), getY2());
	}
	
}
