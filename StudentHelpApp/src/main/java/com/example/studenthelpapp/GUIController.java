package com.example.studenthelpapp;

import java.util.ArrayList;
import java.util.List;

// Some of these unnecessary so far 
//import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
//import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


// (for self)
// TO DO: 
// Alert for delete user
	// Fix alerts in other places as well
// Add back buttons (have some)
// Logout - take back to login page, but sets logged in as false, make all logout buttons the same

// NOTES: Didn't use original button handling

// Notes: Need to fix role page (shouldn't show all roles unless they have them)
// Notes: switchScene useless?
// Notes: Didn't use original button handling

// Better naming conventions?


/**
 * 
 * GUI
 * 
 */
public class GUIController implements EventHandler<ActionEvent>{
	
	/*****************************************
	 * 
	 * Attributes
	 * 
	 *****************************************/
	
	
	private Stage mainStage;
	private HelloApplication helloApp;
	
	//////////////// Still need to add labels for all ////////////////////
	
	// For easy change of window size
		private int windowX = 500;
		private int windowY = 500;
		
	
	
	// UI components for account creation
    private TextField createUsername;
    private TextField createPassword;
    private TextField confirmPassword;
    private Button createAccount;

    // UI components for login
    private TextField enterUsername;
    private TextField enterPassword;
    private TextField enterCode;
    private Button login;
    private Button register;
    
    // UI components for finish set up
    private TextField enterEmail;
    private TextField enterFirstName;
    private TextField enterMiddleName;
    private TextField enterLastName;
    private TextField enterPreferredName;
    private Button finish;
	
    // UI components for select role 
    private Button admin;
    private Button instructor;
    private Button student;
    
    // UI components for admin home page
    private Button adminLogout; 
    private Button invite;
    private Button resetUser;
    private Button deleteUser;
    private Button listUsers;
    private Button addRoleToUser;
    private Button removeRoleFromUser;
	private TextField enterUserID; // Used for all scenes related to admin so far 

	    // UI components for inviteUser
    private String giveInvCode;   	// Give invite code - String for testing
    private Button confirmRoles;	// Confirm roles given
    private CheckBox selAdmin;   	// Select admin
    private CheckBox selInstructor; // Select Instructor
    private CheckBox selStudent;  	// Select Student
    private Alert codeAlert;
    
    // UI components for resetUserPass 
    private String date;   			// String for testing
    private Button resetPass; 		// Button to reset password 
    
    // UI components for changePass   
    private TextField newPass;
    private TextField confirmNewPass; 
    private Button setPass;

	// UI components for addRoleToUser
	private CheckBox addAdmin;
	private CheckBox addInstructor;
	private CheckBox addStudent;
	private Button addRole;

	// UI components for removeRoleFromUser
	private CheckBox removeAdmin;
	private CheckBox removeInstructor;
	private CheckBox removeStudent;
	private Button removeRole;

	// UI components for delete user
	private Button deleteUserButton;
	private Alert sure;
    
	// UI components for list users
	private Button backButton;
    
    // UI components for instructor home page
    private Button instructorLogout; // If all logouts do same thing, can probably just use one logout button
    
    // UI components for student home page 
    private Button studentLogout;
    
    // Show Alert
    private Button okAlert;
    private Label Alert;
    

    private String inviteCode; // Only used to store the invite code for a few functions. Be careful how you use this. 


    /**
     * Initializer 
     * @param mainStage
     * @param helloApp
     */
    public void initialize(Stage mainStage, HelloApplication helloApp) {
    	this.mainStage = mainStage;
		this.helloApp = helloApp;
		switchScene(login_page()); // Placeholder 
    }
    
    /**
     * Create account 
     */
    public Scene create_account(String invite_code) {		
			
    		this.inviteCode = invite_code;
    	
			createUsername = new TextField(); 
			createUsername.setPromptText("Create a username: ");
			
			createPassword = new TextField(); 
			createPassword.setPromptText("Create a password: ");
			
			confirmPassword = new TextField();
			confirmPassword.setPromptText("Confirm your password: ");
			
			
			createAccount = new Button();
			createAccount.setText("Create Account"); 
	        
			createAccount.setOnAction(this); 
	            

		// Currently, it is a VBox, so some of the positioning stuff on the labels don't matter, but I am leaving it
		// as I may change it anyway (plus it still has the font and all that)

		Label labelCreateUsername = new Label("Create a username here: ");
		setupLabelUI(labelCreateUsername, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX/2, 30);
	
		createUsername = new TextField(); 
		createUsername.setPromptText("Create a username: ");

		Label labelCreatePassword = new Label("Create a password here: ");
		setupLabelUI(labelCreatePassword, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX/2, 30);
		
		createPassword = new TextField(); 
		createPassword.setPromptText("Create a password: ");

		Label labelConfirm = new Label("Confirm the password here: (It must match above!)");
		setupLabelUI(labelConfirm, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX/2, 30);
		
		confirmPassword = new TextField();
		confirmPassword.setPromptText("Confirm your password: ");
		

		createAccount = new Button();
		createAccount.setText("Create Account");  
        
		createAccount.setOnAction(this); 
            

        VBox root = new VBox(20);
		
		root.getChildren().addAll(labelCreateUsername, createUsername, labelCreatePassword, createPassword, labelConfirm, confirmPassword, createAccount);
		
		Scene createAccountScene = new Scene(root, windowX, windowY);
		return createAccountScene;
    }

    /**
     * Login
     * @param mainStage
     * @param helloApp
     */
	public Scene login_page() {
		
		mainStage.setTitle("Login or Register");
		
			Label labelEnterUsername = new Label("Enter the username here: ");
			setupLabelUI(labelEnterUsername, "Arial", 12, windowX, 
					Pos.BASELINE_LEFT, (windowX/8) + 20 , 30);
		
			enterUsername = new TextField(); 
			setupTextUI(enterUsername, "Arial", 12, 150, Pos.BASELINE_LEFT, (windowX/8 + 20), 50, "Username: ");
			
			Label labelEnterPassword = new Label("Enter the password here: ");
			setupLabelUI(labelEnterPassword, "Arial", 12, windowX, 
					Pos.BASELINE_LEFT, (windowX/2) + 20, 30);
			
			enterPassword = new TextField(); 
			setupTextUI(enterPassword, "Arial", 12, 150, Pos.BASELINE_LEFT, (windowX/2) + 20, 50, "Password: ");
			
			Label labelEnterCode = new Label("Enter the invite code here: ");
			setupLabelUI(labelEnterCode, "Arial", 12, windowX, 
					Pos.BASELINE_LEFT, (windowX/4) + 20, (windowY/2) - 30);

			enterCode = new TextField(); 
			setupTextUI(enterCode, "Arial", 12, 200, Pos.BASELINE_LEFT, (windowX/4) + 20, windowY/2, "Invitation code: ");
			
	        
	        login = new Button();
	        setupButtonUI(login, "Login", 100, Pos.CENTER, (windowX/2) - 50, 100);
	        
			register = new Button();
			setupButtonUI(register, "Register", 100, Pos.CENTER, (windowX/2) - 50, (windowY/2) + 40);
			
	        login.setOnAction(this);
	        
			register.setOnAction(this);
			
			
	        Pane root = new Pane();

	        root.getChildren().addAll(login, register, labelEnterUsername, enterUsername, labelEnterPassword, 
	        		enterPassword, labelEnterCode, enterCode);
			
			Scene loginScene = new Scene(root, windowX, windowY);
			return loginScene;
	}
	
	
	/**
	 * Finish set up Scene
	 */
	public Scene finishSetUp() {
		
		enterEmail = new TextField(); 
		enterEmail.setPromptText("Enter email: ");
		
		enterFirstName = new TextField(); 
		enterFirstName.setPromptText("Enter first name: ");
		
		enterMiddleName = new TextField(); 
		enterMiddleName.setPromptText("Enter middle name: ");
		
		enterLastName = new TextField(); 
		enterLastName.setPromptText("Enter last name: ");
		
		enterPreferredName = new TextField(); 
		enterPreferredName.setPromptText("Enter preferred name: ");
		
		finish = new Button();
		finish.setText("Finish Set Up");
		
		finish.setOnAction(this);
		
		// Layout
		
		VBox finishSetUpRoot = new VBox(20);
	
		finishSetUpRoot.getChildren().addAll(enterEmail, enterFirstName, enterMiddleName,
				enterLastName, enterPreferredName, finish);

		Scene finishSetUpScene = new Scene(finishSetUpRoot, windowX, windowY);
		return finishSetUpScene;
		
	}
	
	/**
	 * Role scene
	 * Incomplete, or need more scenes so that only the one's that apply show up
	 */
	public Scene selectRole(int[] roles) { 
		List<Button> roleButtons = new ArrayList<>();
		
		for(int i: roles) {
			if(i == 1) {
				admin = new Button("Admin");
				admin.setOnAction(this);
				roleButtons.add(admin);
			} else if (i == 2) {
				instructor = new Button("Instructor");
				instructor.setOnAction(this);
				roleButtons.add(instructor);
			} else if (i == 3) {
				student = new Button("Student");
				student.setOnAction(this);
				roleButtons.add(student);
			}
			
		}
		
		VBox selectRoleRoot = new VBox(20);
		
		selectRoleRoot.getChildren().addAll(roleButtons);

		Scene selectRoleScene = new Scene(selectRoleRoot, windowX, windowY);
		return selectRoleScene;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Scene adminHomePage() { 
		
		adminLogout = new Button();
		adminLogout.setText("Logout");
		adminLogout.setOnAction(this);
		
		invite = new Button();
		invite.setText("Invite User");
		invite.setOnAction(this);

		/*
		enterUser = new TextField();
		enterUser.setPromptText("Enter the user (ID/name)"); // Decide ID or username

		Label labelDecision = new Label("What do you want to do?");
		setupLabelUI(labelDecision, "Arial", 12, windowX, 
			Pos.CENTER, (windowX/2) + 20 , 30);
			*/
		
		resetUser = new Button();
		resetUser.setOnAction(this);

		resetUser.setText("Reset User Password");

		
		deleteUser = new Button();
		deleteUser.setText("Delete User");

		deleteUser.setOnAction(this);

		
		listUsers = new Button();
		listUsers.setText("List Users");
		listUsers.setOnAction(this);
		
		addRoleToUser = new Button();
		addRoleToUser.setOnAction(this);

		addRoleToUser.setText("Add Role to User");

		
		removeRoleFromUser = new Button();
		removeRoleFromUser.setOnAction(this);

		removeRoleFromUser.setText("Remove Role from User");
	
		
		
		
		VBox adminHomeRoot = new VBox(20);
		
		adminHomeRoot.getChildren().addAll(invite, listUsers, resetUser, deleteUser, addRoleToUser, removeRoleFromUser, adminLogout);

		Scene adminHomeScene = new Scene(adminHomeRoot, windowX, windowY);
		return adminHomeScene;
	}

	public Scene inviteUser() {
		
		selAdmin = new CheckBox();
		selAdmin.setText("Admin");
		
		selInstructor = new CheckBox();
		selInstructor.setText("Instructor");
		
		selStudent = new CheckBox();
		selStudent.setText("Student");
		
		confirmRoles = new Button();
		confirmRoles.setText("Confirm");
		confirmRoles.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);
		
		VBox inviteUserRoot = new VBox(20);
		
		inviteUserRoot.getChildren().addAll(selAdmin, selInstructor, selStudent, confirmRoles, backButton);
		
		Scene inviteUserScene = new Scene(inviteUserRoot, windowX, windowY);
		return inviteUserScene;
	}


	
	
	//////// TESTING ONLY, WILL DELETE //////////////
	/////// USED FOR listUser (below) IN PLACE OF ACTUAL USERS CLASS ///////////////
	public static class User {
		private String username;
		private String firstName;
		private int code;
		
		public User(String username, String firstName, int code) {
			this.username = username;
			this.firstName = firstName;
			this.code = code;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public int getCode() {
			return code;
		}
		
	}
	
	///////// END TESTING ////////////

	/**
	 * Finish list 
	 */
	public Scene listUsers(/*List<User> users*/) { // If using the TESTING getItems, take away parameter
		// users from Users class 
		TableView<User> userTable = new TableView<>();
		
		// userTable.getItems().addAll(users);
		
		// TESTING, long list to test scrolling works properly, will be deleted
		userTable.getItems().addAll(new User("User_A", "Name_A", 1),
				new User("User_B", "Name_B", 2), new User("User_C", "Name_C", 3),
				new User("User_D", "Name_D", 4), new User("User_E", "Name_E", 5), new User("User_A", "Name_A", 1),
				new User("User_B", "Name_B", 2), new User("User_C", "Name_C", 3),
				new User("User_D", "Name_D", 4), new User("User_E", "Name_E", 5), new User("User_A", "Name_A", 1),
				new User("User_B", "Name_B", 2), new User("User_C", "Name_C", 3),
				new User("User_D", "Name_D", 4), new User("User_E", "Name_E", 5), new User("User_A", "Name_A", 1),
				new User("User_B", "Name_B", 2), new User("User_C", "Name_C", 3),
				new User("User_D", "Name_D", 4), new User("User_E", "Name_E", 5), new User("User_A", "Name_A", 1),
				new User("User_B", "Name_B", 2), new User("User_C", "Name_C", 3),
				new User("User_D", "Name_D", 4), new User("User_E", "Name_E", 5), new User("User_A", "Name_A", 1),
				new User("User_B", "Name_B", 2), new User("User_C", "Name_C", 3),
				new User("User_D", "Name_D", 4), new User("User_E", "Name_E", 5));
		///////////////
		
		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
		TableColumn<User, String> nameColumn = new TableColumn<>("First Name");
		TableColumn<User, Integer> codeColumn = new TableColumn<>("Role Code");
		
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
		
		@SuppressWarnings("unchecked")
		TableColumn<User, ?>[] columns = new TableColumn[] {usernameColumn, nameColumn, codeColumn};
		userTable.getColumns().addAll(columns);	
		
		backButton = new Button();
		backButton.setText("Back");
		
		backButton.setOnAction(this);
		
		VBox listUsersRoot = new VBox(userTable, backButton);
		
		ScrollPane scrollPane = new ScrollPane(listUsersRoot);
		// scrollPane.setFitToWidth(true);
		
		Scene listUsersScene = new Scene(scrollPane, windowX, windowY);
		return listUsersScene;
	}
	
	/**
	 * For admin to reset password
	 * @return
	 */
	public Scene resetUserPass() { 
		
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter user to password reset: ");
		
		resetPass = new Button();
		resetPass.setText("Reset");
		resetPass.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);
		
		VBox resetUserRoot = new VBox(20);
		
		resetUserRoot.getChildren().addAll(enterUserID, resetPass, backButton);
		
		Scene resetUserScene = new Scene(resetUserRoot, windowX, windowY);
		return resetUserScene;
		
	}
	
	/**
	 * For user to reset password after given code 
	 * @return
	 */
	public Scene changePass() {
		
		newPass = new TextField();
		newPass.setPromptText("Enter your new password: ");
		
		confirmNewPass = new TextField(); 
		confirmNewPass.setPromptText("Confirm your password: ");
		
		setPass = new Button();
		setPass.setText("Change Password");
		setPass.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);
		
		
		VBox changePassRoot = new VBox(20);
		
		changePassRoot.getChildren().addAll(newPass, confirmNewPass, setPass, backButton);
		
		Scene changePassScene = new Scene(changePassRoot, windowX, windowY);
		return changePassScene;
	}

	public Scene deleteUser() {

		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user id");
		
		deleteUser = new Button();
		deleteUser.setText("Delete");
		deleteUser.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);

		VBox deleteUserRoot = new VBox(20);

		deleteUserRoot.getChildren().addAll(enterUserID, deleteUser, backButton);

		Scene deleteUserScene = new Scene(deleteUserRoot, windowX, windowY);
		return deleteUserScene;

	}

	public Scene addRoleToUser() {

		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user (ID/name)");

		addAdmin = new CheckBox();
		addAdmin.setText("Admin");

		addInstructor = new CheckBox();
		addInstructor.setText("Instructor");

		addStudent = new CheckBox();
		addStudent.setText("Student");

		addRole = new Button();
		addRole.setText("Add");
		addRole.setOnAction(this);

		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);
		
		VBox addRoleRoot = new VBox(20);
		
		addRoleRoot.getChildren().addAll(enterUserID, addAdmin, addInstructor, addStudent, addRole, backButton);
		
		Scene addRoleScene = new Scene(addRoleRoot, windowX, windowY);
		return addRoleScene;
	}
	
	public Scene removeRoleFromUser() {

		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user (ID/name)");

		removeAdmin = new CheckBox();
		removeAdmin.setText("Admin");

		removeInstructor = new CheckBox();
		removeInstructor.setText("Instructor");

		removeStudent = new CheckBox();
		removeStudent.setText("Student");

		removeRole = new Button();
		removeRole.setText("Remove");
		removeRole.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);

		VBox removeRoleRoot = new VBox(20);
		
		removeRoleRoot.getChildren().addAll(enterUserID, removeAdmin, removeInstructor, removeStudent, removeRole, backButton);
		
		Scene removeRoleScene = new Scene(removeRoleRoot, windowX, windowY);
		return removeRoleScene;
	}

	public Scene instructorHomePage() {
		
		instructorLogout = new Button();
		instructorLogout.setText("Instructor");
		instructorLogout.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);

		instructorLogout.setText("Logout");

		
		VBox instructorHomeRoot = new VBox(20);
		
		instructorHomeRoot.getChildren().addAll(instructorLogout, backButton);

		Scene instructorHomeScene = new Scene(instructorHomeRoot, windowX, windowY);
		return instructorHomeScene;
	}
	
	
	public Scene studentHomePage() {
		
		studentLogout = new Button();
		studentLogout.setText("Student");
		studentLogout.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);
		studentLogout.setText("Logout");

		
		VBox studentHomeRoot = new VBox(20);
		
		studentHomeRoot.getChildren().addAll(studentLogout, backButton);

		Scene studentHomeScene = new Scene(studentHomeRoot, windowX, windowY);
		return studentHomeScene;
	}
	
	
	/**
	 * Switch Scene
	 * May be obsolete/implemented wrong
	 * @param newScene
	 */
	public void switchScene(Scene newScene) {
	    mainStage.setScene(newScene);
	    mainStage.show();
	}
	
	/**
	 * Set title
	 * @param title
	 */
	public void setTitle(String title) {
		mainStage.setTitle(title);
	}
	
	
	/**
	 * Handle button press
	 * Not used?
	 * 
	 */
	private void handleButtonPress() { //Triggered when someone presses the button
		if(helloApp != null) {
			helloApp.onButtonPressed(); //Calls a function in HelloApplication
		}
	}	
	
	/**
	 * Handle button
	 */
	public void handle(ActionEvent event) {
		
		// NOTES
		// I commented out the calls to HelloApplication temporarily to test just switching scenes
		
		// Create Account Handling 
		if(event.getSource() == createAccount) {
			String username = createUsername.getText();
			String password1 = createPassword.getText();
			String password2 = confirmPassword.getText();

			
			
			// check password is valid
			
			// check passwords match
			if(password1.equals(password2)) {
				createUsername.clear();
				createPassword.clear();
				confirmPassword.clear();
				helloApp.createUser(this.inviteCode, username, password1);
				this.inviteCode = null;
			}
			else {
				showAlert("Passwords do not match");
			}


		}

		// Login Handling 
		else if (event.getSource() == login) {
			String username = enterUsername.getText();
            String password = enterPassword.getText();

            enterUsername.clear();
            enterPassword.clear();
            

        	helloApp.handleLoginAttempt(username, password);

		}

		// Register Handling (Submitting Invitation Code)
		else if (event.getSource() == register) {
           
			String code = enterCode.getText();
            enterCode.clear();
            // Check that code is exists
            
            helloApp.handleInviteCodeAttempt(code);
		}

		// Finish Set Up Handling 
		else if (event.getSource() == finish) {
            String email = enterEmail.getText();
            String firstName = enterFirstName.getText();
            String middleName = enterMiddleName.getText();
            String lastName = enterLastName.getText();
            String preferredName = enterPreferredName.getText();
            
            enterEmail.clear();
            enterFirstName.clear();
            enterMiddleName.clear();
            enterLastName.clear();
            enterPreferredName.clear();
            
            
            helloApp.finishUserSetup(email, firstName, middleName, lastName, preferredName);
		}

		
		else if (event.getSource() == admin) {
			System.out.print("Admin Button Pressed");

			switchScene(adminHomePage());
		}
		
		else if (event.getSource() == instructor) {
			switchScene(instructorHomePage());
		}
		
		else if (event.getSource() == student) {
			switchScene(studentHomePage());
		}
		
		else if (event.getSource() == invite) {
			switchScene(inviteUser());
		}
		
		else if (event.getSource() == confirmRoles) {
			boolean adminSelected = selAdmin.isSelected();
			boolean instructorSelected = selInstructor.isSelected();
			boolean studentSelected = selStudent.isSelected();
			codeAlert = new Alert(AlertType.INFORMATION);
			/*
			if (adminSelected) {
				// Add admin role
			}
			if (instructorSelected) {
				// Add instructor role
			}
			if (studentSelected) {
				// Add student role
			}
			else {
				// Error, no roles selected
				// Perhaps if none selected, automatically make student
				codeAlert.setTitle("Error");
				codeAlert.setContentText("Select at least one role!");
			}
			// If no error
			
			
			// Just testing to get alert to work 
			*/
			
			
			// Need to fix this stuff 
			switchScene(adminHomePage());
			
			codeAlert.setTitle("Invitation Code!");
			codeAlert.setContentText("Code");
			
			codeAlert.showAndWait();

		}
		
		
		else if (event.getSource() == resetPass) {
			// check username exists?
			//int userID = enterUserID.();
			//helloApp.adminResetPassword(userID);
		}

		else if (event.getSource() == setPass) {
			// Check if passwords match
			String newPassword = newPass.getText();
		//	helloApp.resetUserPassword(newPassword);
			switchScene(changePass());
		}
		
		else if (event.getSource() == resetUser) {
			switchScene(resetUserPass());
		}

		else if (event.getSource() == deleteUser) {
			// String userToDelete = enterUserID.getText();

			// I'll fix alert
			//sure.setTitle("Are you sure?");
			//sure.setContentText("This will be permanent. Are you sure you want to delete user " + userToDelete + "?");
			//sure.showAndWait();
			// Are you sure alert
			// If yes, delete
			// If no, 
			switchScene(deleteUser());
		}
		
		else if (event.getSource() == listUsers) {
			switchScene(listUsers());
		}
		else if (event.getSource() == addRoleToUser) {
			// String userID = enterUserID.getText();
			// add
			switchScene(addRoleToUser());
		}
		else if (event.getSource() == removeRoleFromUser) {
			// String userID = enterUserID.getText();
			switchScene(removeRoleFromUser());
		}
		
		else if (event.getSource() == backButton) {
			switchScene(adminHomePage());
		}
		
		
		
	
		
		
		
	}
	
	public void showAlert(String message) {
	    // Create an Alert dialog
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Information");
	    alert.setHeaderText(null);
	    alert.setContentText(message);

	    // Make the dialog blocking (modal)
	    alert.showAndWait();  // This method blocks until the user closes the dialog
	}

	/**************************************
	 * Note: Stuff below not used much yet, got it from HW#5, will probably use to pretty up GUI soon
	 ***************************************/
	
	/**
	 * Label UI for code readability 
	 * @param l
	 * @param ff
	 * @param f
	 * @param w
	 * @param p
	 * @param x
	 * @param y
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	/**
	 * Button UI for code readability 
	 * @param btn
	 * @param ff
	 * @param w
	 * @param p
	 * @param x
	 * @param y
	 */
	private void setupButtonUI(Button btn, String ff, double w, Pos p, double x, double y){
		btn.setText(ff);
		btn.setMinWidth(w);
		btn.setAlignment(p);
		btn.setLayoutX(x);
		btn.setLayoutY(y);		
	}
	
	/**
	 * Text UI for code readability 
	 * @param t
	 * @param ff
	 * @param f
	 * @param w
	 * @param p
	 * @param x
	 * @param y
	 * @param pr
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, String pr){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);	
		t.setPromptText(pr);
	}
	
	
}

