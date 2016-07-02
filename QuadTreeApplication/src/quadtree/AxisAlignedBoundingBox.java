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
    DataPoint center;
    BigDecimal halfDimension;
    
    public AxisAlignedBoundingBox(DataPoint _center, BigDecimal _halfDimension)
    {
        this.center = _center;
        this.halfDimension = _halfDimension;
    }
    
    public boolean containsPoint(DataPoint point)
    {
        BigDecimal xMin = this.center.getxCoord().subtract(this.halfDimension);
        BigDecimal xMax = this.center.getxCoord().add(this.halfDimension);
        BigDecimal yMin = this.center.getyCoord().subtract(this.halfDimension);
        BigDecimal yMax = this.center.getyCoord().add(this.halfDimension);
        
        return BigDecimalUtility.BigDecimalGreaterThanOrEqualTo(point.getxCoord(),xMin) &&
               BigDecimalUtility.BigDecimalLessThanOrEqualTo(point.getxCoord(), xMax) &&
               BigDecimalUtility.BigDecimalGreaterThanOrEqualTo(point.getyCoord(), yMin) &&
               BigDecimalUtility.BigDecimalLessThanOrEqualTo(point.getyCoord(), yMax);
    }
    
    public boolean intersectsAABB(AxisAlignedBoundingBox other)
    {
        BigDecimal xMin = this.center.getxCoord().subtract(this.halfDimension);
        BigDecimal xMax = this.center.getxCoord().add(this.halfDimension);
        BigDecimal yMin = this.center.getyCoord().subtract(this.halfDimension);
        BigDecimal yMax = this.center.getyCoord().add(this.halfDimension);
        
        BigDecimal xMinOther = other.center.getxCoord().subtract(other.halfDimension);
        BigDecimal xMaxOther = other.center.getxCoord().add(other.halfDimension);
        BigDecimal yMinOther = other.center.getyCoord().subtract(other.halfDimension);
        BigDecimal yMaxOther = other.center.getyCoord().add(other.halfDimension);
        
        
        //if (xMax < xMinOther) return false; // a is left of b
        if(BigDecimalUtility.BigDecimalLessThan(xMax, xMinOther)) return false;
        
        //if (xMin > xMaxOther) return false; // a is right of b
        if(BigDecimalUtility.BigDecimalGreaterThan(xMin, xMaxOther)) return false;
        
        //if (yMax < yMinOther) return false; // a is above b
        if(BigDecimalUtility.BigDecimalLessThan(yMax, yMinOther)) return false;
        
        //if (yMin > yMaxOther) return false; // a is below b
        if(BigDecimalUtility.BigDecimalGreaterThan(yMin, yMaxOther)) return false;
        
        return true; // boxes overlap
    }
}
