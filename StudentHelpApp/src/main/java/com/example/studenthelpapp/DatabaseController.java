package com.example.studenthelpapp;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

//Notes:
//Don't store database login and password in code, and change them.
//In addition, switch from SHA256 to bcrypt, scrypt, or Argon2 for better security.



class DatabaseController {
		//DatabaseController is designed to handle all the logic for interacting with the database,
		//and be simple to use.
	
	
		// JDBC driver name and database URL 
		private static final String JDBC_DRIVER = "org.h2.Driver";   
		private static final String DB_URL = "jdbc:h2:~/StudentHelpApp";  
		
		//The applications login credentials
		private static final String USER = "sa"; 
		private static final String PASS = ""; 
		
		//Connection and Statement
		private Connection connection = null;
		private Statement statement = null; 
		
		public static enum RoleCodes {
            ADMIN(1),
            INSTRUCTOR(2), 
            STUDENT(3);
            
            private final int roleId;
			
			RoleCodes(int roleId) {
				this.roleId = roleId;
			}
			
			public int get() {
				return roleId;
			}
        }
		
		public void connectToDatabase() {
			
			try {
				Class.forName(JDBC_DRIVER); // Load the JDBC driver
				System.out.println("Connecting to database...");
				connection = DriverManager.getConnection(DB_URL, USER, PASS);
				statement = connection.createStatement(); 
				//wipeDatabase(); //TODO: This is for testing. Remove
				
				createTables();  // Create the necessary tables if they don't exist
				System.out.println("Connected to database!");
			} catch (ClassNotFoundException e) {
				System.err.println("JDBC Driver not found: " + e.getMessage());
			} catch (SQLException e) {
				System.err.println("SQL ERROR CONNECTING: " + e.getMessage());
			}
		}
		
		
		private void wipeDatabase() {	
			//TODO: THIS IS ONLY FOR TESTING. REMOVE
		    String sql = "DROP ALL OBJECTS";
		    try (Statement stmt = connection.createStatement()) {
		        stmt.execute(sql);
		        System.out.println("All objects in the database have been dropped.");
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		private void fakePeople() {
		   
		    String[] firstNames = {"John", "Jane", "Michael", "Emily", "David", "Emma", "Chris", "Sophia", "Daniel", "Olivia"};
		    String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
		    
		    
		    Random random = new Random();

		    try {
		        
		        String insertUserSQL = "INSERT INTO USERS (username, email, hashed_password, first_name, middle_name, last_name, password_salt) "
		                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

		       
		        String insertUserRoleSQL = "INSERT INTO USERROLES (user_id, role_id) VALUES (?, ?)";

		        PreparedStatement insertUserStmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
		        PreparedStatement insertUserRoleStmt = connection.prepareStatement(insertUserRoleSQL);

		        
		        for (int i = 1; i <= 20; i++) {
		            String firstName = firstNames[random.nextInt(firstNames.length)];
		            String lastName = lastNames[random.nextInt(lastNames.length)];
		            String username = firstName.toLowerCase() + i;
		            String email = username + "@example.com";
		            String hashedPassword = "password" + i; // Plaintext passwords for testing
		            String passwordSalt = "salt" + i; // Plaintext salt for testing

		            
		            insertUserStmt.setString(1, username);
		            insertUserStmt.setString(2, email);
		            insertUserStmt.setString(3, hashedPassword);
		            insertUserStmt.setString(4, firstName);
		            insertUserStmt.setNull(5, java.sql.Types.VARCHAR); // Null for middle_name
		            insertUserStmt.setString(6, lastName);
		            insertUserStmt.setString(7, passwordSalt);
		            insertUserStmt.executeUpdate();

		            // Retrieve the generated user ID
		            ResultSet generatedKeys = insertUserStmt.getGeneratedKeys();
		            if (generatedKeys.next()) {
		                int userId = generatedKeys.getInt(1);

		                // Randomly assign roles to users
		                int numRoles = random.nextInt(3) + 1; // Each user gets between 1 to 3 roles
		                Set<Integer> assignedRoles = new HashSet<>();
		                for (int j = 0; j < numRoles; j++) {
		                    int roleId;
		                    do {
		                    	roleId= random.nextInt(3) + 1; // Role ID between 1 (Admin) and 3 (Student)
		                    } while(assignedRoles.contains(roleId));
		                    assignedRoles.add(roleId);
		                    insertUserRoleStmt.setInt(1, userId);
		                    insertUserRoleStmt.setInt(2, roleId);
		                    insertUserRoleStmt.executeUpdate();
		                }
		            }
		        }

		        insertUserStmt.close();
		        insertUserRoleStmt.close();

		        System.out.println("Inserted 20 fake users with random roles into the database.");

		    } catch (SQLException e) {
		        e.printStackTrace();
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
					+ "password_salt VARCHAR(40) NOT NULL) ";
			
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
				statement.execute(accessCodeRoles);
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
					pstmtRoles.setInt(1, RoleCodes.ADMIN.get());  // role_id
					pstmtRoles.setString(2, "Admin");  // role_name for Admin
					pstmtRoles.executeUpdate();

					// Insert Teacher Role
					pstmtRoles.setInt(1, RoleCodes.INSTRUCTOR.get());  // role_id
					pstmtRoles.setString(2, "Instructor");  // role_name for instructor
					pstmtRoles.executeUpdate();
					
					// Insert Student Role
					pstmtRoles.setInt(1, RoleCodes.STUDENT.get());  // role_id
					pstmtRoles.setString(2, "Student");  // role_name for Student
					pstmtRoles.executeUpdate();
					
					pstmtRoles.close();
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("All tables created");
			fakePeople(); //TODO THIS IS FOR TESTING, REMOVE
			
			//TODO Here
			//Populate topicsTable and ComfortLevelsTable 
			// provide different errors
		}
		
		
		// Check if the database is empty (has no users). Returns true or false
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
		
		
	
		//Given a String username, returns the user_id of that user.
		//Returns an Integer, which will be null if the user is not found, so check for that.
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
		
		public String getFirstNameById(int id) {
			//For getting the first_name by ID, to display whenever needed
			//Will return null if that user isn't found, or if they have not finished registration.
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
			//Salt is mandatory when creating an account, so this function will only return null when user not found
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
			//Returns true (that user exists, log them in) or false (no user found, might be wrong username or password, don't log them in.)
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
			//Returns a boolean for success or failure
			String insertion = "MERGE INTO USERS (username, hashed_password, password_salt)"
					+ "KEY (username) VALUES (?, ?, ?)";
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
		
		public void changeUserRoles(int userID, int[] role) {
			//For changing user roles. Tries to delete all roles from them, and then adds the roles in int[] role	
			int[] roleIntArr = getRoleIdList();
			for(int i : roleIntArr) {
				deleteRoleFromUser(userID, i);
				for(int j: role) {
					if(i == j) {
						addRoleToUser(userID,i);
					}
				}
			}
		}
		
		public boolean addRoleToUser(int userID, int roleID) {
			//adds a role to user with userID of the role roleID
			//Returns a boolean about success.
			//Will return true even if that user already had that role, and won't modify it.
			String addRole = "MERGE INTO USERROLES (user_id, role_id) KEY (user_id, role_id) VALUES (?, ?)";
			try (PreparedStatement pstmt = connection.prepareStatement(addRole)) {
				pstmt.setInt(1,userID);
				pstmt.setInt(2,roleID);
				int rowsAdded = pstmt.executeUpdate();
				return rowsAdded == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		public boolean deleteRoleFromUser(int userID, int roleID) {
			//Attempts to delete the given role from the given user. 
			//Will return false on an error or if that user doesn't have that role.
			String deleteRole = "DELETE FROM USERROLES where user_id = ? AND role_id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(deleteRole)) {
				pstmt.setInt(1,userID);
				pstmt.setInt(2,roleID);
				int rowsDeleted = pstmt.executeUpdate();
				return rowsDeleted == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		
		public int[] getUsersRoleIds(int userID) {
			//returns a sorted int array of roleIDs for the given user. 
			//will return a 0 length int array for errors, failure to find user, or user not having any roles
			List<Integer> roleList = new ArrayList<>();
			String getRoles = "SELECT role_id FROM USERROLES WHERE user_id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(getRoles)) {
				pstmt.setInt(1,userID);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					roleList.add(rs.getInt("role_id"));
				}
				int[] roleIntArr = roleList.stream().mapToInt(Integer::intValue).toArray();
				Arrays.sort(roleIntArr);
				return roleIntArr;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return new int[0];
		}
		
		public int[] getRoleIdList() {
			//Returns an int array of all the roles in the ROLES table.
			//Should just return [1,2,3]
			//If there is an error, returns an empty int array
			List<Integer> roleList = new ArrayList<>();
			String getRoles = "SELECT role_id FROM ROLES";
			try (PreparedStatement pstmt = connection.prepareStatement(getRoles)) {
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					roleList.add(rs.getInt("role_id"));
				}
				return roleList.stream().mapToInt(Integer::intValue).toArray();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return new int[0];
		}
		
		public boolean registerUser(int user_id, String email, String first_name, String middle_name, String last_name, String preferred_name) {
			//Used during user registration.
			//For the given user_id, updates email, first_name, middle_name, last_name, and preferred_name
			//They will be kept null if the String passed is null.
			String register = "UPDATE USERS SET email = COALESCE(?, email), "
					+ "first_name = COALESCE(?, first_name), "
					+ "middle_name = COALESCE(?, middle_name), "
					+ "last_name = COALESCE(?, last_name), "
					+ "preferred_name = COALESCE(?, preferred_name) WHERE id = ?";
			
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
		
		public boolean checkIfInviteCodeValid(String invite_code) {
			//Checks if any roles are associate with that invite code.
			//Returns a boolean. Success means at least 1 role is associate with that code. 
			//Failure means error, no invite code found, or no roles associate with that code.
			String getRows = "SELECT count(*) FROM ACCESSCODEROLES WHERE access_code = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(getRows)) {
				pstmt.setString(1,invite_code);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					int count = rs.getInt(1);
					return count > 0;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		public boolean addInviteCodeRole(String invite_code, int role_id) {
			//Adds the given invite code association with the given role id. 
			//Returns a boolean of success or failure.
			//Will fail if that invite code already exists
			String addRole = "INSERT INTO ACCESSCODEROLES (access_code, role_id) VALUES (?, ?)";
			try (PreparedStatement pstmt = connection.prepareStatement(addRole)) {
				pstmt.setString(1,invite_code);
				pstmt.setInt(2,role_id);
				int rowsAdded = pstmt.executeUpdate();
				return rowsAdded == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		public int[] getInviteCodeRoles(String invite_code) {
			//returns an int array of all role_ids associated with the given invite_code
			//if error, no invite code found, or no roles attached, returns an empty int array of size 0
			String getRoles = "SELECT role_id FROM ACCESSCODEROLES where access_code = ?";
			List<Integer> roleList = new ArrayList<>();
			try (PreparedStatement pstmt = connection.prepareStatement(getRoles)) {
				pstmt.setString(1,invite_code);
				try(ResultSet rs = pstmt.executeQuery()) {
					while(rs.next()) {
						roleList.add(rs.getInt("role_id"));
					}
					return roleList.stream().mapToInt(Integer::intValue).toArray();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return new int[0];
		}
		
		public boolean deleteInviteCodeRole(String invite_code, int role_id) {
			//Removes an access code with the given role from the ACCESSCODEROLES table.
			//Used when someone creates an account with that invite code (or if we want to delete access codes)
			//For removing the invite code entirely (like when account is created), 
			//call for each role_id associate with that invite code
			String deleteRole = "DELETE FROM ACCESSCODEROLES where access_code = ? AND role_id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(deleteRole)) {
				pstmt.setString(1,invite_code);
				pstmt.setInt(2,role_id);
				int rowsDeleted = pstmt.executeUpdate();
				return rowsDeleted == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		
		public boolean adminResetPassword(int user_id, String newHashedPassword) {
			//Called when the admin wants to reset the user's password. 
			//Returns a boolean indicating success or failure.
			//User's have their password_reset_flag set to true, and a Timestamp in UTC of current time + 7 days.
			String resetPassword = "UPDATE USERS SET hashed_password = ?, password_reset_flag = ?, password_reset_timeout = ? WHERE id = ?";
			ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));	//Uses UTC so time stamps are consistent.
			ZonedDateTime resetTimeout = currentTime.plus(7, ChronoUnit.DAYS); //Hard coded 1 week. Easy change if needed.
			Timestamp timeoutTimestamp = Timestamp.from(resetTimeout.toInstant());
			try (PreparedStatement pstmt = connection.prepareStatement(resetPassword)) {
				pstmt.setString(1,newHashedPassword);
				pstmt.setBoolean(2,true);
				pstmt.setTimestamp(3,timeoutTimestamp);
				pstmt.setInt(4, user_id);
				int rowsChanged = pstmt.executeUpdate();
				return rowsChanged == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
			
		}
		
		public boolean userResetPassword(int user_id, String newHashedPassword) {
			//For when the user either manually changes their password (not sure if needed as a feature), or when they login after 
			//Admin has reset their password.
			String resetPassword = "UPDATE USERS SET hashed_password = ?, password_reset_flag = ? WHERE id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(resetPassword)) {
				pstmt.setString(1,newHashedPassword);
				pstmt.setBoolean(2,false);	//Sets the password_reset_flag to false, no matter its current status. Might not be desired behavior, but seems good for now.
				pstmt.setInt(3, user_id);
				int rowsChanged = pstmt.executeUpdate();
				return rowsChanged == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
			
		}
		
		public int checkIfPasswordResetRequired(int user_id) {
			//When the user logs in, we check if they need to reset their password.
			
			//Will return -1 if no results are returned or there is some error
			//Will return 0 if their password_reset_flag is false
			//Will return 1 if their password_reset_flag is true and it is before the timeout
			//Will return 2 if their password_reset_flag is true but it is after the timeout
			
			String resetCheck = "SELECT password_reset_flag, password_reset_timeout FROM USERS WHERE id = ?";
			ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));	//Uses UTC so time stamps are consistent.
			
			try (PreparedStatement pstmt = connection.prepareStatement(resetCheck)) {
				pstmt.setInt(1,user_id);
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					Boolean passwordResetFlag = rs.getBoolean("password_reset_flag");
					Timestamp passwordResetTimeout = rs.getTimestamp("password_reset_timeout");
					
					if(passwordResetFlag == null || !passwordResetFlag) {
						return 0;
					}
					ZonedDateTime resetTimeout = passwordResetTimeout.toInstant().atZone(ZoneId.of("UTC"));
					if(currentTime.isBefore(resetTimeout)) {
						return 1;
					} else {
						return 2;
					}
				}
				return -1;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
			
		}
		
		
		public String listUsers() {
			//Designed to be used for the admin list users functionality
			//Returns a string formatted in the following way
			//"username, First_name Last_Name, [1,2,3]\n" for each user. The '[1,2,3]' contains their role IDs.
			//For all users the string is build like this, and returns a single long string.
			StringBuilder userList = new StringBuilder();
			
			String getUsers = "SELECT id, username, first_name, last_name FROM USERS";
			
			try (PreparedStatement pstmt = connection.prepareStatement(getUsers)) {
				try(ResultSet rs = pstmt.executeQuery()) {
					while(rs.next()) {
						userList.append(rs.getString("username") + ", " + rs.getString("first_name") + " " + rs.getString("last_name")+", ");
						int id = rs.getInt("id");
						int[] roles = getUsersRoleIds(id);
						userList.append(Arrays.toString(roles) + "\n");
					}
					return userList.toString();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "Error, fetching, users";
			
		}
		
		
		public void closeConnection() {
			//Closes the statement and connection to the database
			//Not currently used
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
