/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author tkidder
 */
public class QuadTree
{
    private final int QuadTreeNodeCapacity = 4;
    
    private AxisAlignedBoundingBox boundary;
    
    private ArrayList<DataPoint> points;
    
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;
    
    public QuadTree(ArrayList<DataPoint> dataPoints)
    {
        this.points = new ArrayList<>();
        boundary = createStartBoxFromFileContents(dataPoints);
        createQuadTreeStructure(dataPoints);
    }
    
    public QuadTree(AxisAlignedBoundingBox _boundary)
    {
        this.points = new ArrayList<>();
        this.boundary = _boundary;
    }
    
    public boolean insert(DataPoint p)
    {
        if (!boundary.containsPoint(p))
            return false;
        
        if (points.size() < QuadTreeNodeCapacity)
        {
            points.add(p);
            return true;
        }

        if (northWest == null)
            subdivide();
        
        if (northWest.insert(p)) return true;
        if (northEast.insert(p)) return true;
        if (southWest.insert(p)) return true;
        if (southEast.insert(p)) return true;
        
        return false;
    }
    
    public void subdivide()
    {
        northWest = createNorthWestSubdividedQuadTree();
        northEast = createNorthEastSubdividedQuadTree();
        southWest = createSouthWestSubdividedQuadTree();
        southEast = createSouthEastSubdividedQuadTree();
    }
        
    private QuadTree createNorthWestSubdividedQuadTree()
    {
        return createSubdividedQuadTree(createNorthWestSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox createNorthWestSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(createNorthWestSubdividedCenterPoint(), this.boundary.getHalfDimension().divide(new BigDecimal(2)));
    }
    
    private DataPoint createNorthWestSubdividedCenterPoint()
    {
        double northWestNewCenterX = (boundary.getCenter().getxCoord() - (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue());
        double northWestNewCenterY = (boundary.getCenter().getyCoord() + (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue());
        return new DataPoint(northWestNewCenterX, northWestNewCenterY, 0);
    }
    
    private QuadTree createNorthEastSubdividedQuadTree()
    {
        return createSubdividedQuadTree(createNorthEastSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox createNorthEastSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(createNorthEastSubdividedCenterPoint(), this.boundary.getHalfDimension().divide(new BigDecimal(2)));
    }
    
    private DataPoint createNorthEastSubdividedCenterPoint()
    {
        double northEastNewCenterX = (boundary.getCenter().getxCoord() + (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue());
        double northEastNewCenterY = (boundary.getCenter().getyCoord() + (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue());
        return new DataPoint(northEastNewCenterX, northEastNewCenterY, 0);
    }
    
    private QuadTree createSouthWestSubdividedQuadTree()
    {
        return createSubdividedQuadTree(createSouthWestSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox createSouthWestSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(createSouthWestSubdividedCenterPoint(), this.boundary.getHalfDimension().divide(new BigDecimal(2)));
    }
    
    private DataPoint createSouthWestSubdividedCenterPoint()
    {
        double southWestNewCenterX = (boundary.getCenter().getxCoord() - (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue());
        double southWestNewCenterY = (boundary.getCenter().getyCoord() - (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue());
        return new DataPoint(southWestNewCenterX, southWestNewCenterY, 0);
    }
    
    private QuadTree createSouthEastSubdividedQuadTree()
    {
        return createSubdividedQuadTree(createSouthEastSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox createSouthEastSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(createSouthEastSubdividedCenterPoint(), boundary.getHalfDimension().divide(new BigDecimal(2)));
    }
    
    private DataPoint createSouthEastSubdividedCenterPoint()
    {
        double southEastNewCenterX = boundary.getCenter().getxCoord() + (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue();
        double southEastNewCenterY = boundary.getCenter().getyCoord() - (boundary.getHalfDimension().divide(new BigDecimal(2))).doubleValue();
        return new DataPoint(southEastNewCenterX, southEastNewCenterY, 0);
    }
    
    private QuadTree createSubdividedQuadTree(AxisAlignedBoundingBox boundary)
    {
        return new QuadTree(boundary);
    }
    
    public ArrayList<DataPoint> queryRange(AxisAlignedBoundingBox range)
    {
        ArrayList<DataPoint> pointsInRange = new ArrayList<>();

        if (!boundary.intersectsAABB(range))
        
        for (int i = 0; i < points.size(); i++)
        {
          if (range.containsPoint(points.get(i)))
            pointsInRange.add(points.get(i));
        }

        if(northWest == null)
            return pointsInRange;

        pointsInRange.addAll(northWest.queryRange(range));
        pointsInRange.addAll(northEast.queryRange(range));
        pointsInRange.addAll(southWest.queryRange(range));
        pointsInRange.addAll(southEast.queryRange(range));

        return pointsInRange;
    }
    
    
    private DataPoint getMinValues(ArrayList<DataPoint> fileContents)
    {
        double X = 0;
        double Y = 0;
        for(int i = 0; i < fileContents.size(); i++)
        {
            X = fileContents.get(i).getxCoord() < X ? fileContents.get(i).getxCoord() : X;
            Y = fileContents.get(i).getxCoord() < Y ? fileContents.get(i).getyCoord() : Y;
        }
        double smallestValue = X < Y ? X : Y;
        return new DataPoint(smallestValue - 1, smallestValue - 1, 0);
    }
    
    private DataPoint getMaxValues(ArrayList<DataPoint> fileContents)
    {
        double X = 0;
        double Y = 0;
        for(int i = 0; i < fileContents.size(); i++)
        {
            X = fileContents.get(i).getxCoord() > X ? fileContents.get(i).getxCoord() : X;
            Y = fileContents.get(i).getxCoord() > Y ? fileContents.get(i).getxCoord() : Y;
        }
        double biggestValue = X > Y ? X : Y;
        return new DataPoint(biggestValue + 1, biggestValue + 1, 0);
    }
    
    private DataPoint getCenterValue(DataPoint minValues, DataPoint maxValues)
    {
        return new DataPoint(getCenterXValue(minValues.getxCoord(), maxValues.getxCoord()), getCenterYValue(minValues.getyCoord(), maxValues.getyCoord()), 0);
    }
    
    private double getCenterXValue(double minX, double maxX)
    {
        return (maxX + minX)/2;
    }
    
    private double getCenterYValue(double minY, double maxY)
    {
        return (maxY + minY)/2;
    }
    
    private BigDecimal getStartingHalfDimension(DataPoint minValues, DataPoint maxValues)
    {
        BigDecimal maxyCoord = new BigDecimal(maxValues.getyCoord());
        BigDecimal minyCoord = new BigDecimal(minValues.getyCoord());
        return maxyCoord.subtract(minyCoord).divide(new BigDecimal(2));
    }
    
    private AxisAlignedBoundingBox createStartBoxFromFileContents(ArrayList<DataPoint> fileContents)
    {
        DataPoint minValues = getMinValues(fileContents);
        DataPoint maxValues = getMaxValues(fileContents);
        
        DataPoint CenterValue = getCenterValue(minValues, maxValues);
        
        BigDecimal startingHalfDimension = getStartingHalfDimension(minValues, maxValues);
        
        return new AxisAlignedBoundingBox(new DataPoint(CenterValue.getxCoord(), CenterValue.getyCoord(), 0), startingHalfDimension);
    }
    private void createQuadTreeStructure(ArrayList<DataPoint> fileContents)
    {
        for( int i = 0; i < fileContents.size(); i++ )
        {
            insert(fileContents.get(i));
        }
    }
        
    public ArrayList<DataPoint> queryDataPointsInRange(UserInput inputs)
    {
        double inputCenterX = (inputs.getxLeft() + inputs.getxRight())/2;
        double inputCenterY = (inputs.getyLower() + inputs.getyUpper())/2;
        
        BigDecimal xRight = new BigDecimal(inputs.getxRight());
        BigDecimal xLeft = new BigDecimal(inputs.getxLeft());
        BigDecimal halfDimension = xRight.subtract(xLeft).divide(new BigDecimal(2));
        AxisAlignedBoundingBox queryBox = new AxisAlignedBoundingBox(new DataPoint(inputCenterX, inputCenterY, 0), halfDimension);
        return queryRange(queryBox);
    }
    
    public QuadTree getNorthWest()
    {
        return northWest;
    }

    public QuadTree getNorthEast()
    {
        return northEast;
    }

    public QuadTree getSouthWest()
    {
        return southWest;
    }

    public QuadTree getSouthEast()
    {
        return southEast;
    }
}
