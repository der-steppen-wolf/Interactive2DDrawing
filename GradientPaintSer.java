import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Point2D;

public class GradientPaintSer extends java.awt.GradientPaint implements java.io.Serializable
{
	private static final long serialVersionUID = -4714511291976324347L;
	private float x1, y1;
	private Point2D point1;
	private float x2, y2;
	private Point2D point2;
	private Color color1, color2;
	private boolean cyclic;
	private int transparency;
	private int BITMARK = GradientPaint.OPAQUE;
	private int OPAQUE = GradientPaint.OPAQUE;
	private int TRANSCULENT = GradientPaint.TRANSLUCENT;
	
	public GradientPaintSer(Point2D pt1, Color color1, Point2D pt2, Color color2, boolean cyclic)
	{
		super(pt1, color1, pt2, color2, cyclic);
		this.point1 = pt1;
		this.color1 = color1;
		this.point2 = pt2;
		this.color2 = color2;
		this.cyclic = cyclic;
		transparency = BITMARK;
		
	}
	
	public GradientPaintSer(float x1, float y1, Color color1, float x2, float y2, Color color2, boolean cyclic)
	{
		super(x1, y1, color1, x2, y2, color2, cyclic);
		this.x1 = x1;
		this.y1 = y1;
		this.color1 = color1;
		this.x2 = x2;
		this.y2 = y2;
		this.color2 = color2;
		this.cyclic = cyclic;
		transparency = BITMARK;
	}
	
	public float getX1()
	{
		return x1;
	}
	
	public void setX1(float x1)
	{
		this.x1 = x1;
	}
	
	public float getY1()
	{
		return y1;
	}
	
	public void setY1(float y1)
	{
		this.y1 = y1;
	}
	
	@Override
	public Point2D getPoint1()
	{
		return point1;
	}
	
	public void setpoint1(Point2D point1)
	{
		this.point1 = point1;
	}
	
	@Override
	public Color getColor1()
	{
		return color1;
	}
	
	public void setColor1(Color color1)
	{
		this.color1 = color1;
	}
	
	public float getX2()
	{
		return x2;
	}
	
	public void setX2(float x2)
	{
		this.x2 = x2;
	}
	
	public float getY2()
	{
		return y2;
	}
	
	public void setY2(float y2)
	{
		this.y2 = y2;
	}
	
	@Override
	public Point2D getPoint2()
	{
		return point2;
	}
	
	public void setpoint2(Point2D point2)
	{
		this.point2 = point2;
	}
	
	@Override
	public Color getColor2()
	{
		return color2;
	}
	
	public void setColor2(Color color2)
	{
		this.color2 = color2;
	}
	
	@Override
	public boolean isCyclic()
	{
		return cyclic;
	}
	
	public void setCyclic(boolean cyclic)
	{
		this.cyclic = cyclic;
	}
	
	@Override
	public int getTransparency()
	{
		return transparency;
	}
	
	public void setTransparency(int transparency)
	{
		this.transparency = transparency;
	}
}
