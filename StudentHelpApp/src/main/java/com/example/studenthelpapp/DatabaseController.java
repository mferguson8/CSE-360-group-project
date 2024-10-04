package com.example.studenthelpapp;
import java.sql.*;

//TODO
//Handle Invite Codes, adding new ones, and creating users from access codes
//Handle Registering New Users
//Handle Adding/changing data about current users
//Handle admin listing users (user_name, first middle and last name, roles)

//Note: Use SecureRandom to generate salts, and try to make them at least 20 characters
//In addition, switch from SHA256 to bcrypt, scrypt, or Argon2 for better security.

class DatabaseController {
		// JDBC driver name and database URL 
		static final String JDBC_DRIVER = "org.h2.Driver";   
		static final String DB_URL = "jdbc:h2:~/firstDatabase";  
		
		//The applications login credentials
		static final String USER = "application_user"; 
		static final String PASS = "surelyThereIsABetterWayToDoThis"; 
		
		//Connection and Statement
		private Connection connection = null;
		private Statement statement = null; 
		
		
		public void connectToDatabase() throws SQLException {
			try {
				Class.forName(JDBC_DRIVER); // Load the JDBC driver
				System.out.println("Connecting to database...");
				connection = DriverManager.getConnection(DB_URL, USER, PASS);
				statement = connection.createStatement(); 
				createTables();  // Create the necessary tables if they don't exist
			} catch (ClassNotFoundException e) {
				System.err.println("JDBC Driver not found: " + e.getMessage());
			}
		}
		
		
		private void createTables() throws SQLException {
			//Creates these tables if they don't exist
			
			
			//Roles stores the role id and name
			String roleTable = "CREATE TABLE IF NOT EXISTS ROLES ("
					+ "role_id INT PRIMARY KEY,"
					+ "role_name VARCHAR(50) NOT NULL)";
			//Username stores 
			//id, email, password, username. first_name, middle_name, last_name, preferred_name, One-time password flag, One-time-flag expiry date, password_salt
			String userTable = "CREATE TABLE IF NOT EXISTS USERS ("
					+ "id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "username VARCHAR(20) UNIQUE NOT NULL,"
					+ "email VARCHAR(255) UNIQUE NULL, "
					+ "hashed_password VARCHAR(255) NOT NULL, "
					+ "first_name VARCHAR(20) NULL,"
					+ "middle_name VARCHAR(20) NULL,"
					+ "last_name VARCHAR(20) NULL,"
					+ "preferred_name VARCHAR(20) NULL,"
					+ "password_reset_flag BOOLEAN DEFAULT FALSE,"
					+ "password_reset_timeout TIMESTAMP NULL,"
					+ "password_salt VARCHAR(20) NOT NULL) ";
			
			//Table holds what users have what roles
			String roleJunctionTable = "CREATE TABLE IF NOT EXISTS USERROLES ("
					+ "user_id INT,"
					+ "role_id INT,"
					+ "PRIMARY KEY (user_id, role_id),"
					+ "FOREIGN KEY (user_id) REFERENCES USERS(id),"
					+ "FOREIGN KEY (role_id) REFERENCES ROLES(role_id))";
			
			//Holds topics id and name
			String topicsTable = "CREATE TABLE IF NOT EXISTS TOPICS ("
					+ "topic_id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "topic_name VARCHAR(50) NOT NULL)";
			
			//Holds comfort level ids and name
			String comfortLevelsTable = "CREATE TABLE IF NOT EXISTS COMFORTLEVELS ("
					+ "comfort_id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "comfort_name VARCHAR(50) NOT NULL)";
			
			String userTopicComfortJunctionTable = "CREATE TABLE IF NOT EXISTS USERTOPICCOMFORT ("
					+ "user_id INT,"
					+ "topic_id INT,"
					+ "comfort_id INT,"
					+ "PRIMARY KEY (user_id, topic_id),"
					+ "FOREIGN KEY (user_id) REFERENCES USERS(id),"
					+ "FOREIGN KEY (topic_id) REFERENCES TOPICS(topic_id),"
					+ "FOREIGN KEY (comfort_id) REFERENCES COMFORTLEVELS(comfort_id))";
			
			String accessCodeRoles = "CREATE TABLE IF NOT EXISTS ACCESSCODEROLES ("
					+ "access_code VARCHAR(20),"
					+ "role_id INT,"
					+ "PRIMARY KEY (access_code, role_id),"
					+ "FOREIGN KEY (role_id) REFERENCES ROLES(role_id))";
			
			//Execute all the statements to generate tables if they don't exist
			try {
				statement.execute(roleTable);
				statement.execute(userTable);
				statement.execute(roleJunctionTable);
				statement.execute(topicsTable);
				statement.execute(comfortLevelsTable);
				statement.execute(userTopicComfortJunctionTable);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String checkRoles = "SELECT COUNT(*) FROM ROLES";
			try(PreparedStatement checkStatement = connection.prepareStatement(checkRoles);
					ResultSet rs = checkStatement.executeQuery()) {
				
				if(rs.next() && rs.getInt(1) == 0) {
					
					//Populates the roles table
					String insertRoles = "INSERT INTO ROLES (role_id, role_name) VALUES (?, ?)";
					PreparedStatement pstmtRoles = connection.prepareStatement(insertRoles);
					// Insert Admin Role
					pstmtRoles.setInt(1, 1);  // role_id
					pstmtRoles.setString(2, "Admin");  // role_name for Admin
					pstmtRoles.executeUpdate();

					// Insert Teacher Role
					pstmtRoles.setInt(1, 2);  // role_id
					pstmtRoles.setString(2, "Teacher");  // role_name for Teacher
					pstmtRoles.executeUpdate();
					
					// Insert Student Role
					pstmtRoles.setInt(1, 3);  // role_id
					pstmtRoles.setString(2, "Student");  // role_name for Student
					pstmtRoles.executeUpdate();
					
					pstmtRoles.close();
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			//TODO Here
			//Populate topicsTable and ComfortLevelsTable 
			// provide different errors
		}
		
		
		// Check if the database is empty
		public boolean isDatabaseEmpty() {
			String query = "SELECT COUNT(*) AS count FROM USERS";
			try (ResultSet resultSet = statement.executeQuery(query)) {
				if (resultSet.next()) {
					return resultSet.getInt("count") == 0;
				}
			} catch (SQLException e) {
		        e.printStackTrace();
		    }
			return false;
		}
		
		
		//This function might be useless currently, as it would never be called
		public Integer getUserIdByEmail(String email) {
			String query = "SELECT id FROM USERS WHERE email = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, email);
		        ResultSet rs = pstmt.executeQuery();
		        // Check if any rows were returned
		        if (rs.next()) {
		            return rs.getInt("id"); // Return the user ID if found
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // Return null if user not found or error occurred
			
		}
		
		public Integer getUserIdByUsername(String username) {
			//gets the UserId provided the username
			String query = "SELECT id FROM USERS WHERE username = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, username);
		        ResultSet rs = pstmt.executeQuery();
		        // Check if any rows were returned
		        if (rs.next()) {
		            return rs.getInt("id"); // Return the user ID if found
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // Return null if user not found or error occurred
			
		}
		
		/*//If we never login via email, this function is not needed
		 * public boolean doesUserExist(String email) {
			//Checks if a user exists by email, by attempting to get their ID.
		    return getUserIdByEmail(email) != null;
		}*/
		
		public boolean doesUserExist(String username) {
			//Checks if a user exists by email, by attempting to get their ID.
		    return getUserIdByUsername(username) != null;
		}
		
		public String getFirstNameById(int id) {
			//For getting the first_name by ID, to display whenever needed
			String query = "SELECT first_name FROM USERS WHERE id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setInt(1, id);
		        ResultSet rs = pstmt.executeQuery();
		        // Check if any rows were returned
		        if (rs.next()) {
		            return rs.getString("first_name"); // Return the user first_name if found
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // Return null if user not found or error occurred
			
		}
		
		public String getSaltById(int id) {
			//For getting the password_salt by the ID, for when you need to hash
			String query = "SELECT password_salt FROM USERS WHERE id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setInt(1, id);
		        ResultSet rs = pstmt.executeQuery();
		        // Check if any rows were returned
		        if (rs.next()) {
		            return rs.getString("password_salt"); // Return the user password_salt if found
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // Return null if user not found or error occurred
			
		}
		
		public boolean loginAttempt(int user_id, String username, String hashedPassword) {
			//Attempts to login by checking if there is a user with that id, username, and hashed_password
			String query = "SELECT * FROM USERS WHERE id = ? AND username = ? AND hashed_password = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setInt(1, user_id);
				pstmt.setString(2, username);
				pstmt.setString(3, hashedPassword);
				try (ResultSet rs = pstmt.executeQuery()) {
					return rs.next();
				}
			}  catch (SQLException e) {
		        e.printStackTrace();
		    }
			return false;
		}
		
		public boolean deleteUser(int user_id) {
			//Attempts to delete a user. Returns true if it successfully deletes the user, false if it fails
			String query = "DELETE FROM USERS WHERE id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setInt(1, user_id);
				int rowsDeleted = pstmt.executeUpdate();
				return rowsDeleted > 0;
			}  catch (SQLException e) {
		        e.printStackTrace();
		    }
			return false;
		}
		
		public boolean createUser(String username, String hashed_password, String password_salt) {
			//For initial user creation, you only need to specify the username, hashed_password, and password_salt
			String insertion = "INSERT INTO USERS (username, hashed_password, password_salt)"
					+ "VALUES (?, ?, ?)";
			try (PreparedStatement pstmt = connection.prepareStatement(insertion)) {
				pstmt.setString(1,username);
				pstmt.setString(2,hashed_password);
				pstmt.setString(3,password_salt);
				int rowsAdded = pstmt.executeUpdate();
				return rowsAdded == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		public boolean registerUser(int user_id, String email, String first_name, String middle_name, String last_name, String preferred_name) {
			String register = "UPDATE USERS SET email = ?, first_name = ?, middle_name = ?, last_name = ?, preferred_name = ? WHERE id = ?";
			
			try(PreparedStatement pstmt = connection.prepareStatement(register)) {
				
		        if (email != null) {
		            pstmt.setString(1, email);
		        } else {
		            pstmt.setNull(1, java.sql.Types.VARCHAR);
		        }

		       
		        if (first_name != null) {
		            pstmt.setString(2, first_name);
		        } else {
		            pstmt.setNull(2, java.sql.Types.VARCHAR);
		        }

		        
		        if (middle_name != null) {
		            pstmt.setString(3, middle_name);
		        } else {
		            pstmt.setNull(3, java.sql.Types.VARCHAR);
		        }

		        
		        if (last_name != null) {
		            pstmt.setString(4, last_name);
		        } else {
		            pstmt.setNull(4, java.sql.Types.VARCHAR);
		        }

		        
		        if (preferred_name != null) {
		            pstmt.setString(5, preferred_name);
		        } else {
		            pstmt.setNull(5, java.sql.Types.VARCHAR);
		        }

		        
		        pstmt.setInt(6, user_id);

		        
		        int rowsUpdated = pstmt.executeUpdate();
		        return rowsUpdated > 0;

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
			return false;
		}
		
		public void closeConnection() {
			//Closes the statement and connection to the database
			try{ 
				if(statement!=null) statement.close(); 
			} catch(SQLException se2) { 
				se2.printStackTrace();
			} 
			try { 
				if(connection!=null) connection.close(); 
			} catch(SQLException se){ 
				se.printStackTrace(); 
			} 
		}
		
		
		
		
		
		
		
}
