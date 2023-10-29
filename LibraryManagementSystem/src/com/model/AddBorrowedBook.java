package com.model;

public class AddBorrowedBook {
	   private int BookID;
	   private int MemberID;
	   private String BorrowedDate;
	   private String DueDate;
	   private String ReturnedDate;
	   private String Status;
	public int getBookID() {
		return BookID;
	}
	public void setBookID(int bookID) {
		BookID = bookID;
	}
	public int getMemberID() {
		return MemberID;
	}
	public void setMemberID(int memberID) {
		MemberID = memberID;
	}
	public String getBorrowedDate() {
		return BorrowedDate;
	}
	public void setBorrowedDate(String borrowedDate) {
		BorrowedDate = borrowedDate;
	}
	public String getDueDate() {
		return DueDate;
	}
	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}
	public String getReturnedDate() {
		return ReturnedDate;
	}
	public void setReturnedDate(String returnedDate) {
		ReturnedDate = returnedDate;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

}
