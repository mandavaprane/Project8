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
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Files location: ");
            String filesLocation = scanner.nextLine();

            System.out.println("Action codes:");
            System.out.println("Add user:                  1");
            System.out.println("Delete user:               2");
            System.out.println("Search user by first name: 3");
            System.out.println("Search user by last name:  4");
            System.out.println("Get all users:             5");
            System.out.println("Add movie:                 6");
            System.out.println("Rent movie:                7");
            System.out.println("Return rented movie:       8");
            System.out.println("Get all movies:            9");
            System.out.println("Get rented movies         10");
            System.out.println("Get rent history:         11");
            System.out.println("Exit:                     12");

            Manager manager = new Manager(filesLocation);
            for(;;){
                System.out.println("Your action:");
                int actionCode = scanner.nextInt();

                try {
                    switch (actionCode) {
                        case 1: {
                            System.out.println("First name:");
                            String firstName = scanner.next();
                            System.out.println("Last name:");
                            String lastName = scanner.next();
                            manager.addUser(firstName, lastName);
                            break;
                        }
                        case 2: {
                            System.out.println("User id:");
                            int id = scanner.nextInt();
                            manager.deleteUser(id);
                            break;
                        }
                        case 3: {
                            System.out.println("First name:");
                            String name = scanner.next();
                            manager.searchUserByFirstName(name);
                            break;
                        }
                        case 4: {
                            System.out.println("Last name:");
                            String name = scanner.next();
                            manager.searchUserByLastName(name);
                            break;
                        }
                        case 5: {
                            manager.getAllUsers();
                            break;
                        }
                        case 6: {
                            System.out.println("Title (no spaces):");
                            String title = scanner.next();
                            manager.addMovie(title);
                            break;
                        }
                        case 7: {
                            System.out.println("User id:");
                            int userId = scanner.nextInt();
                            System.out.println("Movie id:");
                            int movieId = scanner.nextInt();
                            System.out.println("Date:");
                            String date = scanner.next();
                            manager.rentMovie(userId, movieId, date);
                            break;
                        }
                        case 8: {
                            System.out.println("Movie id:");
                            int movieId = scanner.nextInt();
                            System.out.println("Date:");
                            String date = scanner.next();
                            manager.returnMovie(movieId, date);
                            break;
                        }
                        case 9: {
                            manager.getAllMovies();
                            break;
                        }
                        case 10: {
                            manager.getRentedMovies();
                            break;
                        }
                        case 11: {
                            System.out.println("Movie id:");
                            int movieId = scanner.nextInt();
                            manager.getRentHistory(movieId);
                            break;
                        }
                        case 12: {
                            System.exit(0);
                            break;
                        }
                        default:
                            break;
                    }
                }
                catch (Exception e){
                    System.out.println("Incorrect input");
                    System.exit(0);
                }
            }
        }
        catch (IOException ex1){
            System.out.println("Some file did not found");
        }
    }
}
