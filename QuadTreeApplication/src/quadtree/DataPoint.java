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
public class DataPoint
{
    private double xCoord, yCoord, Value;
    
    public DataPoint() { }
    
    public DataPoint(double _xCoord, double _yCoord, double _Value)
    {
        this.xCoord = _xCoord;
        this.yCoord = _yCoord;
        this.Value = _Value;
    }

    public double getxCoord()
    {
        return xCoord;
    }

    public void setxCoord(double xCoord)
    {
        this.xCoord = xCoord;
    }

    public double getyCoord()
    {
        return yCoord;
    }

    public void setyCoord(double yCoord)
    {
        this.yCoord = yCoord;
    }

    public double getValue()
    {
        return Value;
    }

    public void setValue(double Value)
    {
        this.Value = Value;
    }
}
