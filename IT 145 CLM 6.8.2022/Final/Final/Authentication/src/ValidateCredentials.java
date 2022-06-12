/* This includes all of the imports requried to have tools avalible for further
coding, and imports the method from MD5Digest to allow for password hasing

*/
import java.security.MessageDigest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ValidateCredentials {

    private boolean isValid;
    private String filePath;
    final private String credentialsFileName;

    public ValidateCredentials() {
        filePath = "";
        isValid = false;
        credentialsFileName = "credentials.txt";
    }

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


/*

import java.sql.*;

/**
 * A Java MySQL SELECT statement example.
 * Demonstrates the use of a SQL SELECT statement against a
 * MySQL database, called from a Java program.
 * 
 * Created by Alvin Alexander, http://alvinalexander.com
 */
public class JavaMysqlSelectExample
{

  public static void main(String[] args)
  {
    try
    {
      // create our mysql database connection
      String myDriver = "org.gjt.mm.mysql.Driver";
      String myUrl = "jdbc:mysql://localhost/test";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "");
      
      // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT * FROM users";

      // create the java statement
      Statement st = conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      
      // iterate through the java resultset
      while (rs.next())
      {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Date dateCreated = rs.getDate("date_created");
        boolean isAdmin = rs.getBoolean("is_admin");
        int numPoints = rs.getInt("num_points");
        
        // print the results
        System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
      }
      st.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
  }
}

*/