package com.client;



import java.util.*;

import com.services.LibraryDataOperations;
import com.services.MemberOps;
import com.services.ReturnBookOps;
import com.services.AddBorrowedBookOps;
import com.services.AuthorsOps;
import com.services.BooksOps;
import com.services.CategoryOps;

public class LmsMain {
			
			static {
				System.out.println("------------------------------------------------------------------------------------------------------");
				System.out.println("         Welcome");
				System.out.println("Library Management System");
				
				
			}
			

		    public static void main(String[] args) {
		        Scanner sc = new Scanner(System.in);
		        LibraryDataOperations bk = new BooksOps();
		        LibraryDataOperations authr = new AuthorsOps();
		        LibraryDataOperations ctgry = new CategoryOps();
		        LibraryDataOperations membrs=new MemberOps();
		        LibraryDataOperations borrow=new AddBorrowedBookOps();
		        ReturnBookOps returnb=new ReturnBookOps();
		        AddBorrowedBookOps hstry=new AddBorrowedBookOps();
		        boolean running = true; // Initialize a flag to control the loop

		        while (running) { // Start a loop that continues until running is set to false
		            System.out.println("------------------------------------------------------------------------------------------------------");
		            System.out.println("***Select*** \n"
		            		+ "1. Add Books \n"
		                    + "2. Add Author \n"
		                    + "3. Add Category of Books   \n"
		                    + "4. Add Members   \n"
		                    + "5. Borrow a Book  \n"
		                    + "6. Return a Book  \n"
		                    + "7. view Books  \n"
		                    + "8. view Members  \n"
							+ "9.View Authors  \n"
							+ "10.View Category  \n"
		                    + "11. view Borrowed Books  \n"
							+ "12.view history  \n"
		                    + "13.Update Member info  \n"
							+ "14.Update Authors  \n"
		                    + "15. Exit  ");
		            System.out.println("------------------------------------------------------------------------------------------------------");
		            int ch = sc.nextInt();

		            switch (ch) {
		            
		                case 1:		                     
		                    bk.add_data();
		                    break;

		                case 2:	                     
		                   authr.add_data();
		                    break;

		                case 3:		                     
		                    ctgry.add_data();
		                    break;

		                case 4:
		                	membrs.add_data();  
		                    break;
		                    
		                case 5:
		                    borrow.add_data();  
		                    break; 
		                
		                case 6:
		                	returnb.ReturnBooks();  
		                    break;
		                
		                case 7:		                     
		                    bk.view_data();
		                    break;
		                    
		                case 8:		                     
		                	membrs.view_data();
		                    break;
		                    
		                case 9:	
		                	authr.view_data();
		                	break;
		                    
		                case 10:
		                	ctgry.view_data();
		                	break; 
		                    
		                case 11:
		                	borrow.view_data();
		                	break;
		                    
		                case 12:	
		                	hstry.view_history();
		                	break; 
		                    
		                case 13:		                     
		                	membrs.update_data();
		                    break; 
		                    
		                case 14:		                     
		                	authr.update_data();
		                    break; 
		                    
		                case 15:
		                    running = false; 
		                    System.out.println("Exiting the program.");
		                    System.exit(0);
		                    break; 
		                default:
		                    System.out.println("Invalid input");
		                    break;
		            }
		        }

		        // The program will exit the loop when option 13 is selected
		        System.out.println("Exiting the program.");

		        // Close the scanner when you're done with it
		        sc.close();
		    }
		
	}

