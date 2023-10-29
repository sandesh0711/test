package com.model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;



public class LibraryUtils {
	
	public  String current_date()
	{
		 Date currentDate = new Date();
         // Format the current date as a string in "yyyy-MM-dd" format
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         String borrowDate = dateFormat.format(currentDate);
         return borrowDate;
		
	}
	public  String calculateDueDate() {
        // Create a Calendar instance to calculate the due date
        Calendar calendar = Calendar.getInstance();

        // Add 25 days to the current date to calculate the due date
        calendar.add(Calendar.DAY_OF_MONTH, 25);

        // Get the due date as a Date object
        Date dueDate = calendar.getTime();

        // Format the due date as a string in "yyyy-MM-dd" format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dueDateStr = dateFormat.format(dueDate);

        return dueDateStr;
	}
	
	
	

	}