import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class MyRectangle extends MyBoundedShape
{
	public MyRectangle(){ }
	
	public MyRectangle(Color c, int x1, int x2, int y1, int y2,
						Stroke stroke, boolean filled)
	{
		super(c, x1, x2, y1, y2, stroke, filled);
	}

	@Override
	public void draw(Graphics2D g)
	{
		g.setPaint(getPaint());
		
		if (getFilled())
			g.fillRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
		else
			g.drawRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
	}
}
