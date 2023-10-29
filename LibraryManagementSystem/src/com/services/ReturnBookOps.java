package com.services;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

import com.model.LibraryUtils;
import com.model.Returnb;

public class ReturnBookOps{
	Scanner sc=new Scanner(System.in);

	public void ReturnBooks() {
		Returnb r=new Returnb();
	    LibraryUtils l = new LibraryUtils();

	    System.out.println("Please enter the Borrowed Book ID: ");
	    r.setBookid(sc.nextInt());

	    System.out.println("Please enter the Member ID: ");
	    r.setMemberid(sc.nextInt());

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";

	        Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

	        // Check if the book is borrowed and not yet returned
	        String checkBorrowedQuery = "SELECT * FROM BorrowedBooks WHERE BookID = ? AND MemberID = ? AND status = 'Borrowed'";
	        PreparedStatement checkBorrowedStatement = connection.prepareStatement(checkBorrowedQuery);
	        checkBorrowedStatement.setInt(1, r.getBookid());
	        checkBorrowedStatement.setInt(2, r.getMemberid());

	        ResultSet resultSet = checkBorrowedStatement.executeQuery();

	        if (resultSet.next()) {
	            // Book is borrowed and not yet returned
	            r.setDueDate(resultSet.getString("DueDate"));

	            // Check if the returned date is late
	            r.setReturnedDate(l.current_date());

	            String returnBookQuery = "UPDATE BorrowedBooks SET status = 'Returned', " +
	                                     "ReturnedDate = ?, " +
	                                     "comments = CASE " +
	                                     "WHEN ? > ? THEN 'Returned late!' " +
	                                     "ELSE comments " +
	                                     "END " +
	                                     "WHERE BookID = ? AND MemberID = ?";

	            PreparedStatement returnBookStatement = connection.prepareStatement(returnBookQuery);
	            returnBookStatement.setString(1, r.getReturnedDate());
	            returnBookStatement.setString(2, r.getReturnedDate());
	            returnBookStatement.setString(3, r.getDueDate());
	            returnBookStatement.setInt(4, r.getBookid());
	            returnBookStatement.setInt(5, r.getMemberid());

	            int rowsUpdated = returnBookStatement.executeUpdate();

	            if (rowsUpdated > 0) {
	                System.out.println("Book returned successfully!");
	            } else {
	                System.out.println("Failed to return the book.");
	            }
	        } else {
	            System.out.println("Book is not currently borrowed or has already been returned.");
	        }

	        // Close resources
	        resultSet.close();
	        checkBorrowedStatement.close();
	        connection.close();
	    } catch (Exception e) {
	        System.out.println(e);
	    }

	}
   
}