package quadtree;

import java.io.PrintWriter;
import java.util.ArrayList;

public class CoordinatesFilePrinter
{
    public static void PrintToFile(ArrayList<DataPoint> coordinates, String filePath)
    {
        long startTime = System.nanoTime();
        try(PrintWriter pr = new PrintWriter(filePath))
        {
            coordinates.stream().forEach((coordinate) ->
            {
                pr.println(coordinate.getxCoord() + "," + coordinate.getyCoord() + "," + coordinate.getValue());
            });
            pr.close();
        }
        catch (Exception e)
        {
            System.out.println("No such file exists.");
        }
        
        long endTime = System.nanoTime();
        System.out.println("Print To File Took " + ((endTime - startTime)/ 1000000000.0) + " seconds");
    }
}
