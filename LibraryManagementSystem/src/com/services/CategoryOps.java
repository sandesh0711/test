package com.services;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.model.Category;

//import DatabaseConnector.ConnectionProvider;

public class CategoryOps implements LibraryDataOperations {
	
	Scanner scanner = new Scanner(System.in);
	Category c=new Category();
	@Override
	public void add_data() {
		
	
		 

            
            try {
            	 System.out.print("Enter Category: ");
                 c.setCategoryname(scanner.nextLine().trim());

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid value.");
                scanner.nextLine(); // Clear the input buffer
                return; // Return to the caller without processing further
            }

            

            String insertcategorySQL = "INSERT INTO category (CategoryName) VALUES (?)";

                      
            try {
            	Class.forName ("com.mysql.cj.jdbc.Driver");
         	   final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
         	
         	 Connection connection=DriverManager.getConnection(JDBC_URL,"root","root");
         	PreparedStatement preparedStatement = connection.prepareStatement(insertcategorySQL);
                preparedStatement.setString(1, c.getCategoryname());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
                System.out.println("Category added successfully!");
            } catch (SQLException e) {
            	System.out.println(e);
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
	        String selectAllAuthorsQuery = "SELECT * FROM Category";

	        PreparedStatement preparedStatement = connection.prepareStatement(selectAllAuthorsQuery);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Column headers
	        System.out.printf("%-10s%-30s%n", "Category ID", "Category Name");
	        System.out.println("----------------------------------------------------------------");

	        // Iterate through the result set and print author data in tabular format
	        while (resultSet.next()) {
	            int CategoryID = resultSet.getInt("CategoryID");
	            String CategoryName = resultSet.getString("CategoryName");

	            System.out.printf("%-10d%-30s%n", CategoryID, CategoryName);
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


