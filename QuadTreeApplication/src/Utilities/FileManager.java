/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author tkidder
 */
public class FileManager
{
    
    private enum fileOperation {OPEN, SAVE};
    public ArrayList<DataPoint> ReadFile()
    {
        String filePath = GetFilePath(fileOperation.OPEN); 
        return ReadFile(filePath);
    }
    
    private ArrayList<DataPoint> ReadFile(String filePath)
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
    
    public void PrintToFile(ArrayList<DataPoint> coordinates)
    {
        long startTime = System.nanoTime();
        System.out.println(GetFilePath(fileOperation.SAVE) + "\\query_results.txt");
        try(PrintWriter pr = new PrintWriter(GetFilePath(fileOperation.SAVE) + "\\query_results.txt"))
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
    
    private int LinesInFile(String filePath)
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
    
    private String GetFilePath(fileOperation operation)
    {
        JFrame f = new JFrame("File Reader");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        if(operation == fileOperation.SAVE)
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int result = operation == fileOperation.OPEN ? fileChooser.showOpenDialog(f) : fileChooser.showSaveDialog(f);
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
