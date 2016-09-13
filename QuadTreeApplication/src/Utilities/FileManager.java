/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import quadtree.DataPoint;

/**
 *
 * @author tkidder
 */
public class FileManager
{
    private int timesSaved = 0;
    private enum fileOperation {OPEN, SAVE};
    public ArrayList<DataPoint> ReadFile()
    {
        String filePath;
        do
        {
            JOptionPane.showMessageDialog(null, "Please choose an input file");
            filePath = GetFilePath(fileOperation.OPEN);
            System.out.println(filePath);
        }while(filePath.equals(""));
        return ReadFile(filePath);
    }
    
    private ArrayList<DataPoint> ReadFile(String filePath)
    {
        ArrayList<DataPoint> coordinates = new ArrayList<>();
        
        int counter = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null)
            {
                counter++;
                double xCoord = Double.parseDouble(line.split(",")[0]);
                double yCoord = Double.parseDouble(line.split(",")[1]);
                double Value = Double.parseDouble(line.split(",")[2]);
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
        return coordinates;
    }
    
    public void PrintToFile(ArrayList<DataPoint> coordinates)
    {
        JOptionPane.showMessageDialog(null, "Please choose a location to save");
        try(PrintWriter pr = new PrintWriter(GetFilePath(fileOperation.SAVE) + 
                                            "\\query_results" + 
                                            ((timesSaved == 0) ? "" : timesSaved).toString() + 
                                            ".txt"))
        {
            coordinates.stream().forEach((coordinate) ->
            {
                pr.println(coordinate.getxCoord() + "," + coordinate.getyCoord() + "," + coordinate.getValue());
            });
            pr.close();
            timesSaved++;
        }
        catch (Exception e)
        {
            System.out.println("No such file exists.");
        }
    }
    
    private int LinesInFile(String filePath)
    {
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
