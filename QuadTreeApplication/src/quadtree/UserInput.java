/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;
/**
 *
 * @author tkidder
 */
public class UserInput
{
    private double xLeft, xRight, yLower, yUpper;

    public UserInput() { }
    
    public UserInput(double _xLeft, double _xRight, double _yLower, double _yUpper)
    {
        xLeft = _xLeft;
        xRight = _xRight;
        yLower = _yLower;
        yUpper = _yUpper;
    }
    
    public UserInput(String[] args)
    {
        xLeft = Double.parseDouble(args[0]);
        xRight = Double.parseDouble(args[1]);
        yLower = Double.parseDouble(args[2]);
        yUpper = Double.parseDouble(args[3]);
    }
    
    public double getxLeft()
    {
        return xLeft;
    }

    public void setxLeft(double xLeft)
    {
        this.xLeft = xLeft;
    }

    public double getxRight()
    {
        return xRight;
    }

    public void setxRight(double xRight)
    {
        this.xRight = xRight;
    }

    public double getyLower()
    {
        return yLower;
    }

    public void setyLower(double yLower)
    {
        this.yLower = yLower;
    }

    public double getyUpper()
    {
        return yUpper;
    }

    public void setyUpper(double yUpper)
    {
        this.yUpper = yUpper;
    }
}
