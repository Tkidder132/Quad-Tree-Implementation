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
    // Arbitrary constant to indicate how many elements can be stored in this quad tree node
    final int QuadTreeNodeCapacity = 4;
    
    AxisAlignedBoundingBox boundary;
    
    ArrayList<DataPoint> points = new ArrayList<>();
    
    //Children
    QuadTree northWest;
    QuadTree northEast;
    QuadTree southWest;
    QuadTree southEast;
    
    public QuadTree(ArrayList<DataPoint> dataPoints)
    {
        boundary = CreateStartBoxFromFileContents(dataPoints);
        CreateQuadTreeStructure(dataPoints);
    }
    
    public QuadTree(AxisAlignedBoundingBox _boundary)
    {
        this.boundary = _boundary;
    }
    
    // Insert a point into the QuadTree
    public boolean Insert(DataPoint p)
    {
        // Ignore objects that do not belong in this quad tree
        if (!boundary.containsPoint(p))
            return false; // object cannot be added
        
        // If there is space in this quad tree, add the object here
        if (points.size() < QuadTreeNodeCapacity)
        {
            points.add(p);
            return true;
        }

        // Otherwise, subdivide and then add the point to whichever node will accept it
        if (northWest == null)
            Subdivide();
        
        if (northWest.Insert(p)) return true;
        if (northEast.Insert(p)) return true;
        if (southWest.Insert(p)) return true;
        if (southEast.Insert(p)) return true;
        
        // Otherwise, the point cannot be inserted for some unknown reason (this should never happen)
        //QuadTreeApplication.logger.AddLog(String.format("Point p: (%.10f, %.10f)", p.getxCoord(), p.getyCoord()));
        //LogSubdividedValues(northWest, northEast, southWest, southEast);
        return false;
    }
    
    public void Subdivide()
    {
        northWest = CreateNorthWestSubdividedQuadTree();
        northEast = CreateNorthEastSubdividedQuadTree();
        southWest = CreateSouthWestSubdividedQuadTree();
        southEast = CreateSouthEastSubdividedQuadTree();
    }
        
    private QuadTree CreateNorthWestSubdividedQuadTree()
    {
        return CreateSubdividedQuadTree(CreateNorthWestSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox CreateNorthWestSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(CreateNorthWestSubdividedCenterPoint(), this.boundary.halfDimension.divide(new BigDecimal(2)));
    }
    
    private DataPoint CreateNorthWestSubdividedCenterPoint()
    {
        double northWestNewCenterX = (boundary.center.getxCoord() - (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue());
        double northWestNewCenterY = (boundary.center.getyCoord() + (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue());
        return new DataPoint(northWestNewCenterX, northWestNewCenterY, 0);
    }
    
    private QuadTree CreateNorthEastSubdividedQuadTree()
    {
        return CreateSubdividedQuadTree(CreateNorthEastSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox CreateNorthEastSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(CreateNorthEastSubdividedCenterPoint(), this.boundary.halfDimension.divide(new BigDecimal(2)));
    }
    
    private DataPoint CreateNorthEastSubdividedCenterPoint()
    {
        double northEastNewCenterX = (boundary.center.getxCoord() + (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue());
        double northEastNewCenterY = (boundary.center.getyCoord() + (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue());
        return new DataPoint(northEastNewCenterX, northEastNewCenterY, 0);
    }
    
    private QuadTree CreateSouthWestSubdividedQuadTree()
    {
        return CreateSubdividedQuadTree(CreateSouthWestSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox CreateSouthWestSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(CreateSouthWestSubdividedCenterPoint(), this.boundary.halfDimension.divide(new BigDecimal(2)));
    }
    
    private DataPoint CreateSouthWestSubdividedCenterPoint()
    {
        double southWestNewCenterX = (boundary.center.getxCoord() - (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue());
        double southWestNewCenterY = (boundary.center.getyCoord() - (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue());
        return new DataPoint(southWestNewCenterX, southWestNewCenterY, 0);
    }
    
    private QuadTree CreateSouthEastSubdividedQuadTree()
    {
        return CreateSubdividedQuadTree(CreateSouthEastSubdividedBoundingBox());
    }
    
    private AxisAlignedBoundingBox CreateSouthEastSubdividedBoundingBox()
    {
        return new AxisAlignedBoundingBox(CreateSouthEastSubdividedCenterPoint(), boundary.halfDimension.divide(new BigDecimal(2)));
    }
    
    private DataPoint CreateSouthEastSubdividedCenterPoint()
    {
        double southEastNewCenterX = boundary.center.getxCoord() + (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue();
        double southEastNewCenterY = boundary.center.getyCoord() - (boundary.halfDimension.divide(new BigDecimal(2))).doubleValue();
        return new DataPoint(southEastNewCenterX, southEastNewCenterY, 0);
    }
    
    private QuadTree CreateSubdividedQuadTree(AxisAlignedBoundingBox boundary)
    {
        return new QuadTree(boundary);
    }
    
    public ArrayList<DataPoint> QueryRange(AxisAlignedBoundingBox range)
    {
        ArrayList<DataPoint> pointsInRange = new ArrayList<>();

        // Automatically abort if the range does not intersect this quad
        if (!boundary.intersectsAABB(range))
            return pointsInRange; // empty list
        
        // Check objects at this quad level
        for (int i = 0; i < points.size(); i++)
        {
          if (range.containsPoint(points.get(i)))
            pointsInRange.add(points.get(i));
        }

        // Terminate here, if there are no children
        if (northWest == null)
            return pointsInRange;

        // Otherwise, add the points from the children
        pointsInRange.addAll(northWest.QueryRange(range));
        pointsInRange.addAll(northEast.QueryRange(range));
        pointsInRange.addAll(southWest.QueryRange(range));
        pointsInRange.addAll(southEast.QueryRange(range));

        return pointsInRange;
    }
    
    
    private DataPoint GetMinValues(ArrayList<DataPoint> fileContents)
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
    
    private DataPoint GetMaxValues(ArrayList<DataPoint> fileContents)
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
    
    private DataPoint GetCenterValue(DataPoint minValues, DataPoint maxValues)
    {
        return new DataPoint(GetCenterXValue(minValues.getxCoord(), maxValues.getxCoord()), GetCenterYValue(minValues.getyCoord(), maxValues.getyCoord()), 0);
    }
    
    private double GetCenterXValue(double minX, double maxX)
    {
        return (maxX + minX)/2;
    }
    
    private double GetCenterYValue(double minY, double maxY)
    {
        return (maxY + minY)/2;
    }
    
    private BigDecimal GetStartingHalfDimension(DataPoint minValues, DataPoint maxValues)
    {
        BigDecimal maxyCoord = new BigDecimal(maxValues.getyCoord());
        BigDecimal minyCoord = new BigDecimal(minValues.getyCoord());
        return maxyCoord.subtract(minyCoord).divide(new BigDecimal(2));
    }
    
    private AxisAlignedBoundingBox CreateStartBoxFromFileContents(ArrayList<DataPoint> fileContents)
    {
        DataPoint minValues = GetMinValues(fileContents);
        DataPoint maxValues = GetMaxValues(fileContents);
        
        DataPoint CenterValue = GetCenterValue(minValues, maxValues);
        
        BigDecimal startingHalfDimension = GetStartingHalfDimension(minValues, maxValues);
        
        return new AxisAlignedBoundingBox(new DataPoint(CenterValue.getxCoord(), CenterValue.getyCoord(), 0), startingHalfDimension);
    }
    private void CreateQuadTreeStructure(ArrayList<DataPoint> fileContents)
    {
        long startTime = System.nanoTime();
        AxisAlignedBoundingBox startBox = CreateStartBoxFromFileContents(fileContents);
        
        int counter = 0;
        for( int i = 0; i < fileContents.size(); i++ )
        {
            if(Insert(fileContents.get(i)))
                counter++;
            
            if(counter % 100000 == 0)
                System.out.println("Inserted " + counter + " values");
        }
        long endTime = System.nanoTime();
        System.out.println("Creating QuadTree Structure Took " + ((endTime - startTime)/ 1000000000.0) + " seconds");
        System.out.println("Nodes added: " + counter + "/" + fileContents.size());
    }
        
    public ArrayList<DataPoint> QueryDataPointsInRange(UserInput inputs)
    {
        //long startTime = System.nanoTime();
        
        double inputCenterX = (inputs.getxLeft() + inputs.getxRight())/2;
        double inputCenterY = (inputs.getyLower() + inputs.getyUpper())/2;
        
        BigDecimal xRight = new BigDecimal(inputs.getxRight());
        BigDecimal xLeft = new BigDecimal(inputs.getxLeft());
        BigDecimal halfDimension = xRight.subtract(xLeft).divide(new BigDecimal(2));
        AxisAlignedBoundingBox queryBox = new AxisAlignedBoundingBox(new DataPoint(inputCenterX, inputCenterY, 0), halfDimension);
        
        //long endTime = System.nanoTime();
        //System.out.println("Querying Coordinates In Range Took " + ((endTime - startTime)/ 1000000000.0) + " seconds");
        
        return QueryRange(queryBox);
    }
}
