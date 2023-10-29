package com.services;
import java.sql.*;

import java.util.*;

import com.model.Authors;
import com.model.Books;


//import DatabaseConnector.ConnectionProvider;
public class BooksOps  implements LibraryDataOperations{
	
	//private int bookid;
	
	Scanner sc=new Scanner(System.in);
	Books b=new Books();
	Authors a=new Authors();
	AuthorsOps authr = new AuthorsOps();
	//CategoryOps categry=new CategoryOps();
	@Override
	public void add_data() {
	
		Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
	        connection = DriverManager.getConnection(JDBC_URL, "root", "root");

	        System.out.print("Enter Title of the book: ");
	        b.setTitle(sc.nextLine().trim());
	        while(b.getTitle().isEmpty() /*|| b.getQuantity().isEmpty() || b.getPublicationyear().isEmpty() */)
            {
	        	 if (b.getTitle().isEmpty()  ) {
                     System.out.println("Invalid input. Please enter non-empty values.");
                     // Skip this iteration and prompt for input again
                    // continue;
                 }
	        	System.out.print("Enter Title of the book: ");
		        b.setTitle(sc.nextLine().trim());
	        	 	                    
                   
            }

	        System.out.print("Enter author name of the book: ");
	        a.setAuthorName(sc.nextLine().trim());

	        

	        // Check if author exists in the Authors table
	        String checkAuthorSQL = "SELECT AuthorID, Nationality FROM Authors WHERE AuthorName = ?";
	        preparedStatement = connection.prepareStatement(checkAuthorSQL);
	        preparedStatement.setString(1, a.getAuthorName());
	        ResultSet authorResult = preparedStatement.executeQuery();

	        if (authorResult.next()) {
	            a.setAuthoeid(authorResult.getInt("AuthorID")); 
	        } else {
	            // Author doesn't exist; prompt for nationality and insert into Authors table
	            System.out.print("Enter author's nationality: ");
	            a.setNationality(sc.nextLine().trim());
	            
	            while(a.getAuthorName().isEmpty() || a.getNationality().isEmpty())
                {
	            	if (a.getAuthorName().isEmpty() || a.getNationality().isEmpty()) {
                        System.out.println("Invalid input. Please enter non-empty values.");
                        // Skip this iteration and prompt for input again
                      
                    }
             	   System.out.print("Enter Updated Name of Author: ");
	                    a.setAuthorName(sc.nextLine().trim());

	                    System.out.print("Enter Updated Nationality of Author: ");
	                    a.setNationality(sc.nextLine().trim());
	                    
	                    
                }

	            String insertAuthorSQL = "INSERT INTO Authors (AuthorName, Nationality) VALUES (?, ?)";
	            preparedStatement = connection.prepareStatement(insertAuthorSQL, PreparedStatement.RETURN_GENERATED_KEYS);
	            preparedStatement.setString(1, a.getAuthorName());
	            preparedStatement.setString(2, a.getNationality());
	            int rowsInserted = preparedStatement.executeUpdate();

	            if (rowsInserted > 0) {
	                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    a.setAuthoeid(generatedKeys.getInt(1));
	                } else {
	                    throw new SQLException("Failed to insert author and retrieve generated ID.");
	                }
	            } else {
	                throw new SQLException("Failed to insert author.");
	            }
	        }

	       System.out.print("Enter publication year of the book: ");
		        b.setPublicationyear(sc.nextInt());
		        sc.nextLine(); // Consume the newline character left in the buffer

		        System.out.print("Enter category of the book: ");
		        b.setCategoryname(sc.nextLine().trim());

		        System.out.print("Enter quantity of the book: ");
		        b.setQuantity(sc.nextInt());
		        
		       

		       

		        // Check if category exists in the Category table
		        String checkCategorySQL = "SELECT CategoryID FROM Category WHERE CategoryName = ?";
		        preparedStatement = connection.prepareStatement(checkCategorySQL);
		        preparedStatement.setString(1, b.getCategoryname());
		        ResultSet categoryResult = preparedStatement.executeQuery();

		        int categoryID;
		        if (categoryResult.next()) {
		            categoryID = categoryResult.getInt("CategoryID");
		        } else {
		            // Category doesn't exist; insert into Category table
		            String insertCategorySQL = "INSERT INTO Category (CategoryName) VALUES (?)";
		            preparedStatement = connection.prepareStatement(insertCategorySQL, PreparedStatement.RETURN_GENERATED_KEYS);
		            preparedStatement.setString(1, b.getCategoryname());
		            int rowsInserted = preparedStatement.executeUpdate();

		            if (rowsInserted > 0) {
		                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		                if (generatedKeys.next()) {
		                    categoryID = generatedKeys.getInt(1);
		                } else {
		                    throw new SQLException("Failed to insert category and retrieve generated ID.");
		                }
		            } else {
		                throw new SQLException("Failed to insert category.");
		            }
		        }

		        // Insert the book using authorID and categoryID
		        String insertBookSQL = "INSERT INTO Books (Title, AuthorID, PublicationYear, CategoryID, Quantity) " +
		                "VALUES (?, ?, ?, ?, ?)";
		        preparedStatement = connection.prepareStatement(insertBookSQL);
		        preparedStatement.setString(1, a.getAuthorName());
		        preparedStatement.setInt(2, a.getAuthoeid());
		        preparedStatement.setInt(3, b.getPublicationyear());
		        preparedStatement.setInt(4, categoryID);
		        preparedStatement.setInt(5, b.getQuantity());

		       // int rowsInserted = preparedStatement.executeUpdate();

	        int rowsInserted = preparedStatement.executeUpdate();
	        preparedStatement.close();
            connection.close();
	        if (rowsInserted > 0) {
	            System.out.println("A new book was inserted successfully!");
	        } else {
	            System.out.println("Failed to insert the book.");
	        }
	    } catch (InputMismatchException e) {
	        System.out.println("Invalid input. Please enter a valid value.");
	    } catch (SQLException e) {
	        System.out.println("SQL error: " + e.getMessage());
	    } catch (ClassNotFoundException e) {
	        System.out.println("Class not found: " + e.getMessage());
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	           // sc.close();
	        } catch (SQLException e) {
	            System.out.println("Error closing database resources: " + e.getMessage());
	        }
	    }
		
		
	}
	@Override
	public void view_data() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";

	        Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

	        String selectAllBooksQuery = "SELECT b.BookID, b.Title, a.AuthorName, b.PublicationYear, c.CategoryName, b.Quantity, b.Borrowed, b.update_dt " +
	        	    "FROM Books b " +
	        	    "left JOIN Authors a ON b.AuthorID = a.AuthorID " +
	        	    "left JOIN Category c ON b.CategoryID = c.CategoryID";


	        PreparedStatement preparedStatement = connection.prepareStatement(selectAllBooksQuery);
	        ResultSet resultSet = preparedStatement.executeQuery();
	       
	        // Column headers
	        //String header = "Book ID\tTitle\tAuthor\tPublication Year\tCategory\tQuantity";
	        //System.out.println(header);
	        System.out.printf("%-8s%-20s%-20s%-25s%-15s%-10s%-10s%-15s%n",
	                "Book ID", "Title", "Author", "Publication Year", "Category", "Quantity","Borrowed","Update_dt");
	        System.out.println("------------------------------------------------------------------------------------------------------");
	       
	 

	        // Iterate through the result set and print book data in tabular format
	        while (resultSet.next()) {
	            int bookId = resultSet.getInt("BookID");
	            String title = resultSet.getString("Title");
	            String authorName = resultSet.getString("AuthorName");
	            int publicationYear = resultSet.getInt("PublicationYear");
	            String categoryName = resultSet.getString("CategoryName");
	            int quantity = resultSet.getInt("Quantity");
	            int Borrowed = resultSet.getInt("Borrowed");
	            String update_dt = resultSet.getString("update_dt");

	            System.out.printf("%-8d%-20s%-20s%-25d%-15s%-10d%-10s%-15s%n",
	                    bookId, title, authorName, publicationYear, categoryName, quantity,Borrowed,update_dt);
	            
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
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
	
	

}
