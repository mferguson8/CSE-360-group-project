package com.example.studenthelpapp;

public class handleLogin {
	private String username;
	private String password;
	private String email;

	public handleLogin(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public boolean checkLogin(String username, String password) {
	  if (this.username.equals(username) && this.password.equals(password)) {
	   return true;
	  }
	  return false;
	}


	public void printInfo() {
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Email: " + email);
	}

	public static void main(String[] args) {
		String username = "user";
		String password = "password";
		
		try {

		handleLogin login = new handleLogin(username, password, "email");
		if(!login.checkLogin(username, password)) {
		  System.out.println("Invalid login");
		}

			else {
				login.printInfo();
			    System.out.println("Login successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
