package com.services;

import java.sql.*;


import java.util.*;

import com.model.Member;

public class MemberOps implements LibraryDataOperations {
	Scanner sc=new Scanner(System.in);
	Member m=new Member();

	@Override
	public void add_data() {
		
		  try {
         	 System.out.print("Enter FirstName:  ");
             m.setFirstName(sc.nextLine().trim());
             
             System.out.print("Enter LastName:  ");
             m.setLastName(sc.nextLine().trim());
             
             System.out.print("Enter Email:  ");
             m.setEmail(sc.nextLine().trim());
             
             System.out.print("Enter Phone:  ");
             m.setPhone(sc.nextLine().trim());
             
             System.out.print("Enter Address:  ");
             m.setAddress(sc.nextLine().trim());

         } catch (InputMismatchException e) {
             System.out.println("Invalid input. Please enter a valid value.");
             sc.nextLine(); // Clear the input buffer
             return; // Return to the caller without processing further
         }

		
		 try {
			 
			 	Class.forName ("com.mysql.cj.jdbc.Driver");
			 	final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
       	
       	   		Connection connection=DriverManager.getConnection(JDBC_URL,"root","root");
	            String insertMemberSQL = "INSERT INTO Members (FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement preparedStatement = connection.prepareStatement(insertMemberSQL);
	            preparedStatement.setString(1,m.getFirstName());
	            preparedStatement.setString(2, m.getLastName());
	            preparedStatement.setString(3, m.getEmail());
	            preparedStatement.setString(4, m.getPhone());
	            preparedStatement.setString(5, m.getAddress());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            System.out.println("Member added successfully!");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public void view_data() {
		
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";

		        Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

		        String selectAllMembersQuery = "select * FROM Members";

		        PreparedStatement preparedStatement = connection.prepareStatement(selectAllMembersQuery);
		        ResultSet resultSet = preparedStatement.executeQuery();

		        // Column headers
		        System.out.printf("%-10s%-20s%-20s%-30s%-15s%-30s%-15s%n",
		                "Member ID", "First Name", "Last Name", "Email", "Phone" ,"Address","update_dt");
		        System.out.println("--------------------------------------------------------------------------------------------");

		        // Iterate through the result set and print member data in tabular format
		        while (resultSet.next()) {
		            int memberId = resultSet.getInt("MemberID");
		            String firstName = resultSet.getString("FirstName");
		            String lastName = resultSet.getString("LastName");
		            String email = resultSet.getString("Email");
		            String phone = resultSet.getString("Phone");
		            String address = resultSet.getString("address");
		            String update_dt = resultSet.getString("update_dt");

		            System.out.printf("%-10s%-20s%-20s%-30s%-15s%-30s%-15s%n",
		                    memberId, firstName, lastName, email, phone,address,update_dt);
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
			boolean memberexists=false;
		System.out.println("Enter Member Id to Update Details");
		m.setMemberId(sc.nextInt());
		 sc.nextLine();
		
		    try {
		        
		        // Connect to the database
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
		        Connection connection = DriverManager.getConnection(JDBC_URL, "root", "root");

		        // Check if the member with the provided MemberID exists
		        String checkMemberQuery = "SELECT * FROM Members WHERE MemberID = ?";
		        PreparedStatement checkMemberStatement = connection.prepareStatement(checkMemberQuery);
		        checkMemberStatement.setInt(1, m.getMemberId());
		        ResultSet resultSet = checkMemberStatement.executeQuery();
		        ResultSet temp=resultSet;
		        System.out.printf("%-10s%-20s%-20s%-30s%-15s%-30s%-15s%n",
		                "Member ID", "First Name", "Last Name", "Email", "Phone" ,"Address","update_dt");
		        System.out.println("--------------------------------------------------------------------------------------------");
		        while (temp.next()) {
		            int memberId = temp.getInt("MemberID");
		            String firstName = temp.getString("FirstName");
		            String lastName = temp.getString("LastName");
		            String email = temp.getString("Email");
		            String phone = temp.getString("Phone");
		            String address = temp.getString("address");
		            String update_dt = temp.getString("update_dt");

		            System.out.printf("%-10s%-20s%-20s%-30s%-15s%-30s%-15s%n",
		                    memberId, firstName, lastName, email, phone,address,update_dt);
		        
		        	
		        	System.out.print("Enter updated First Name: ");
			        m.setFirstName(sc.nextLine().trim());

			        System.out.print("Enter updated Last Name: ");
			        m.setLastName(sc.nextLine().trim());

			        System.out.print("Enter updated Email: ");
			        m.setEmail(sc.nextLine().trim());

			        System.out.print("Enter updated Phone: ");
			        m.setPhone(sc.nextLine().trim());

			        System.out.print("Enter updated Address: ");
			        m.setAddress(sc.nextLine().trim());

		            // Member with the given MemberID exists, proceed with the update
		            String updateMemberSQL = "UPDATE Members SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, Address = ? " +
		                                     "WHERE MemberID = ?";
		            PreparedStatement updateMemberStatement = connection.prepareStatement(updateMemberSQL);
		            updateMemberStatement.setString(1, m.getFirstName());
		            updateMemberStatement.setString(2, m.getLastName());
		            updateMemberStatement.setString(3, m.getEmail());
		            updateMemberStatement.setString(4, m.getPhone());
		            updateMemberStatement.setString(5, m.getAddress());
		            updateMemberStatement.setInt(6,m.getMemberId());
		            int rowsUpdated = updateMemberStatement.executeUpdate();
		            memberexists=true;
		            if (rowsUpdated > 0) {
		                System.out.println("Member information updated successfully!");
		            } else {
		                System.out.println("Failed to update member information.");
		            }

		            updateMemberStatement.close();
		        } if(!memberexists) {
		            System.out.println("Member with MemberID " + m.getMemberId() + " does not exist.");
		        }

		        resultSet.close();
		        checkMemberStatement.close();
		        connection.close();
		    } catch (SQLException e) {
		        System.out.println("SQL Error: " + e.getMessage());
		    } catch (ClassNotFoundException e) {
		        System.out.println("Class Not Found: " + e.getMessage());
		    }
		}


		
	}
	
	
	
	


