import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public abstract class MyBoundedShape extends MyShape
{
	private boolean filled;
	
	public MyBoundedShape()
	{
		filled = false;
	}
	
	public MyBoundedShape(Paint paint, int x1, int x2, int y1, int y2,
							Stroke stroke, boolean filled)
	{
		super(paint, x1, x2, y1, y2, stroke);
		this.filled = filled;
	}
	
	public int getUpperLeftX()
	{
		return Math.min(getX1(), getX2());
	}
	
	public int getUpperLeftY()
	{
		return Math.min(getY1(), getY2());
	}
	
	public int getWidth()
	{
		return Math.abs(getX1() - getX2());
	}
	
	public int getHeight()
	{
		return Math.abs(getY1() - getY2());
	}

	public boolean getFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public abstract void draw(Graphics2D g);
	
}
