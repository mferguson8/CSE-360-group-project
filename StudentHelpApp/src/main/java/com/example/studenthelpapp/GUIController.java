package com.example.studenthelpapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
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
// Add labels (stuff I can do myself though)


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
    private Button logout; // Used for all home pages 
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
    
    
    // UI components for student home page 

    
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
		//switchScene(login_page()); // Placeholder 
		switchScene(adminHomePage()); //TODO FOR TEST. MUST REMOVE
    }
    
    /**
     * Create account 
     */
    public Scene create_account(String invite_code) {		

    	setTitle("Create Account");

		Label labelCreateAccount = new Label("Create An Account!");
		setupLabelUI(labelCreateAccount, "Arial", 18, windowX, 
				Pos.CENTER, windowX/2, 30);
    	
    	setTitle("Create Account");

		Label labelCreateUsername = new Label("Create a username here: ");
		setupLabelUI(labelCreateUsername, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX/2, 30);
	
		createUsername = new TextField(); 
		createUsername.setPromptText("Create a username: ");
		createUsername.setPromptText("Enter username: ");
		createUsername.setMaxWidth(windowX - 20);
		createUsername.setMinHeight(30);

		Label labelCreatePassword = new Label("Create a password here: ");
		setupLabelUI(labelCreatePassword, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX/2, 30);
		
		createPassword = new TextField(); 
		createPassword.setPromptText("Create a password: ");
		createPassword.setPromptText("Enter password: ");
		createPassword.setMaxWidth(windowX - 20);
		createPassword.setMinHeight(30);

		Label labelConfirm = new Label("Confirm the password here: (It must match above!)");
		setupLabelUI(labelConfirm, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX/2, 30);
		
		confirmPassword = new TextField();
		confirmPassword.setPromptText("Confirm your password: ");
		confirmPassword.setPromptText("Confirm password: ");
		confirmPassword.setMaxWidth(windowX - 20);
		confirmPassword.setMinHeight(30);
		

		createAccount = new Button();
		createAccount.setText("Create Account");  
        
		createAccount.setOnAction(this); 
            

        VBox root = new VBox(20);
		
		root.getChildren().addAll(labelCreateAccount, labelCreateUsername, createUsername, labelCreatePassword, createPassword,
				labelConfirm, confirmPassword, createAccount);
		
		Scene createAccountScene = new Scene(root, windowX, windowY);
		return createAccountScene;
    }
	
    /**
     * Login
     * @param mainStage
     * @param helloApp
     */
	public Scene login_page() {
		
		setTitle("Login or Register");
		
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
		
		Label labelFinish = new Label("You must finish setting up your account before logging in!");
		setupLabelUI(labelFinish, "Arial", 18, windowX, 
				Pos.CENTER, 1, 10);
		
		Label labelEmail = new Label("Enter your email: ");
		setupLabelUI(labelEmail, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX, 30);
		
		enterEmail = new TextField(); 
		enterEmail.setPromptText("Enter email: ");
		enterEmail.setMaxWidth(windowX - 20);
		enterEmail.setMinHeight(30);
		
		Label labelFirstName = new Label("Enter the username here: ");
		setupLabelUI(labelFirstName, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX, 30);
		
		enterFirstName = new TextField(); 
		enterFirstName.setPromptText("Enter first name: ");
		enterFirstName.setMaxWidth(windowX - 20);
		enterFirstName.setMinHeight(30);
		
		Label labelMiddleName = new Label("Enter your middle name: ");
		setupLabelUI(labelMiddleName, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX, 30);
		
		enterMiddleName = new TextField(); 
		enterMiddleName.setPromptText("Enter middle name: ");
		enterMiddleName.setMaxWidth(windowX - 20);
		enterMiddleName.setMinHeight(30);
		
		Label labelLastName = new Label("Enter your last name: ");
		setupLabelUI(labelLastName, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX, 30);
		
		enterLastName = new TextField(); 
		enterLastName.setPromptText("Enter last name: ");
		enterLastName.setMaxWidth(windowX - 20);
		enterLastName.setMinHeight(30);
		
		Label labelPreferredName = new Label("Enter your preferred name: ");
		setupLabelUI(labelPreferredName, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, windowX, 30);
		
		enterPreferredName = new TextField(); 
		enterPreferredName.setPromptText("Enter preferred name: ");
		enterPreferredName.setMaxWidth(windowX - 20);
		enterPreferredName.setMinHeight(30);
		
		finish = new Button();
		finish.setText("Finish Set Up");
		finish.setOnAction(this);
		
		
		VBox finishSetUpRoot = new VBox(15);
	
		finishSetUpRoot.getChildren().addAll(labelFinish, labelEmail, enterEmail, labelFirstName, enterFirstName, 
				labelMiddleName, enterMiddleName, labelLastName, enterLastName, labelPreferredName, enterPreferredName, finish);

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
			if(i == RoleCodes.ADMIN.get()) {
				admin = new Button("Admin");
				admin.setOnAction(this);
				roleButtons.add(admin);
			} else if (i == RoleCodes.INSTRUCTOR.get()) {
				instructor = new Button("Instructor");
				instructor.setOnAction(this);
				roleButtons.add(instructor);
			} else if (i == RoleCodes.STUDENT.get()) {
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
		
		setTitle("Home Page");
		
		Label labelCommand = new Label("What do you want to do?");
		setupLabelUI(labelCommand, "Arial", 18, windowX, 
				Pos.CENTER, 1, 10);
		
		Label labelInvite = new Label("Reset a user's password: ");
		setupLabelUI(labelInvite, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, (windowX/6) - 60, (windowY/4) - 70);
		
		invite = new Button();
		setupButtonUI(invite, "Invite User", 200, Pos.CENTER, (windowX/6) - 60, (windowY/4) - 50);
		invite.setOnAction(this);
	
		Label labelListUsers = new Label("List all application users: ");
		setupLabelUI(labelListUsers, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, (2*windowX/3) - 60, (windowY/4) - 70);
		
		listUsers = new Button();
		setupButtonUI(listUsers, "List Users", 200, Pos.CENTER, (2*windowX/3) - 60, (windowY/4) - 50);
		listUsers.setOnAction(this);
		
		
		Label labelResetUser = new Label("Reset a user's password: ");
		setupLabelUI(labelResetUser, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, (windowX/6) - 60, (windowY/2) - 70);
		
		resetUser = new Button();
		resetUser.setText("Reset User");
		resetUser.setOnAction(this);

		resetUser.setText("Reset User Password");

		
		Label labelDeleteUser = new Label("Permanently delete a user: ");
		setupLabelUI(labelDeleteUser, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, (2*windowX/3) - 60, (windowY/2) - 70);
		
		deleteUser = new Button();
		deleteUser.setText("Delete User");
		deleteUser.setOnAction(this);

		
		Label labelAddRole= new Label("Permanently delete a user: ");
		setupLabelUI(labelAddRole, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, (windowX/6) - 60, (3*windowY/4) - 70);
		
		addRoleToUser = new Button();
		addRoleToUser.setText("Add Role");
		addRoleToUser.setOnAction(this);

		addRoleToUser.setText("Add Role to User");

		
		Label labelRemoveRole = new Label("Permanently delete a user: ");
		setupLabelUI(labelRemoveRole, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, (2*windowX/3) - 60, (3*windowY/4) - 70);
		
		removeRoleFromUser = new Button();
		removeRoleFromUser.setText("Remove Role");
		removeRoleFromUser.setOnAction(this);

		removeRoleFromUser.setText("Remove Role from User");
	
		
		logout = new Button();
		setupButtonUI(logout, "Logout", 100, Pos.CENTER, (windowX/2) - 50, windowY - 50);
		logout.setOnAction(this);
		
		
		
		Pane adminHomeRoot = new Pane();
		
		adminHomeRoot.getChildren().addAll(labelCommand, invite, listUsers, resetUser, deleteUser, addRoleToUser, removeRoleFromUser, 
				labelResetUser, labelListUsers, labelInvite, labelDeleteUser, labelAddRole, labelRemoveRole, logout);

		Scene adminHomeScene = new Scene(adminHomeRoot, windowX, windowY);
		return adminHomeScene;
	}

	public Scene inviteUser() {
		
		setTitle("Invite User");
		
		Label labelInvite = new Label("Please check which roles you want to give the new user: ");
		setupLabelUI(labelInvite, "Arial", 18, windowX, 
				Pos.CENTER, 1, 20);
		
		selAdmin = new CheckBox();
		selAdmin.setText("Admin");
		selAdmin.setLayoutX(20);
		selAdmin.setLayoutY(50);
		
		selInstructor = new CheckBox();
		selInstructor.setText("Instructor");
		selInstructor.setLayoutX(20);
		selInstructor.setLayoutY(70);
		
		selStudent = new CheckBox();
		selStudent.setText("Student");
		selStudent.setLayoutX(20);
		selStudent.setLayoutY(90);
		
		confirmRoles = new Button();
		setupButtonUI(confirmRoles, "Confirm", 50, Pos.CENTER, 20, 120);

		confirmRoles.setOnAction(this);
		
		backButton = new Button();
		setupButtonUI(backButton, "Back", 50, Pos.CENTER, 5, windowY - 30);
		backButton.setOnAction(this);
		
		Pane inviteUserRoot = new Pane();
		
		inviteUserRoot.getChildren().addAll(labelInvite, selAdmin, selInstructor, selStudent, confirmRoles, backButton);
		
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

		setTitle("List Users");
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
		
		userTable.getItems().addAll(users);
		
		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
		TableColumn<User, String> nameColumn = new TableColumn<>("First Name");
		TableColumn<User, String> codeColumn = new TableColumn<>("Role Codes");
		
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("codes"));
		
		@SuppressWarnings("unchecked")
		TableColumn<User, ?>[] columns = new TableColumn[] {usernameColumn, nameColumn, codeColumn};
		userTable.getColumns().addAll(columns);	
		
		backButton = new Button();
		backButton.setText("Back");
		
		backButton.setOnAction(this);
		
		VBox listUsersRoot = new VBox(20, userTable, backButton);
		
		ScrollPane scrollPane = new ScrollPane(listUsersRoot);
		scrollPane.setFitToWidth(true);
		
		Scene listUsersScene = new Scene(scrollPane, windowX, windowY);
		return listUsersScene;
	}
	
	/**
	 * For admin to reset password
	 * @return
	 */
	public Scene resetUserPass() { 
		
		setTitle("Resetting User Password");
		
		Label labelResetPass = new Label("Reset a User's Password");
		setupLabelUI(labelResetPass, "Arial", 14, windowX, 
				Pos.CENTER, 1, 10);
		
		Label labelUserID = new Label("Enter the user's ID: ");
		setupLabelUI(labelUserID, "Arial", 12, windowX, 
				Pos.BASELINE_LEFT, 10, 50);
		
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter user to password reset: ");
		
		resetPass = new Button();
		setupButtonUI(resetPass, "Reset", 100, Pos.CENTER, (windowX/2) - 50, 100);
		resetPass.setOnAction(this);
		
		backButton = new Button();
		setupButtonUI(backButton, "Back", 50, Pos.CENTER, 5, windowY - 30);
		backButton.setOnAction(this);
		
		Pane resetUserRoot = new Pane();
		
		resetUserRoot.getChildren().addAll(labelResetPass, labelUserID, enterUserID, resetPass, backButton);
		
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
		setupButtonUI(backButton, "Back", 50, Pos.CENTER, 5, windowY - 30);
		backButton.setOnAction(this);
			

		deleteUserRoot.getChildren().addAll(enterUserID, deleteUser, backButton);

		Scene deleteUserScene = new Scene(deleteUserRoot, windowX, windowY);
		return deleteUserScene;

	}

	public Scene addRoleToUser() {
		
		setTitle("Add Roles to User");
		
		Label labelUserID = new Label("Enter the user ID of the user to edit: ");
		setupLabelUI(labelUserID, "Arial", 14, windowX, 
				Pos.BASELINE_LEFT, 1, 20);
		
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user (ID/name)");

		addAdmin = new CheckBox();
		addAdmin.setText("Admin");
		addAdmin.setLayoutX(20);
		addAdmin.setLayoutY(100);

		addInstructor = new CheckBox();
		addInstructor.setText("Instructor");
		addInstructor.setLayoutX(20);
		addInstructor.setLayoutY(120);

		addStudent = new CheckBox();
		addStudent.setText("Student");
		addStudent.setLayoutX(20);
		addStudent.setLayoutY(140);

		addRole = new Button();
		setupButtonUI(addRole, "Add", 50, Pos.CENTER, 20, 170);
		addRole.setOnAction(this);

		backButton = new Button();
		setupButtonUI(backButton, "Back", 50, Pos.CENTER, 5, windowY - 30);
		backButton.setOnAction(this);
		
		Pane addRoleRoot = new Pane();
		
		addRoleRoot.getChildren().addAll(labelAddRoles, labelUserID, enterUserID, addAdmin, addInstructor, addStudent, addRole, backButton);
		
		Scene addRoleScene = new Scene(addRoleRoot, windowX, windowY);
		return addRoleScene;
	}
	
	public Scene removeRoleFromUser() {

		setTitle("Add Roles to User");
		
		Label labelUserID = new Label("Enter the user ID of the user to edit: ");
		setupLabelUI(labelUserID, "Arial", 14, windowX, 
				Pos.BASELINE_LEFT, 1, 20);
		
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user (ID/name)");

		removeAdmin = new CheckBox();
		removeAdmin.setText("Admin");
		removeAdmin.setLayoutX(20);
		removeAdmin.setLayoutY(100);

		removeInstructor = new CheckBox();
		removeInstructor.setText("Instructor");
		removeInstructor.setLayoutX(20);
		removeInstructor.setLayoutY(120);

		removeStudent = new CheckBox();
		removeStudent.setText("Student");
		removeStudent.setLayoutX(20);
		removeStudent.setLayoutY(140);

		removeRole = new Button();
		setupButtonUI(removeRole, "Add", 50, Pos.CENTER, 20, 170);
		removeRole.setOnAction(this);

		backButton = new Button();
		setupButtonUI(backButton, "Back", 50, Pos.CENTER, 5, windowY - 30);
		backButton.setOnAction(this);
		
		Pane removeRoleRoot = new Pane();
		
		removeRoleRoot.getChildren().addAll(labelRemoveRoles, labelUserID, enterUserID, removeAdmin, removeInstructor, removeStudent, removeRole, backButton);
		
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
		
		VBox studentHomeRoot = new VBox(20);
		
		studentHomeRoot.getChildren().addAll(studentLogout);

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
			
		//	switchScene(create_account(code));

            helloApp.handleInviteCodeAttempt(code); 
		}

		// Finish Set Up Handling 
		else if (event.getSource() == finish) {
            String email = enterEmail.getText();
            String firstName = enterFirstName.getText();
            String middleName = enterMiddleName.getText();
            String lastName = enterLastName.getText();
            String preferredName = enterPreferredName.getText();
            
           
            
            Email.EmailResult email_check = Email.make_email(email);
            switch(email_check.check()) {
            	case NO_DOM:
            		showAlert("Email has no domain");
            		return;
            	case MULT_ATS:
            		showAlert("Email has multiple @ symbols");
            		return;
            	case INV_DOM:
            		showAlert("Not a valid domain name");
            		return;
            	case SUCCESS:
            }
            Name.NameResult name_check;
            Name.NameResult.Position[] positionsToCheck;
            if(preferredName.trim().isEmpty()) {
            	name_check = Name.noPreferred(firstName, middleName, lastName);
            	positionsToCheck = new Name.NameResult.Position[]{Name.NameResult.Position.FIRST, 
            			Name.NameResult.Position.MIDDLE,
            			Name.NameResult.Position.LAST};
            } else {
            	name_check = Name.hasPreferred(firstName, middleName, lastName, preferredName);
            	positionsToCheck = new Name.NameResult.Position[]{Name.NameResult.Position.FIRST, 
            			Name.NameResult.Position.MIDDLE,
            			Name.NameResult.Position.LAST,
            			Name.NameResult.Position.PREFERRED};
            }
            Name names = name_check.getName();
            if(names == null) {
            	StringBuilder nameError = new StringBuilder();
            	nameError.append("Names don't meet the following requirements:");
	            for(Name.NameResult.Position currentPosition: positionsToCheck) {
            		switch(name_check.checkNV(currentPosition)) {
	            		case Name.NameResult.Status.START_NUM:
	            			nameError.append("First name starts with numbers; ");
	            			break;
	            		case Name.NameResult.Status.HAS_SPEC:
	            			nameError.append("First name contains special characters; ");
	            			break;
	            		case Name.NameResult.Status.DNE:
	            			nameError.append("ERROR: First name does not exist; ");
	            			break;
	            		case Name.NameResult.Status.OOPN:
	            			nameError.append("First name has an out of place number; ");
	            			break;
	            		case Name.NameResult.Status.SUCCESS:
	            		default:
	            	}
	            }
	          showAlert(nameError.toString()); 
	          return;
	          //This might be really long and I'm not sure how showAlert will deal with that.
	          //TODO: Test this.
            }
            
            
            
            
            
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
			codeAlert.setContentText("The invitation code is: "+code);
			
			codeAlert.showAndWait();
			// Need to fix this stuff
			switchScene(adminHomePage());
		}
		
		
		else if (event.getSource() == resetPass) {
			String username = enterUserID.getText();
			Integer id = helloApp.getUsernameId(username);
			if(id != null) {
				enterUserID.clear();
				helloApp.adminResetPassword(id);
				switchScene(adminHomePage());
			} else {
				showAlert("User with that Username not found");
			}
		}

		else if (event.getSource() == setPass) {
			// Check if passwords match
			String newPassword = newPass.getText();
			String copyPassword = confirmNewPass.getText();
			if(newPassword.equals(copyPassword))
			{
				newPass.clear();
				confirmNewPass.clear();
				helloApp.resetUserPassword(newPassword);
			} else {
				
				showAlert("Passwords do not match");
				
			}
		}
		
		else if (event.getSource() == resetUser) {
			//Button on the admin home page that takes you to the password reset scene
			switchScene(resetUserPass());
		}

		else if (event.getSource() == deleteUser) {
			//Button on the admin home page that takes you to the delete user scene
			switchScene(deleteUser());
		}
		else if (event.getSource() == deleteUserButton) {
			String userToDelete = enterUserID.getText();
			Integer id = helloApp.getUsernameId(userToDelete);
			if(id != null) {
				sure = new Alert(AlertType.CONFIRMATION);
				sure.setTitle("Are you sure?");
				sure.setContentText("This will be permanent. Are you sure you want to delete user " + userToDelete + "?");
				ButtonType yesButton = new ButtonType("Yes");
				ButtonType noButton = new ButtonType("No");
				sure.getButtonTypes().setAll(yesButton, noButton);
				Optional<ButtonType> result = sure.showAndWait();
				if(result.isPresent() && (result.get() == yesButton)) {
					enterUserID.clear();
					helloApp.deleteUser(id);
				} else if(result.isPresent() && (result.get() == noButton)) {
					enterUserID.clear();
				}
			} else {
				showAlert("No user found with that username");
			}
		}
		
		else if (event.getSource() == listUsers) {
			switchScene(listUsers());
		}
		else if (event.getSource() == addRoleToUser) {
			switchScene(addRoleToUser());
		}
		else if (event.getSource() == removeRoleFromUser) {
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

