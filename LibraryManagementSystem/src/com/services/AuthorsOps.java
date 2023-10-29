package com.services;

import java.sql.*;
import java.util.*;

import com.model.Authors;

//import DatabaseConnector.ConnectionProvider;

public class AuthorsOps implements LibraryDataOperations {
   
    Scanner sc = new Scanner(System.in);
    Authors a=new Authors();
    

    @Override
    public void add_data() {
       
            
            
            try {
                System.out.print("Enter Name of Author: ");
                a.setAuthorName(sc.nextLine().trim());

                System.out.print("Enter Nationality of Author: ");
                a.setNationality(sc.nextLine().trim());
                
                while(a.getAuthorName().isEmpty() || a.getNationality().isEmpty())
                {
             	   System.out.print("Enter Updated Name of Author: ");
	                    a.setAuthorName(sc.nextLine().trim());

	                    System.out.print("Enter Updated Nationality of Author: ");
	                    a.setNationality(sc.nextLine().trim());
	                    
	                    if (a.getAuthorName().isEmpty() || a.getNationality().isEmpty()) {
	                        System.out.println("Invalid input. Please enter non-empty values.");
	                        // Skip this iteration and prompt for input again
	                        continue;
	                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid value.");
                sc.nextLine(); // Clear the input buffer
                return; // Return to the caller without processing further
            }
            
            String insertAuthorSQL = "INSERT INTO Authors (AuthorName, Nationality) VALUES (?, ?)";

            try {
            	Class.forName ("com.mysql.cj.jdbc.Driver");
          	   final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
          	
          	 Connection connection=DriverManager.getConnection(JDBC_URL,"root","root");
          	 PreparedStatement preparedStatement = connection.prepareStatement(insertAuthorSQL);
                preparedStatement.setString(1, a.getAuthorName());
                preparedStatement.setString(2, a.getNationality());

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Author added successfully!");
                } else {
                    System.out.println("Failed to add author.");
                }
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
            	System.out.println(e);
            } catch (ClassNotFoundException e) {
            	System.out.println(e);
			}
        }

	@Override
	public void view_data() {
		try {
	        // Connect to the database
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
	        Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

	        // SQL query to select all authors
	        String selectAllAuthorsQuery = "SELECT AuthorID, AuthorName, Nationality FROM Authors";

	        PreparedStatement preparedStatement = connection.prepareStatement(selectAllAuthorsQuery);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Column headers
	        System.out.printf("%-10s%-30s%-20s%n", "Author ID", "Author Name", "Nationality");
	        System.out.println("----------------------------------------------------------------");

	        // Iterate through the result set and print author data in tabular format
	        while (resultSet.next()) {
	            int authorId = resultSet.getInt("AuthorID");
	            String authorName = resultSet.getString("AuthorName");
	            String nationality = resultSet.getString("Nationality");

	            System.out.printf("%-10d%-30s%-20s%n", authorId, authorName, nationality);
	        }

	        resultSet.close();
	        preparedStatement.close();
	        connection.close();
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    } catch (ClassNotFoundException e) {
	        System.out.println("Class Not Found: " + e.getMessage());
	    }

		
	}

	@Override
	public void update_data() {


		 System.out.println("Enter Author ID to Update Details:");
	        int authorId = sc.nextInt();
	        sc.nextLine(); // Consume the newline character
	        boolean isavailable=false;

	        try {
	            // Connect to the database
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
	            Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

	            // Check if the author with the provided AuthorID exists
	            String checkAuthorQuery = "SELECT * FROM authors WHERE AuthorID = ?";
	            PreparedStatement checkAuthorStatement = connection.prepareStatement(checkAuthorQuery);
	            checkAuthorStatement.setInt(1, authorId);
	            ResultSet resultSet = checkAuthorStatement.executeQuery();
	           
	            System.out.printf("%-10s%-25s%-30s%n",
	                    "Author ID", "Author Name", "Nationality");
	            System.out.println("--------------------------------------------------------------------------------------------");
	            
	            
	            while (resultSet.next()) {
	            	isavailable=true;
	                int authorID = resultSet.getInt("AuthorID");
	                String authorName = resultSet.getString("AuthorName");
	                String nationality = resultSet.getString("Nationality");

	                System.out.printf("%-10s%-25s%-30s%n",
	                        authorID, authorName, nationality);
	                
	                try {
	                	System.out.print("Enter Updated Name of Author: ");
	                    a.setAuthorName(sc.nextLine().trim());

	                    System.out.print("Enter Updated Nationality of Author: ");
	                    a.setNationality(sc.nextLine().trim());
	                    
	                   while(a.getAuthorName().isEmpty() || a.getNationality().isEmpty())
	                   {
	                	   System.out.print("Enter Updated Name of Author: ");
		                    a.setAuthorName(sc.nextLine().trim());

		                    System.out.print("Enter Updated Nationality of Author: ");
		                    a.setNationality(sc.nextLine().trim());
		                    
		                    if (a.getAuthorName().isEmpty() || a.getNationality().isEmpty()) {
		                        System.out.println("Invalid input. Please enter non-empty values.");
		                        // Skip this iteration and prompt for input again
		                        continue;
		                    }
	                   }

	                } catch (InputMismatchException e) {
	                    System.out.println("Invalid input. Please enter a valid value.");
	                    sc.nextLine(); // Clear the input buffer
	                    return; // Return to the caller without processing further
	                }
	                

	                String updateAuthorSQL = "UPDATE authors SET AuthorName = ?, Nationality = ? " +
	                        "WHERE AuthorID = ?";
	                PreparedStatement updateAuthorStatement = connection.prepareStatement(updateAuthorSQL);
	                updateAuthorStatement.setString(1, a.getAuthorName());
	                updateAuthorStatement.setString(2, a.getNationality());
	                updateAuthorStatement.setInt(3, authorID);

	                int rowsUpdated = updateAuthorStatement.executeUpdate();

	                if (rowsUpdated > 0) {
	                    System.out.println("Author information updated successfully!");
	                } else {
	                    System.out.println("Failed to update Author information.");
	                }

	                updateAuthorStatement.close();
	            }
	            
	            if (!isavailable) {
	                System.out.println("Author with AuthorID " + authorId + " does not exist.");
	            }
	            

	           

	            resultSet.close();
	            checkAuthorStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            System.out.println("SQL Error: " + e.getMessage());
	            e.printStackTrace(); // Print the full stack trace for debugging
	        } catch (ClassNotFoundException e) {
	            System.out.println("Class Not Found: " + e.getMessage());
	        }
		}
}

	
    

