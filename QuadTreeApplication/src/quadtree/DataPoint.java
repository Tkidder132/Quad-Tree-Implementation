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
public class DataPoint
{
    private BigDecimal xCoord, yCoord, Value;
    
    public DataPoint() { }
    
    public DataPoint(BigDecimal _xCoord, BigDecimal _yCoord, BigDecimal _Value)
    {
        this.xCoord = _xCoord;
        this.yCoord = _yCoord;
        this.Value = _Value;
    }

    public BigDecimal getxCoord()
    {
        return xCoord;
    }

    public void setxCoord(BigDecimal xCoord)
    {
        this.xCoord = xCoord;
    }

    public BigDecimal getyCoord()
    {
        return yCoord;
    }

    public void setyCoord(BigDecimal yCoord)
    {
        this.yCoord = yCoord;
    }

    public BigDecimal getValue()
    {
        return Value;
    }

    public void setValue(BigDecimal Value)
    {
        this.Value = Value;
    }
}
