/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author tkidder
 */
public class ActionLogger
{
    ArrayList<String> logs;
    String filePath = "C:\\Users\\tkidder\\Desktop\\LogFile.txt";
    public ActionLogger()
    {
        logs = new ArrayList<>();
    }
    
    public void AddLog(String log)
    {
        logs.add(log);
    }
    
    public void WriteToFile()
    {
        try(PrintWriter pr = new PrintWriter(filePath))
        {
            logs.stream().forEach((log) ->
            {
                pr.println(log);
            });
            pr.close();
        }
        catch (Exception e)
        {
            System.out.println("No such file exists.");
        }
    }
}
