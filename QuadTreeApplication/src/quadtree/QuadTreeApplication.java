package quadtree;

import Utilities.ActionLogger;
import java.math.BigDecimal;
import java.util.ArrayList;

public class QuadTreeApplication
{
    public static ActionLogger logger = new ActionLogger();
    public static void main(String[] args)
    {
        FileManager fileManager = new FileManager();
        UserInput inputs = CreateUserInput(args);
        ArrayList<DataPoint> fileContents = fileManager.ReadFile();
        
        //Brute Force
        //ArrayList<DataPoint> coordinatesInRange = GetCoordinatesInRange(fileContents, inputs);
        
        //QuadTree Implementation
        QuadTree QuadTreeStructure = CreateQuadTreeStructure(fileContents);
        ArrayList<DataPoint> coordinatesInRange = QueryDataPointsInRange(QuadTreeStructure, inputs);
        
        fileManager.PrintToFile(coordinatesInRange);
        //logger.WriteToFile();
    }
    
    private static DataPoint GetMinValues(ArrayList<DataPoint> fileContents)
    {
        BigDecimal X = new BigDecimal(0);
        BigDecimal Y = new BigDecimal(0);
        for(int i = 0; i < fileContents.size(); i++)
        {
            X = BigDecimalUtility.BigDecimalLessThan(fileContents.get(i).getxCoord(), X) ? fileContents.get(i).getxCoord() : X;
            Y = BigDecimalUtility.BigDecimalLessThan(fileContents.get(i).getxCoord(), Y) ? fileContents.get(i).getyCoord() : Y;
        }
        BigDecimal smallestValue = BigDecimalUtility.BigDecimalLessThan(X, Y) ? X : Y;
        return new DataPoint(smallestValue.subtract(new BigDecimal(1)), smallestValue.subtract(new BigDecimal(1)), new BigDecimal(0));
    }
    
    private static DataPoint GetMaxValues(ArrayList<DataPoint> fileContents)
    {
        BigDecimal X = new BigDecimal(0);
        BigDecimal Y = new BigDecimal(0);
        for(int i = 0; i < fileContents.size(); i++)
        {
            X = BigDecimalUtility.BigDecimalGreaterThan(fileContents.get(i).getxCoord(), X) ? fileContents.get(i).getxCoord() : X;
            Y = BigDecimalUtility.BigDecimalGreaterThan(fileContents.get(i).getxCoord(), Y) ? fileContents.get(i).getxCoord() : Y;
        }
        BigDecimal biggestValue = BigDecimalUtility.BigDecimalGreaterThan(X, Y) ? X : Y;
        return new DataPoint(biggestValue.add(new BigDecimal(1)), biggestValue.add(new BigDecimal(1)), new BigDecimal(0));
    }
    
    private static DataPoint GetCenterValue(DataPoint minValues, DataPoint maxValues)
    {
        return new DataPoint(GetCenterXValue(minValues.getxCoord(), maxValues.getxCoord()), GetCenterYValue(minValues.getyCoord(), maxValues.getyCoord()), new BigDecimal(0));
    }
    
    private static BigDecimal GetCenterXValue(BigDecimal minX, BigDecimal maxX)
    {
        return (maxX.add(minX)).divide(new BigDecimal(2));
    }
    
    private static BigDecimal GetCenterYValue(BigDecimal minY, BigDecimal maxY)
    {
        return (maxY.add(minY)).divide(new BigDecimal(2));
    }
    
    private static BigDecimal GetStartingHalfDimension(DataPoint minValues, DataPoint maxValues)
    {
        return maxValues.getyCoord().subtract(minValues.getyCoord()).divide(new BigDecimal(2));
    }
    
    private static AxisAlignedBoundingBox CreateStartBoxFromFileContents(ArrayList<DataPoint> fileContents)
    {
        DataPoint minValues = GetMinValues(fileContents);
        DataPoint maxValues = GetMaxValues(fileContents);
        System.out.println("Min Values X: " + minValues.getxCoord() + " Y: " + minValues.getyCoord());
        System.out.println("Max Values X: " + maxValues.getxCoord() + " Y: " + maxValues.getyCoord());
        
        DataPoint CenterValue = GetCenterValue(minValues, maxValues);
        System.out.println("Center Value X: " + CenterValue.getxCoord() + " Y: " + CenterValue.getyCoord());
        
        BigDecimal startingHalfDimension = GetStartingHalfDimension(minValues, maxValues);
        System.out.println("Starting half dimension: " + startingHalfDimension);
        
        return new AxisAlignedBoundingBox(new DataPoint(CenterValue.getxCoord(), CenterValue.getyCoord(), new BigDecimal(0)), startingHalfDimension);
    }
    private static QuadTree CreateQuadTreeStructure(ArrayList<DataPoint> fileContents)
    {
        long startTime = System.nanoTime();
        AxisAlignedBoundingBox startBox = CreateStartBoxFromFileContents(fileContents); 
        QuadTree startNode = new QuadTree(startBox);
        
        int counter = 0;
        for( int i = 0; i < fileContents.size(); i++ )
        {
            if(startNode.Insert(fileContents.get(i)))
                counter++;
            
            if(counter % 100000 == 0)
                System.out.println("Inserted " + counter + " values");
        }
        long endTime = System.nanoTime();
        System.out.println("Creating QuadTree Structure Took " + ((endTime - startTime)/ 1000000000.0) + " seconds");
        System.out.println("Nodes added: " + counter + "/" + fileContents.size());
        return startNode;
    }
    
    private static UserInput CreateUserInput(String[] args)
    {
        BigDecimal xLeft = new BigDecimal(args[0]);
        BigDecimal xRight = new BigDecimal(args[1]);
        BigDecimal yLower = new BigDecimal(args[2]);
        BigDecimal yUpper = new BigDecimal(args[3]);
        return new UserInput(xLeft, xRight, yLower, yUpper);
    }
    
    private static ArrayList<DataPoint> QueryDataPointsInRange(QuadTree QuadTreeStructure, UserInput inputs)
    {
        long startTime = System.nanoTime();
        
        BigDecimal inputCenterX = inputs.getxLeft().add(inputs.getxRight()).divide(new BigDecimal(2));
        BigDecimal inputCenterY = inputs.getyLower().add(inputs.getyUpper()).divide(new BigDecimal(2));
        BigDecimal halfDimension = inputs.getxRight().subtract(inputs.getxLeft()).divide(new BigDecimal(2));
        AxisAlignedBoundingBox queryBox = new AxisAlignedBoundingBox(new DataPoint(inputCenterX, inputCenterY, new BigDecimal(0)), halfDimension);
        
        long endTime = System.nanoTime();
        System.out.println("Querying Coordinates In Range Took " + ((endTime - startTime)/ 1000000000.0) + " seconds");
        
        return QuadTreeStructure.QueryRange(queryBox);
    }

    private static ArrayList<DataPoint> GetCoordinatesInRange(ArrayList<DataPoint> dataPoints, UserInput inputs)
    {
        long startTime = System.nanoTime();
        ArrayList<DataPoint> coordinatesInRange = new ArrayList<>();
        dataPoints.stream().filter((dataPoint) -> IsDataPointInInputsRange(dataPoint, inputs)).forEach((dataPoint) ->
        {
            coordinatesInRange.add(dataPoint);
        });
        long endTime = System.nanoTime();
        System.out.println("Finding CoordinatesInRange Took " + ((endTime - startTime)/ 1000000000.0) + " seconds");
        return coordinatesInRange;
    }
    
    private static boolean IsDataPointInInputsRange(DataPoint dataPoint, UserInput inputs)
    {
        return BigDecimalUtility.BigDecimalGreaterThanOrEqualTo(dataPoint.getxCoord(), inputs.getxLeft()) &&
               BigDecimalUtility.BigDecimalLessThanOrEqualTo(dataPoint.getxCoord(), inputs.getxRight()) &&
               BigDecimalUtility.BigDecimalGreaterThanOrEqualTo(dataPoint.getyCoord(), inputs.getyLower()) &&
               BigDecimalUtility.BigDecimalLessThanOrEqualTo(dataPoint.getyCoord(), inputs.getyUpper());
    }
    
    private static void PrintCoordinates(ArrayList<DataPoint> fileContents)
    {
        long startTime = System.nanoTime();
        fileContents.stream().forEach((fileContent) ->
        {
            System.out.println("(" + fileContent.getxCoord() + "," + fileContent.getyCoord() + "), " + fileContent.getValue());
        });
        long endTime = System.nanoTime();
        System.out.println("Print Contents Took " + ((endTime - startTime)/ 1000000000.0) + " seconds");
    }
}
