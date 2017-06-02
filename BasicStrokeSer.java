/**
 * This class functions likes the BasicStroke class
 * but follow Beans convention and can be saved by XMLEncoder
 * 
 * @author Coolprosu
 * Jan 27, 2009 1:13:27 AM
 */
public class BasicStrokeSer extends java.awt.BasicStroke implements java.io.Serializable
{
	private static final long serialVersionUID = 7054882020881890687L;
	private float lineWidth, miterLimit, dashArray[], dashPhase;
    private int endCap, lineJoin;
    
	public BasicStrokeSer()
	{
		
	}
	
	public BasicStrokeSer(float width, int cap, int join)
	{
		super(width, cap, join);
		this.lineWidth = width;
		this.endCap = cap;
		this.lineJoin = join;
	}
	
	public BasicStrokeSer(float width, int cap, int join, float miterLimit, float[] dash, float dash_phase)
	{
		super(width, cap, join, miterLimit, dash, dash_phase);
		this.lineWidth = width;
        this.endCap = cap;
        this.lineJoin = join;
        this.miterLimit = miterLimit;
        this.dashArray = dash;
        this.dashPhase = dash_phase;
	}
	     
	@Override
	public float getLineWidth()
	{
		return lineWidth;
	}
	
	public void setLineWidth(float lineWidth)
	{
		this.lineWidth = lineWidth;
	}
	
	@Override
	public float getMiterLimit()
	{
		return miterLimit;
	}
	     
	public void setMiterLimit(float miterLimit)
	{
		this.miterLimit = miterLimit;
	}
	
	@Override
	public float[] getDashArray()
	{
		return dashArray;
	}
	     
	public void setDashArray(float[] dashArray)
	{
		this.dashArray = dashArray;
	}
	
	@Override
	public float getDashPhase()
	{
		return dashPhase;
	}
	     
	public void setDashPhase(float dash_phase)
	{
		this.dashPhase = dash_phase;
	}
	
	@Override
	public int getEndCap()
	{
		return endCap;
	}
	     
	public void setEndCap(int endCap)
	{
		this.endCap = endCap;
	}

	@Override
	public int getLineJoin()
	{
		return lineJoin;
	}
	     
	public void setLineJoin(int lineJoin)
	{
		this.lineJoin = lineJoin;
	}
}
