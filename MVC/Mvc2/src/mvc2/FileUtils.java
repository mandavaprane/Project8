/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc2;

/**
 *
 * @author Praneetha
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
    public static void write(String path, String content) throws IOException{
        File file = new File(path);
        FileWriter fooWriter = new FileWriter(file, false);
        fooWriter.write(content);
        fooWriter.close();
    }

    public static String[] read(String path) throws IOException{
        String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
        return content.split("\n");
    }
}
