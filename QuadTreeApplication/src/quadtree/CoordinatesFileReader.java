package quadtree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class CoordinatesFileReader
{
    public static ArrayList<DataPoint> ReadFile()
    {
        String filePath = GetFilePath(); 
        return ReadFile(filePath);
    }
    
    public static ArrayList<DataPoint> ReadFile(String filePath)
    {        
        long startTime = System.nanoTime();
        
        ArrayList<DataPoint> coordinates = new ArrayList<>();
        
        int counter = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null)
            {
                counter++;
                BigDecimal xCoord = new BigDecimal(line.split(",")[0]);
                BigDecimal yCoord = new BigDecimal(line.split(",")[1]);
                BigDecimal Value = new BigDecimal(line.split(",")[2]);
                coordinates.add(new DataPoint(xCoord, yCoord, Value));
                line = br.readLine();
                
                if(counter % 100000 == 0)
                    System.out.println("Read: " + counter + " lines");
            }
            br.close();
        }
        catch(IOException ex)
        {
            
        }
        long endTime = System.nanoTime();
        System.out.println("Read File Took " + ((endTime - startTime)/ 1000000000.0) + " seconds"); 
        return coordinates;
    }
    
    /**
    * Does some thing in old style.
    *
    * @deprecated as we no longer count file lines  
    */
    private static int LinesInFile(String filePath)
    {
        long startTime = System.nanoTime();
        int linesInFile = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
            String line = br.readLine();
            while (line != null)
            {
                linesInFile++;
                line = br.readLine();
            }
        }
        catch(IOException ex)
        {
            
        }
        long endTime = System.nanoTime();
        System.out.println("Lines In File Took "+ ((endTime - startTime)/ 1000000000.0) + " seconds"); 
        return linesInFile;
    }
    
    private static String GetFilePath()
    {
        JFrame f = new JFrame("File Reader");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(f);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            f.dispose();
            return selectedFile.getAbsolutePath();
        }
        f.dispose();
        return "";
    }
}
