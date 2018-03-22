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
import java.util.Collections;
import java.util.Comparator;

public class Store {
    private ArrayList<User> usersList;
    private ArrayList<Movie> moviesList;
    private ArrayList<RentalInfo> rentalInfosList;

    public static class MoviesComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie o1, Movie o2) {
            Integer o1Value = o1.getRentedState()?1:0;
            Integer o2Value = o2.getRentedState()?1:0;
            return -o1Value.compareTo(o2Value);
        }
    }

    public static class RentalInfosComparator implements Comparator<RentalInfo>{
        @Override
        public int compare(RentalInfo o1, RentalInfo o2) {
            return -o1.getDateIn().compareTo(o2.getDateOut());
        }
    }

    private void readFromFile(ArrayList to, String fileName, String filesDirectory, Class type) throws IOException, InstantiationException, IllegalAccessException{
        String[] items = FileUtils.read(filesDirectory + fileName);
        for(int i = 0; i<items.length; i++){
            Parsable client = (Parsable)type.newInstance();
            client.fromText(items[i]);
            to.add(client);
        }
    }

    public void load(String filesDirectory) throws IOException{
        usersList = new ArrayList<User>();
        moviesList = new ArrayList<Movie>();
        rentalInfosList = new ArrayList<RentalInfo>();
        try {
            readFromFile(usersList, "clients.txt", filesDirectory, User.class);
            readFromFile(moviesList, "movies.txt", filesDirectory, Movie.class);
            readFromFile(rentalInfosList, "rental_info.txt", filesDirectory, RentalInfo.class);
        }
        catch (IllegalAccessException e1){
            e1.printStackTrace();
        }
        catch (InstantiationException e2){
            e2.printStackTrace();
        }

        for(int i = 0; i< moviesList.size(); i++){
            for(int j = 0; j< rentalInfosList.size(); j++){
                RentalInfo rentalInfo = rentalInfosList.get(j);
                Movie movie = moviesList.get(i);
                if(rentalInfo.getMovie() == movie.getId()){
                    if(!rentalInfo.getReturnedState()) {
                        movie.rentMovie();
                    }
                }
            }
        }
    }

    private String getContent(ArrayList list){
        String res = "";
        for(int i = 0; i< list.size(); i++){
            Dumpable object = (Dumpable)list.get(i);
            res += object.toText();
            if(i != list.size() - 1){
                res += "\n";
            }
        }
        return res;
    }

    public void dump(String filesLocation) throws IOException{
        String clientContent = getContent(usersList);
        String moviesContent = getContent(moviesList);
        String rentalsContent = getContent(rentalInfosList);
        FileUtils.write(filesLocation+"clients.txt", clientContent);
        FileUtils.write(filesLocation+"movies.txt", moviesContent);
        FileUtils.write(filesLocation+"rental_info.txt", rentalsContent);
    }

    public int addMovie(String title){
        Movie movie = new Movie();
        movie.setTitle(title);
        moviesList.add(movie);
        return movie.getId();
    }

    private boolean isFound(ArrayList list, int id){
        for(int i = 0; i < list.size(); i++){
            Idables idable = (Idables)list.get(i);
            if(idable.getId() == id){
               return true;
            }
        }
        return false;
    }

    public void rentMovie(int user, int movie, Date date) throws IllegalStateException{
        int rentalsCount = 0;
        for(int i = 0; i< rentalInfosList.size(); i++){
            RentalInfo loc = rentalInfosList.get(i);
            if(!loc.getReturnedState() && loc.getClient()==user){
                rentalsCount++;
            }
        }

        if(rentalsCount >= 3){
            throw new IllegalStateException("This user already rented >= 3 movies this time");
        }

        boolean userFound = isFound(usersList, user);
        boolean movieFound = isFound(moviesList, movie);

        if(!userFound){
            throw new IllegalStateException("No user with that id found");
        }

        if(!movieFound){
            throw new IllegalStateException("No movie with that id found");
        }

        RentalInfo rentalInfo = new RentalInfo();
        rentalInfo.setClient(user);
        rentalInfo.setMovie(movie);
        rentalInfo.setDateIn(date);

        for(int i = 0; i< moviesList.size(); i++) {
            Movie _movie = moviesList.get(i);
            if(_movie.getId() == movie){
                if(_movie.getRentedState()){
                    throw new IllegalStateException("The movie is currently rented");
                }
                else{
                    _movie.rentMovie();
                }
            }
        }
        rentalInfosList.add(rentalInfo);
    }

    public void returnMovie(int movieId, Date date) throws IllegalStateException{
        Movie movie = null;
        for(int i = 0; i< moviesList.size(); i++){
            if(moviesList.get(i).getId() == movieId){
                movie = moviesList.get(i);
            }
        }

        if(movie == null){
            throw new IllegalStateException("No movie with that is found");
        }

        if(!movie.getRentedState()){
            throw new IllegalStateException("The movie is not rented");
        }

        for(int j = rentalInfosList.size() - 1; j>=0; j--){
            RentalInfo rentalInfo = rentalInfosList.get(j);
            if(rentalInfo.getMovie() == movieId && !rentalInfo.getReturnedState()){
                movie.returnMovie();
                rentalInfo.returnAction(date);
            }
        }
    }

    public ArrayList<Movie> getAllMovies(){
        ArrayList<Movie> res = new ArrayList<Movie>();
        for(int i = 0; i< moviesList.size(); i++){
            res.add(moviesList.get(i));
        }

        Collections.sort(res, new MoviesComparator());
        return res;
    }

    public ArrayList<Movie> getRentedMovies(){
        ArrayList<Movie> res = new ArrayList<Movie>();
        for(int i = 0; i< moviesList.size(); i++){
            Movie movie = moviesList.get(i);
            if(movie.getRentedState()){
                res.add(movie);
            }
        }
        return res;
    }

    public ArrayList<RentalInfo> getRentHistory(int movie){
        ArrayList<RentalInfo> res = new ArrayList<RentalInfo>();
        for(int i = 0; i< rentalInfosList.size(); i++){
            RentalInfo rental = rentalInfosList.get(i);
            if(rental.getMovie() == movie){
                res.add(rental);
            }
        }

        Collections.sort(res, new RentalInfosComparator());
        return res;
    }

    public int addUser(String firstName, String lastName){
        User client = new User();
        client.setName(firstName+" "+lastName);
        usersList.add(client);
        return client.getId();
    }

    public void deleteUser(int id) throws IllegalArgumentException{
        for(int i = 0; i< usersList.size(); i++){
            if(usersList.get(i).getId() == id){
                usersList.get(i).delete();
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    public ArrayList<User> searchUserByFirstName(String firstName){
        ArrayList<User> res = new ArrayList<User>();
        for(int i = 0; i< usersList.size(); i++){
            User client = usersList.get(i);
            if(!client.getDeletedState()){
                if(client.getFirstName().equals(firstName)){
                    res.add(client);
                }
            }
        }
        return res;
    }


    public ArrayList<User> searchUserByLastName(String lastName){
        ArrayList<User> res = new ArrayList<User>();
        for(int i = 0; i < usersList.size(); i++){
            User user = usersList.get(i);
            if(!user.getDeletedState()){
                if(user.getLastName().equals(lastName)){
                    res.add(user);
                }
            }
        }
        return res;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> res = new ArrayList<User>();
        for(int i = 0; i< usersList.size(); i++){
            if(!usersList.get(i).getDeletedState()){
                res.add(usersList.get(i));
            }
        }
        return res;
    }
}
