package com.services;


import java.sql.*;
import java.sql.DriverManager;
import java.util.*;


import com.model.AddBorrowedBook;
import com.model.LibraryUtils;

public class AddBorrowedBookOps implements LibraryDataOperations {

	Scanner sc=new Scanner(System.in);
	AddBorrowedBook a=new AddBorrowedBook();
	LibraryUtils l=new LibraryUtils();
	@Override
	public void add_data() {
		// TODO Auto-generated method stub
		//"yyyy-MM-dd" date format
		System.out.println("Please enter BookID");
		a.setBookID(sc.nextInt());
		
		

		try {
			Class.forName ("com.mysql.cj.jdbc.Driver");
		 	final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
   	
   	   		Connection connection=DriverManager.getConnection(JDBC_URL,"root","root");
		    // Check if the book is available (Quantity > 0)
		    String checkAvailabilityQuery = "SELECT (Quantity-Borrowed) As AvailableQuantity FROM Books WHERE BookID = ?";
		    PreparedStatement availabilityStatement = connection.prepareStatement(checkAvailabilityQuery);
		    availabilityStatement.setInt(1, a.getBookID());
		    ResultSet availabilityResult = availabilityStatement.executeQuery();

		    if (availabilityResult.next()) {
		        int quantity = availabilityResult.getInt("AvailableQuantity");
		        if (quantity > 0) {
		            // Book is available; proceed with the borrowing

		            // Get the borrower's ID (you need to obtain this from user input or authentication)
		        	System.out.println("Please enter Member ID");
		            a.setMemberID(sc.nextInt());
		            a.setStatus("Borrowed");
		            a.setBorrowedDate(l.current_date());
		            // Calculate the due date based on your business logic
		            a.setDueDate(l.calculateDueDate());

		            // Insert a record into the BorrowedBooks table
		            String borrowBookQuery = "INSERT INTO BorrowedBooks (BookID, MemberID, BorrowedDate, DueDate,Status) VALUES (?, ?, ?, ?, ?)";
		            PreparedStatement borrowStatement = connection.prepareStatement(borrowBookQuery);
		            borrowStatement.setInt(1, a.getBookID());
		            borrowStatement.setInt(2, a.getMemberID());
		            borrowStatement.setString(3, a.getBorrowedDate());
		            borrowStatement.setString(4, a.getDueDate());
		            borrowStatement.setString(5, a.getStatus());
		            int rowsInserted = borrowStatement.executeUpdate();

		            if (rowsInserted > 0) {
		            	// logic to decrease book count that is borrowed
		                System.out.println("Book borrowed successfully!");
		                System.out.println("Please return book by "+a.getDueDate());
		                //quantity=quantity-1;
		                String decrementQuantityQuery = "UPDATE Books SET Borrowed = Borrowed + 1 WHERE BookID = ?";
		                PreparedStatement decrementStatement = connection.prepareStatement(decrementQuantityQuery);
		                decrementStatement.setInt(1, a.getBookID());
		                decrementStatement.executeUpdate();
		            } else {
		                System.out.println("Failed to borrow the book.");
		            }
		        } else {
		            System.out.println("Sorry, the book is not available at the moment.");
		        }
		    } else {
		        System.out.println("Book not found.");
		    }

		    // Close resources (PreparedStatement, ResultSet, and Connection)
		    availabilityResult.close();
		    availabilityStatement.close();
		    connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	@Override
	public void view_data() {
		
		
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";

		        Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

		        String selectAllBorrowedBooksQuery = "SELECT bb.TransactionID, bb.BookID, bb.MemberID, bb.BorrowedDate, " +
		                "bb.DueDate, bb.ReturnedDate, bb.Status, bb.comments, b.Title, m.FirstName, m.LastName " +
		                "FROM BorrowedBooks bb " +
		                "INNER JOIN Books b ON bb.BookID = b.BookID " +
		                "INNER JOIN Members m ON bb.MemberID = m.MemberID";

		        PreparedStatement preparedStatement = connection.prepareStatement(selectAllBorrowedBooksQuery);
		        ResultSet resultSet = preparedStatement.executeQuery();

		        // Column headers
		        System.out.printf("%-15s%-10s%-10s%-15s%-15s%-15s%-15s%-50s%%n",
		                "Transaction ID", "Book ID", "Member ID", "Borrowed Date", "Due Date", "Returned Date",
		                "Status", "Comments");
		        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");

		        // Iterate through the result set and print borrowed books' data in tabular format
		        while (resultSet.next()) {
		            int transactionId = resultSet.getInt("TransactionID");
		            int bookId = resultSet.getInt("BookID");
		            int memberId = resultSet.getInt("MemberID");
		            String borrowedDate = resultSet.getString("BorrowedDate");
		            String dueDate = resultSet.getString("DueDate");
		            String returnedDate = resultSet.getString("ReturnedDate");
		            String status = resultSet.getString("Status");
		            String comments = resultSet.getString("comments");
		            

		            System.out.printf("%-15d%-10d%-10d%-15s%-15s%-15s%-15s%-50s%n",
		                    transactionId, bookId, memberId, borrowedDate, dueDate, returnedDate, status, comments);
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
	
	
	public void view_history() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";

	        Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

	        String selectAllHistoryQuery = "SELECT TransactionID, BookID, MemberID, BorrowedDate, ReturnedDate, Status, comments " +
	                "FROM history";

	        PreparedStatement preparedStatement = connection.prepareStatement(selectAllHistoryQuery);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Column headers
	        System.out.printf("%-20s%-10s%-10s%-15s%-15s%-20s%-50s%n",
	                "TransactionID", "BookID", "MemberID", "BorrowedDate", "ReturnedDate", "Status", "Comments");
	        System.out.println("----------------------------------------------------------------------------");

	        // Iterate through the result set and print history data in tabular format
	        while (resultSet.next()) {
	            String transactionID = resultSet.getString("TransactionID");
	            int bookID = resultSet.getInt("BookID");
	            int memberID = resultSet.getInt("MemberID");
	            String borrowedDate = resultSet.getString("BorrowedDate");
	            String returnedDate = resultSet.getString("ReturnedDate");
	            String status = resultSet.getString("Status");
	            String comments = resultSet.getString("comments");

	            System.out.printf("%-20s%-10d%-10d%-15s%-15s%-20s%-50s%n",
	                    transactionID, bookID, memberID, borrowedDate, returnedDate, status, comments);
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
