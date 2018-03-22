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
public class RentalInfo extends Idables implements Parsable, Dumpable{
    private Date dateIn;
    private Date dateOut;
    private int client;
    private int movie;

    @Override
    public String toText() {
        if(dateOut != null) {
            return getId() + ";" + client + ";" + movie + ";" + dateIn.toString() + ";" + dateOut.toString();
        }
        else{
            return getId() + ";" + client + ";" + movie + ";" + dateIn.toString();
        }
    }

    @Override
    public void fromText(String s) {
        String[] spl = s.split(";");
        client = Integer.parseInt(spl[1]);
        movie = Integer.parseInt(spl[2]);
        dateIn = new Date(spl[3]);
        dateOut = null;
        if(spl.length == 5) {
            dateOut = new Date(spl[4]);
        }
        int id = Integer.parseInt(spl[0]);
        setId(id);
    }

    public void setMovie(int id){
        this.movie = id;
    }

    public int getMovie(){
        return movie;
    }

    public int getClient(){
        return client;
    }

    public Date getDateIn(){
        return dateIn;
    }

    public void returnAction(Date date){
        dateOut = date;
    }

    public void setDateIn(Date date){
        this.dateIn = date;
    }

    public boolean getReturnedState(){
        return dateOut != null;
    }

    public void setClient(int id){
        this.client = id;
    }

    public Date getDateOut(){
        return dateOut;
    }

    @Override
    public String toString(){
        if(getReturnedState()) {
            return String.format("Id: %d, User: %s, Movie: %s, Available: %b, Date in: %s, Date out: %s", getId(), client, movie, getReturnedState(), dateIn.toString(), dateOut.toString());
        }
        else{
            return String.format("Id: %d, User: %s, Movie: %s, Available: %b, Date in: %s", getId(), client, movie, getReturnedState(), dateIn.toString());
        }
    }
}
