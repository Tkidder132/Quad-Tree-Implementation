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
public class UserInput
{
    private BigDecimal xLeft, xRight, yLower, yUpper;

    public UserInput() { }
    
    public UserInput(BigDecimal _xLeft, BigDecimal _xRight, BigDecimal _yLower, BigDecimal _yUpper)
    {
        this.xLeft = _xLeft;
        this.xRight = _xRight;
        this.yLower = _yLower;
        this.yUpper = _yUpper;
    }
    
    public BigDecimal getxLeft()
    {
        return xLeft;
    }

    public void setxLeft(BigDecimal xLeft)
    {
        this.xLeft = xLeft;
    }

    public BigDecimal getxRight()
    {
        return xRight;
    }

    public void setxRight(BigDecimal xRight)
    {
        this.xRight = xRight;
    }

    public BigDecimal getyLower()
    {
        return yLower;
    }

    public void setyLower(BigDecimal yLower)
    {
        this.yLower = yLower;
    }

    public BigDecimal getyUpper()
    {
        return yUpper;
    }

    public void setyUpper(BigDecimal yUpper)
    {
        this.yUpper = yUpper;
    }
}
