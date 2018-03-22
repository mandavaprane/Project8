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
public class Movie extends Idables implements Parsable, Dumpable{
    private String title;
    private boolean rentedState;

    public Movie(){
    }

    public String toText() {
        return getId()+";"+title+";"+(rentedState ?"1":"0");
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void rentMovie(){
        rentedState = true;
    }

    public void returnMovie(){
        rentedState = false;
    }

    public boolean getRentedState(){
        return rentedState;
    }

    @Override
    public void fromText(String s) {
        String[] spl = s.split(";");
        int id = Integer.parseInt(spl[0]);
        title = spl[1];
        rentedState = spl[2].equals("1");
        setId(id);
    }

    @Override
    public String toString(){
        return String.format("Id: %d, Title: %s, Rented: %b", getId(), title, rentedState);
    }
}
