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
public class Date implements Comparable{
    private int day;
    private int month;
    private int year;

    public Date(String s) throws IllegalArgumentException{
        try {
            String[] spl = s.split("/");
            year = Integer.parseInt(spl[2]);
            month = Integer.parseInt(spl[0]);
            day = Integer.parseInt(spl[1]);
        }
        catch (Exception e){
            throw new IllegalArgumentException();
        }
    }

    public int getDays() {
        return year * 365 + month * 30 + day;
    }

    @Override
    public String toString(){
        return month+"/"+day+"/"+year;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Date){
            Date newO = (Date)o;
            return ((Integer)getDays()).compareTo(newO.getDays());
        }
        else{
            return 0;
        }
    }
}
