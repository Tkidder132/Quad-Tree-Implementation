/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.math.BigDecimal;

/**
 *
 * @author tkidder
 */
public class AxisAlignedBoundingBox
{
    private final DataPoint center;
    private final BigDecimal halfDimension;
    
    public AxisAlignedBoundingBox(DataPoint _center, BigDecimal _halfDimension)
    {
        this.center = _center;
        this.halfDimension = _halfDimension;
    }
    
    public boolean containsPoint(DataPoint point)
    {
        double xMin = this.center.getxCoord() - this.halfDimension.doubleValue();
        double xMax = this.center.getxCoord() + this.halfDimension.doubleValue();
        double yMin = this.center.getyCoord() - this.halfDimension.doubleValue();
        double yMax = this.center.getyCoord() + this.halfDimension.doubleValue();
        
        return (point.getxCoord() >= xMin) &&
                (point.getxCoord() <= xMax) &&
                (point.getyCoord() >= yMin) &&
                (point.getyCoord() <= yMax);
    }
    
    public boolean intersectsAABB(AxisAlignedBoundingBox other)
    {
        double xMin = this.center.getxCoord() - this.halfDimension.doubleValue();
        double xMax = this.center.getxCoord() + this.halfDimension.doubleValue();
        double yMin = this.center.getyCoord() - this.halfDimension.doubleValue();
        double yMax = this.center.getyCoord() + this.halfDimension.doubleValue();
        
        double xMinOther = other.center.getxCoord() - other.halfDimension.doubleValue();
        double xMaxOther = other.center.getxCoord() + other.halfDimension.doubleValue();
        double yMinOther = other.center.getyCoord() - other.halfDimension.doubleValue();
        double yMaxOther = other.center.getyCoord() + other.halfDimension.doubleValue();
        
        if(xMax < xMinOther) return false;
        if(xMin > xMaxOther) return false;
        if(yMax < yMinOther) return false;
        if(yMin > yMaxOther) return false;
        return true;
    }
    
    public BigDecimal getHalfDimension()
    {
        return this.halfDimension;
    }
    
    public DataPoint getCenter()
    {
        return this.center;
    }
}
