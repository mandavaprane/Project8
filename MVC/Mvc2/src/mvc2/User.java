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
public class User extends Idables implements Parsable, Dumpable{
    private String name;
    private boolean deletedState;

    public User(){
    }

    public void setName(String name){
        this.name = name;
    }

    public void delete(){
        deletedState = true;
    }

    public boolean getDeletedState(){
        return deletedState;
    }

    public String getFirstName(){
        String[] spl = name.split(" ");
        return spl[0];
    }

    public String getLastName(){
        String[] spl = name.split(" ");
        return spl[1];
    }

    @Override
    public String toText() {
        if(deletedState) {
            return getId() + ";" + getName() + ";1";
        }
        else{
            return getId() + ";" + getName() + ";0";
        }
    }

    @Override
    public void fromText(String s) {
        String[] spl = s.split(";");
        name = spl[1];
        if(spl[2].equals("1")) {
            deletedState = true;
        }
        else{
            deletedState = false;
        }
        int id = Integer.parseInt(spl[0]);
        setId(id);
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return String.format("Id: %d, Name: %s", getId(), name);
    }
}
