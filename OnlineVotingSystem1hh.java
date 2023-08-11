import java.util.*;
import java.sql.*;
//``import java.io.*;


public class OnlineVotingSystem1hh {
   static final String DB_URL = "jdbc:mysql://localhost/voting_system"; // Replace with your own database URL
   static final String USER = "root"; // Replace with your own username
   static final String PASS = "nandini@123_mokhamatam"; // Replace with your own password
   static Scanner scanner = new Scanner(System.in);
   public static void main(String[] args) {
      
      Connection conn = null;
      Statement stmt = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL,USER,PASS);
         stmt = conn.createStatement();

         // Create tables if they don't exist
         String createVotersTable = "CREATE TABLE IF NOT EXISTS voters " +
                                    "(voter_id INT NOT NULL AUTO_INCREMENT, " +
                                    " name VARCHAR(255), " +
                                    " email VARCHAR(255), " +
                                    " age INT, " +
                                    " PRIMARY KEY ( voter_id ))";
         stmt.executeUpdate(createVotersTable);

         String createCandidatesTable = "CREATE TABLE IF NOT EXISTS candidates " +
                                    "(candidate_id INT NOT NULL AUTO_INCREMENT, " +
                                    " name VARCHAR(255), " +
                                    " party VARCHAR(255), " +
                                    " PRIMARY KEY ( candidate_id ))";
         stmt.executeUpdate(createCandidatesTable);

         String createVotesTable = "CREATE TABLE IF NOT EXISTS votes " +
                                    "(vote_id INT NOT NULL AUTO_INCREMENT, " +
                                    " voter_id INT, " +
                                    " candidate_id INT, " +
                                    " FOREIGN KEY (voter_id) REFERENCES voters(voter_id), " +
                                    " FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id), " +
                                    " PRIMARY KEY ( vote_id ))";
         stmt.executeUpdate(createVotesTable);

         // Display the main menu
         //login();
         displayMenu();
         // Close all connections
         stmt.close();
         conn.close();
      } catch(SQLException se) {
         se.printStackTrace();
      } catch(Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if(stmt!=null) stmt.close();
         } catch(SQLException se2) {
         }
         try {
            if(conn!=null) conn.close();
         } catch(SQLException se) {
            se.printStackTrace();
         }
      }
   }
   
  
   public static void displayMenu() {
      System.out.print("\033[H\033[2J");
        System.out.flush();
      System.out.println("Welcome to the Online Voting System");
      System.out.println("1. Register as a voter");
      System.out.println("2. Register as a candidate");
      System.out.println("3. View all registered voters");
      System.out.println("4. View all registered candidates");
      System.out.println("5. Cast your vote");
      System.out.println("6. View the election results");
      System.out.println("7. Remove Voters");
      System.out.println("0. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();
      switch (choice) {
         case 1:
            registerVoter();
            break;
         case 2:
            registerCandidate();
            break;
         case 3:
            viewVoters();
            break;
         case 4:
            viewCandidates();
            break;
         case 5:
            login();
            break;
         case 6:
            viewResults();
            break;
         case 7:
            removeuser();
            break;
         case 0:
            System.out.println("Thank you for using the Online Voting System");
            break;
         default:
            System.out.println("Invalid choice");
            break;
      }
   }

   public static void removeuser()
   {
      System.out.println("WELCOME TO DELETE RECORDS");
        // Prompt the user to enter the registration number of the user they want to remove
        System.out.println("Enter the following to search for record");
        System.out.println("Enter Voter Id : ");
        int id = scanner.nextInt();
        // Define a SQL query to delete the user with the given registration number from the database
        String delete_query = "DELETE FROM voters WHERE voter_id='"+id+"'";
        try {
            // Load the MySQL JDBC driver
            Class.forName("java.sql.Driver");
        } catch (ClassNotFoundException e) {
            // Print the stack trace if an exception is thrown
            e.printStackTrace();
        }
        try {
            // Establish a connection to the database using the URL, username, and password
            Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
            // Create a statement object to execute the SQL query
            Statement st = con.createStatement();
            // Execute the SQL query and store the number of rows affected
            int rs = st.executeUpdate(delete_query);
            if(rs == 0)
            {
               System.out.println("No voter found!");
            }
            else{
            // Display the number of rows affected to the user
            System.out.println("Voter Id : "+id+" can no longer vote");
            // Close the statement object
            st.close();
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception is thrown
            e.printStackTrace();
        }
        System.out.println("Press 0 to go back to Main Menu...");
   scanner.nextInt();
        displayMenu();
   }
   public static void login() {
      System.out.println("USER NAME :");
      String username = scanner.next();
      System.out.println("PASSWORD : ");
      String pass = scanner.next();
      String jdbc_usr = "";
      String jdbc_pass = "";
      String query = "SELECT * from login where aadhar = '" + username + "' and pass = '" + pass + "'";
      try {
          // Load the MySQL JDBC driver
          Class.forName("java.sql.Driver");
      } catch (ClassNotFoundException e) {
          // Print the stack trace if an exception is thrown
          e.printStackTrace();
      }
      try {
          // Establish a connection to the database using the URL, username, and password
          Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
          // Create a statement object to execute the SQL query
          Statement st = con.createStatement();
          ResultSet rs = st.executeQuery(query);
          // Check if the ResultSet has any rows
          if (rs.next()) {
              jdbc_usr = rs.getString(1); // Retrieve the first column using column index
              jdbc_pass = rs.getString(2); // Retrieve the second column using column index

              if (username.equals(jdbc_usr) && pass.equals(jdbc_pass)) {
               castVote();
              }
               else{
                  login();
               System.out.println("LLLLLLLOOOL");
               }
           
          }
          else
          {
            System.out.println("WRONNNNGGGG! TRY AGAIN!");
            login();
          }
      } catch (SQLException e) {
          // Print the stack trace if an exception is thrown
          e.printStackTrace();
      }
  
      }
   public static void registerVoter() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL,USER,PASS);
              System.out.print("Enter voter name: ");
     String name = System.console().readLine();
     System.out.print("Enter voter email: ");
     String email = System.console().readLine();
     System.out.print("Enter voter age: ");
     int age = Integer.parseInt(System.console().readLine());
         if(age < 18)
         {
            System.out.println("You are not eligibile to vote!");
            registerVoter();
         }
         
     // Insert new voter into database
     String insertVoter = "INSERT INTO voters (name, email, age) VALUES (?, ?, ?)";
     stmt = conn.prepareStatement(insertVoter);
     stmt.setString(1, name);
     stmt.setString(2, email);
     stmt.setInt(3, age);
     int rows = stmt.executeUpdate();
     if (rows > 0) {
        System.out.println("Voter registered successfully");
     } else {
        System.out.println("Error registering voter");
     }

     // Close all connections
     stmt.close();
     conn.close();
  } catch(SQLException se) {
     se.printStackTrace();
  } catch(Exception e) {
     e.printStackTrace();
  } finally {
     try {
        if(stmt!=null) stmt.close();
     } catch(SQLException se2) {
     }
     try {
        if(conn!=null) conn.close();
     } catch(SQLException se) {
        se.printStackTrace();
     }
  }
   System.out.println("Press 0 to go back to Main Menu...");
   scanner.nextInt();
   displayMenu();
}


public static void registerCandidate() {
   System.out.print("\033[H\033[2J");
      System.out.flush();
Connection conn = null;
PreparedStatement stmt = null;
try {
Class.forName("com.mysql.jdbc.Driver");
conn = DriverManager.getConnection(DB_URL,USER,PASS);
System.out.print("Enter candidate name: ");
String name = System.console().readLine();// scanner.nextLine();
System.out.print("Enter candidate party: ");
String party = System.console().readLine();// scanner.nextLine();


     // Insert new candidate into database
     String insertCandidate = "INSERT INTO candidates (name, party) VALUES (?, ?)";
     stmt = conn.prepareStatement(insertCandidate);
     stmt.setString(1, name);
     stmt.setString(2, party);
     int rows = stmt.executeUpdate();
     if (rows > 0) {
        System.out.println("Candidate registered successfully");
     } else {
        System.out.println("Error registering candidate");
     }

     // Close all connections
     stmt.close();
     conn.close();
  } catch(SQLException se) {
     se.printStackTrace();
  } catch(Exception e) {
     e.printStackTrace();
  } finally {
     try {
        if(stmt!=null) stmt.close();
     } catch(SQLException se2) {
     }
     try {
        if(conn!=null) conn.close();
     } catch(SQLException se) {
        se.printStackTrace();
     }
  }
  System.out.println("Press 0 to go back to Main Menu...");
   scanner.nextInt();
  displayMenu();
}

public static void viewVoters() {
   System.out.print("\033[H\033[2J");
      System.out.flush();
Connection conn = null;
Statement stmt = null;
try {
Class.forName("com.mysql.jdbc.Driver");
conn = DriverManager.getConnection(DB_URL,USER,PASS);

     // Retrieve all voters from database
     String selectVoters = "SELECT * FROM voters";
     stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery(selectVoters);

     // Print all voters
     while(rs.next()) {
        int id  = rs.getInt("voter_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        int age = rs.getInt("age");
        System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Age: " + age);
     }

     // Close all connections
     rs.close();
     stmt.close();
     conn.close();
  } catch(SQLException se) {
     se.printStackTrace();
  } catch(Exception e) {
     e.printStackTrace();
  } finally {
     try {
        if(stmt!=null) stmt.close();
} catch(SQLException se2) {
     }
     try {
        if(conn!=null) conn.close();
     } catch(SQLException se) {
        se.printStackTrace();
}
  }
  System.out.println("Press 0 to go back to Main Menu...");
   scanner.nextInt();
  displayMenu();
}
public static void viewCandidates()  {
   System.out.print("\033[H\033[2J");
      System.out.flush();
Connection conn = null;
Statement stmt = null;
try {
Class.forName("com.mysql.jdbc.Driver");
conn = DriverManager.getConnection(DB_URL,USER,PASS);
String query = "SELECT * FROM candidates";
 stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(query);
  
            while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
           // String email = rs.getString("email");
            //String phone =// rs.getString("phone");
           // String resume = rs.getString("resume");
            System.out.println("ID: " + id + ", Name: " + name );
        }

 rs.close();
     stmt.close();
     conn.close();
  } catch(SQLException se) {
     se.printStackTrace();
  } catch(Exception e) {
     e.printStackTrace();
  } finally {
     try {
        if(stmt!=null) stmt.close();
} catch(SQLException se2) {
     }
     try {
        if(conn!=null) conn.close();
     } catch(SQLException se) {
        se.printStackTrace();
     }
  }
  System.out.println("Press 0 to go back to Main Menu...");
   scanner.nextInt();
  displayMenu();
}
public static void castVote() {
   System.out.print("\033[H\033[2J");
      System.out.flush();
Connection conn = null;
        Statement stmt = null;

         
         System.out.println("Enter your voter ID: ");
           int voterId = scanner.nextInt();
           System.out.println("Enter the candidate ID you wish to vote for: ");
           int candidateId = scanner.nextInt();
           
          
           try {
             Class.forName("com.mysql.jdbc.Driver");
              conn = DriverManager.getConnection(DB_URL,USER,PASS);


               // Executing SQL query to insert the vote into votes table
               String sql = "INSERT INTO votes (voter_id, candidate_id) VALUES (" + voterId + ", " + candidateId + ")";
               stmt = conn.createStatement();
               int rowsAffected = stmt.executeUpdate(sql);

               if (rowsAffected == 1) {
                   System.out.println("Vote has been cast successfully!");
               } else {
                   System.out.println("Unable to cast vote. Please try again.");
               }
  // Close all connections
    stmt.close();
    conn.close();
  } catch(SQLException se) {
    se.printStackTrace();
  } catch(Exception e) {
    e.printStackTrace();
  } finally {
    try {
           if(stmt!=null) stmt.close();
    } catch(SQLException se2) {
    }
    try {
           if(conn!=null) conn.close();
    } catch(SQLException se) {
           se.printStackTrace();
    }
  }
  System.out.println("Press 0 to go back to Main Menu...");
   scanner.nextInt();
  displayMenu();
      }
      


public static void viewResults() {
   System.out.print("\033[H\033[2J");
      System.out.flush();
    try {
        Connection conn = null;
        Statement stmt = null;
       // Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

         stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT candidate_id, COUNT(*) AS num_votes FROM votes GROUP BY candidate_id");

        System.out.println("Results:");
        while (rs.next())
        {
            String candidates = rs.getString("candidate_id");
            int numVotes = rs.getInt("num_votes");
            System.out.println(candidates + ": " + numVotes + " votes");
        }

         // Close all connections
     
     conn.close();
 
     rs.close();
        stmt.close();
    } catch (SQLException e) {
        System.out.println("Error viewing results: " + e.getMessage());
    }
    System.out.println("Press 0 to go back to Main Menu...");
   scanner.nextInt();
    displayMenu();
  }
   
public void exit() {
    try {
         Connection conn = null;
        Statement stmt = null;
      //  Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

         stmt = conn.createStatement();
        // Close the Statement object
        if (stmt != null) {
            stmt.close();
        }
        // Close the Connection object
        if (conn != null) {
            conn.close();
        }
        System.out.println("Database connection closed.");
    } catch (SQLException e) {
        System.out.println("Error closing database connection: " + e.getMessage());
    }
    // Exit the program
    System.exit(0);
    
}
}