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
import java.io.IOException;
import java.util.ArrayList;

public class Manager {
    private String filesLocation;
    private Store store = new Store();

    private Date checkDate(String s){
        Date date = null;
        try {
            date = new Date(s);
            return date;
        }
        catch (IllegalArgumentException e){
            System.out.println("Can't parse date");
            return null;
        }
    }

    public Manager(String filesLocation) throws IOException{
        this.filesLocation = filesLocation;
        load();
    }

    private void load() throws IOException{
        store.load(filesLocation);
    }

    private void dump() throws IOException{
        store.dump(filesLocation);
    }

    public void addMovie(String title) throws IOException{
        load();
        int res = store.addMovie(title);
        System.out.println("Added movie id: "+res);
        dump();
    }

    public void rentMovie(int client, int movie, String s) throws IOException{
        Date date = checkDate(s);
        if(date == null){
            return;
        }

        load();
        try {
            store.rentMovie(client, movie, date);
        }
        catch (IllegalStateException ex){
            System.out.println(ex.getMessage());
        }
        dump();
    }

    public void returnMovie(int movie, String s) throws IOException{
        Date date = checkDate(s);
        if(date == null){
            return;
        }

        load();
        try {
            store.returnMovie(movie, date);
        }
        catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
        dump();
    }

    public void getAllMovies() throws IOException{
        load();
        ArrayList<Movie> array = store.getAllMovies();
        for(int i = 0; i<array.size(); i++){
            System.out.println(array.get(i));
        }
        dump();
    }

    public void getRentedMovies() throws IOException{
        load();
        ArrayList<Movie> array = store.getRentedMovies();
        for(int i = 0; i<array.size(); i++){
            System.out.println(array.get(i));
        }
        dump();
    }


    public void getRentHistory(int movie) throws IOException{
        load();
        ArrayList<RentalInfo> array = store.getRentHistory(movie);
        for(int i = 0; i<array.size(); i++){
            System.out.println(array.get(i));
        }
        dump();
    }

    public void addUser(String firstName, String lastName) throws IOException{
        load();
        int res = store.addUser(firstName, lastName);
        System.out.println("Added client id: "+res);
        dump();
    }

    public void deleteUser(int id) throws IOException{
        load();
        try {
            store.deleteUser(id);
        }
        catch (IllegalArgumentException e){
            System.out.println("No user with that id found");
        }
        dump();
    }


    public void searchUserByFirstName(String firstName) throws IOException{
        load();
        ArrayList<User> array = store.searchUserByFirstName(firstName);
        for(int i = 0; i<array.size(); i++){
            System.out.println(array.get(i));
        }
        dump();
    }

    public void searchUserByLastName(String lastName) throws IOException{
        load();
        ArrayList<User> array = store.searchUserByLastName(lastName);
        for(int i = 0; i<array.size(); i++){
            System.out.println(array.get(i));
        }
        dump();
    }

    public void getAllUsers() throws IOException{
        load();
        ArrayList<User> array = store.getAllUsers();
        for(int i = 0; i<array.size(); i++){
            System.out.println(array.get(i));
        }
        dump();
    }
}
