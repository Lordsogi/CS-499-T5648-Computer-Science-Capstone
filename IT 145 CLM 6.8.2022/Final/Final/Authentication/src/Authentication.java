/* This program is designed to prompt the user for a login and password, and if 
they correctly input a specific password and username it will display relevant
information for each given role.
*/

import java.util.Scanner; // allows for the use of a scanner further in the code

public class Authentication { //creates the main class
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in); //creates the scanner
        String userChar = (""); //creates the variable that controlls the main loop
        while (!userChar.equals("Yes")) { //enters the loop
            System.out.print("Login? (Yes / No)"); //prompts the user for the ability to exit the progam
            userChar = scnr.next();
            if (userChar.equals("No")) { //allows the program to exit while in the loop
                System.out.println("Goodbye");
                System.exit(0); //leaves the loop
            } else if (userChar.equals("Yes")) { //continutes the loop to the next stage
                try {
                    int counter = 0;
                    while (counter < 3) {
                      	System.out.println("Please enter a Username.");
                    	String userUserName = scnr.next(); //inputs the username
                    	System.out.println("Please enter a Password.");
                    	String userPassword = scnr.next(); //inputs the scanner
                        ValidateCredentials validator = new ValidateCredentials();
                        boolean valid = validator.isCredentialsValid(userUserName, userPassword);
                        if (valid) {
                            break;
                        } else if (counter >= 2) {
                            System.out.println("Too many invalid attempts, goodbye!");
                          	break;
                        } else {
                            counter = counter + 1;
                        }
                    }
                } catch (Exception e) {
                	System.out.println("Error while reading the files");
                    System.out.println(e);
                }
            } else { //restarts the loop and directs the user for a correct input
                System.out.println("Please enter a acceptable answer!");
            }
        }
        //System.out.println(e.printStackTrace());
    }
}