package quadtree;

import Utilities.ActionLogger;
import Utilities.FileManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuadTreeApplication
{
    public static ActionLogger logger = new ActionLogger();
    public static void main(String[] args)
    {
        FileManager fileManager = new FileManager();
        ArrayList<DataPoint> fileContents = fileManager.ReadFile();
        
        //QuadTree Implementation
        QuadTree QuadTreeStructure = new QuadTree(fileContents);
        
        //query loop
        boolean firstTime = true;
        while(JOptionPane.showConfirmDialog(null, 
                    firstTime ? "Would you like to run a query?" : "Would you like to run another query?",
                    "Choose", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            firstTime = false;
            String input = JOptionPane.showInputDialog("Please input boundaries (xLeft xRight yLower yUpper: ");
            UserInput inputs = new UserInput(input.split(" "));
            ArrayList<DataPoint> coordinatesInRange = QuadTreeStructure.QueryDataPointsInRange(inputs);
            fileManager.PrintToFile(coordinatesInRange);
            JOptionPane.showMessageDialog(null, "Query output saved to file");
        }
    }
}
