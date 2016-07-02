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
    
    private void LogSubdividedValues(QuadTree northWest, QuadTree northEast, QuadTree southWest, QuadTree southEast)
    {
        QuadTreeApplication.logger.AddLog("\n\n");
        QuadTreeApplication.logger.AddLog("/*****************New Subdivided Trees****************/");
        
        QuadTreeApplication.logger.AddLog("\n\n");
        QuadTreeApplication.logger.AddLog("North West Information");
        QuadTreeApplication.logger.AddLog("North West Center: (" + northWest.boundary.center.getxCoord() + ", " + northWest.boundary.center.getyCoord() + ")");
        QuadTreeApplication.logger.AddLog("North West Half Dimension: " + northWest.boundary.halfDimension);
        QuadTreeApplication.logger.AddLog("North West MinX: " + northWest.boundary.center.getxCoord().subtract(northWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("North West MaxX: " + northWest.boundary.center.getxCoord().add(northWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("North West MinY: " + northWest.boundary.center.getyCoord().subtract(northWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("North West MaxY: " + northWest.boundary.center.getyCoord().add(northWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("\n\n");
        
        QuadTreeApplication.logger.AddLog("\n\n");
        QuadTreeApplication.logger.AddLog("North East Information");
        QuadTreeApplication.logger.AddLog("North East Center: (" + northEast.boundary.center.getxCoord() + ", " + northEast.boundary.center.getyCoord() + ")");
        QuadTreeApplication.logger.AddLog("North East Half Dimension: " + northEast.boundary.halfDimension);
        QuadTreeApplication.logger.AddLog("North East MinX: " + northEast.boundary.center.getxCoord().subtract(northEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("North East MaxX: " + northEast.boundary.center.getxCoord().add(northEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("North East MinY: " + northEast.boundary.center.getyCoord().subtract(northEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("North East MaxY: " + northEast.boundary.center.getyCoord().add(northEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("\n\n");
        
        QuadTreeApplication.logger.AddLog("\n\n");
        QuadTreeApplication.logger.AddLog("South West Information");
        QuadTreeApplication.logger.AddLog("South West Center: (" + southWest.boundary.center.getxCoord() + ", " + southWest.boundary.center.getyCoord() + ")");
        QuadTreeApplication.logger.AddLog("South West Half Dimension: " + southWest.boundary.halfDimension);
        QuadTreeApplication.logger.AddLog("South West MinX: " + southWest.boundary.center.getxCoord().subtract(southWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("South West MaxX: " + southWest.boundary.center.getxCoord().add(southWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("South West MinY: " + southWest.boundary.center.getyCoord().subtract(southWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("South West MaxY: " + southWest.boundary.center.getyCoord().add(southWest.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("\n\n");
        
        QuadTreeApplication.logger.AddLog("\n\n");
        QuadTreeApplication.logger.AddLog("South East Information");
        QuadTreeApplication.logger.AddLog("South East Center: (" + southEast.boundary.center.getxCoord() + ", " + southEast.boundary.center.getyCoord() + ")");
        QuadTreeApplication.logger.AddLog("South East Half Dimension: " + southEast.boundary.halfDimension);
        QuadTreeApplication.logger.AddLog("South East MinX: " + southEast.boundary.center.getxCoord().subtract(southEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("South East MaxX: " + southEast.boundary.center.getxCoord().add(southEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("South East MinY: " + southEast.boundary.center.getyCoord().subtract(southEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("South East MaxY: " + southEast.boundary.center.getyCoord().add(southEast.boundary.halfDimension) );
        QuadTreeApplication.logger.AddLog("\n\n");
        
        QuadTreeApplication.logger.AddLog("/*****************End Subdivided Trees****************/");
        QuadTreeApplication.logger.AddLog("\n\n");
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
        BigDecimal northWestNewCenterX = (boundary.center.getxCoord().subtract(boundary.halfDimension.divide(new BigDecimal(2))));
        BigDecimal northWestNewCenterY = (boundary.center.getyCoord().add(boundary.halfDimension.divide(new BigDecimal(2))));
        return new DataPoint(northWestNewCenterX, northWestNewCenterY, new BigDecimal(0));
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
        BigDecimal northEastNewCenterX = (boundary.center.getxCoord().add(boundary.halfDimension.divide(new BigDecimal(2))));
        BigDecimal northEastNewCenterY = (boundary.center.getyCoord().add(boundary.halfDimension.divide(new BigDecimal(2))));
        return new DataPoint(northEastNewCenterX, northEastNewCenterY, new BigDecimal(0));
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
        BigDecimal southWestNewCenterX = (boundary.center.getxCoord().subtract(boundary.halfDimension.divide(new BigDecimal(2))));
        BigDecimal southWestNewCenterY = (boundary.center.getyCoord().subtract(boundary.halfDimension.divide(new BigDecimal(2))));
        return new DataPoint(southWestNewCenterX, southWestNewCenterY, new BigDecimal(0));
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
        BigDecimal southEastNewCenterX = boundary.center.getxCoord().add(boundary.halfDimension.divide(new BigDecimal(2)));
        BigDecimal southEastNewCenterY = boundary.center.getyCoord().subtract(boundary.halfDimension.divide(new BigDecimal(2)));
        return new DataPoint(southEastNewCenterX, southEastNewCenterY, new BigDecimal(0));
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
}
