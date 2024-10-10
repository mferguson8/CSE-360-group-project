package com.example.studenthelpapp;

import java.util.Scanner;

public class adminTools {
	
	public int numUsers;
	public int numAdmins;
	public int numStudents;
	public User currUser;
	
	public void setAdmins(User currUser) {
		if (currUser.getRole().equals("admin")) {
			currUser.setAdmin(true);
            numAdmins++;
        }
		if (numUsers == 1) {
			currUser.setAdmin(true);
			numAdmins++;
		}
    }

	public void setStudents(User currUser) {
		if (currUser.getRole().equals("student")) {
			currUser.setStudent(true);
			numStudents++;
		}
	}
	
	Scanner input = new Scanner(System.in);
	public void deleteUser(User currUser, User deleteUser) {		
		if (currUser.getRole().equals("admin")) {
			if (deleteUser.getRole().equals("student")) {			
				System.out.println("Are you sure. (Y/N");
				String choice = input.nextLine();
				
				if (choice.equals("Y")) {
					deleteUser = null;
					numUsers--;
					numStudents--;
				} else {
					System.out.println("User not deleted");
				}
			}
			deleteUser = null;
			numUsers--;
		}
			
	}
	
	public void logOut(User currUser) {
		currUser = null;
	}
}
