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
import java.util.HashMap;

public abstract class Idables {
    private static HashMap<String, Integer> lastMap = new HashMap<String, Integer>();
    private int id;

    public Idables(){
        String clazz = getClass().getSimpleName();
        int last = 0;
        if(lastMap.containsKey(clazz)){
            last = lastMap.get(clazz);
        }
        id = last+1;
        lastMap.put(clazz, id);
    }

    public int getId(){
        return id;
    }

    public void setId(int value){
        id = value;
        String clazz = getClass().getSimpleName();
        lastMap.put(clazz, id);
    }
}
