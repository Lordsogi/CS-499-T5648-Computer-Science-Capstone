/* This includes all of the imports requried to have tools avalible for further
coding, and imports the method from MD5Digest to allow for password hasing
*/
import java.security.MessageDigest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ValidateCredentials { //class used for validating credentials and comparing to sql file

    private boolean isValid; //boolean to break loop if valid
    private String filePath; //filepath to load sql file
    final private String credentialsFileName; //file name itself

    public ValidateCredentials() { //initializing and giving values to previously created variable
        filePath = "";
        isValid = false;
        credentialsFileName = "credentials.txt";
    }
//Md5 digest class was condensed into this function, here is where the hash from the password is created.
    public boolean isCredentialsValid(String userName, String passWord) throws Exception {
        String original = passWord;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        System.out.println("");
        System.out.println("original:" + original);
        System.out.println("digested:" + sb.toString()); //sb.toString() is what you'll need to compare password strings

        isValid = readDataFiles(userName, sb.toString());

        return isValid;
    }
//this function is to actually pull the information from the SQL file and load it into Java
    public boolean readDataFiles(String userName, String passWord) throws IOException {
        FileInputStream fileByteStream1 = null; // File input stream
        FileInputStream fileByteStream2 = null; // File input stream

        Scanner inFS1 = null;                   // Scanner object
        Scanner inFS2 = null;                   // Scanner object

        String textLine = null;
        String textFileName = null;
        
        boolean foundCredentials = false;

        // Try to open file
        System.out.println("");
        System.out.println("Opening file " + credentialsFileName);
        fileByteStream1 = new FileInputStream(credentialsFileName);
        inFS1 = new Scanner(fileByteStream1);

        System.out.println("");
        System.out.println("Reading lines of text.");

        while (inFS1.hasNextLine()) {
            textLine = inFS1.nextLine();
            //System.out.println(textLine);

            String[] words = textLine.split(",");//splits the string based on whitespace

            if (words[0].equals(userName) && textLine.contains(passWord)) {
                foundCredentials = true;
                int last = words.length - 1;
                textFileName = words[last];
                break;
            }
        }

        // Done with file, so try to close it
        System.out.println("");
        System.out.println("Closing file " + credentialsFileName);

        if (textLine != null) {
            fileByteStream1.close(); // close() may throw IOException if fails
        }

        if (foundCredentials) {
            // Try to open file
            System.out.println("");
            System.out.println("Opening file " + textFileName);
            
            fileByteStream2 = new FileInputStream(filePath + textFileName);
            inFS2 = new Scanner(fileByteStream2);

            System.out.println("");

            while (inFS2.hasNextLine()) {
                textLine = inFS2.nextLine();
                System.out.println(textLine);
            }

            // Done with file, so try to close it
            System.out.println("");
            System.out.println("Closing file " + textFileName);

            if (textLine != null) {
                fileByteStream2.close(); // close() may throw IOException if fails
            }
        }

        return foundCredentials;
    }

}
