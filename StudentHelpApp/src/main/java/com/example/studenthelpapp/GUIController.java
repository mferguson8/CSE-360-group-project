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
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
//import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


// Notes: Didn't use original button handling
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
	private Scene mainScene;
	private HelloApplication helloApp;
	
	// For easy change of window size
	private int windowX = 500;
	private int windowY = 500;
	
	
	//////////////// Still need to add labels for all ////////////////////
	
	
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
	private Button deleteUser;
	private Alert sure;
    
    
    // UI components for instructor home page
    private Button instructorLogout; // If all logouts do same thing, can probably just use one logout button
    
    // UI components for student home page 
    private Button studentLogout;
    
    // Show Alert
    private Button okAlert;
    private Label Alert;
    

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
    public Scene create_account() {		

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
		
		invite = new Button();
		invite.setText("Invite User");

		/*
		enterUser = new TextField();
		enterUser.setPromptText("Enter the user (ID/name)"); // Decide ID or username

		Label labelDecision = new Label("What do you want to do?");
		setupLabelUI(labelDecision, "Arial", 12, windowX, 
			Pos.CENTER, (windowX/2) + 20 , 30);
			*/
		
		resetUser = new Button();
		resetUser.setText("Reset User");
		
		deleteUser = new Button();
		deleteUser.setText("Delete User");
		
		listUsers = new Button();
		listUsers.setText("List Users");
		
		addRoleToUser = new Button();
		addRoleToUser.setText("Add Role");
		
		removeRoleFromUser = new Button();
		removeRoleFromUser.setText("Remove Role");
		
		
		
		VBox adminHomeRoot = new VBox(20);
		
		adminHomeRoot.getChildren().addAll(invite, listUsers, enterUser, labelDecision, resetUser, deleteUser, addRoleToUser, removeRoleFromUser, adminLogout);

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
		
		VBox inviteUserRoot = new VBox(20);
		
		inviteUserRoot.getChildren().addAll(selAdmin, selInstructor, selStudent, confirmRoles);
		
		Scene inviteUserScene = new Scene(inviteUserRoot, windowX, windowY);
		return inviteUserScene;
	}

	public Scene listUsers() {
		// list
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
		
		VBox inviteUserRoot = new VBox(20);
		
		inviteUserRoot.getChildren().addAll(enterUser, resetPass);
		
		Scene inviteUserScene = new Scene(inviteUserRoot, windowX, windowY);
		return inviteUserScene;
		
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
		
		
		VBox changePassRoot = new VBox(20);
		
		changePassRoot.getChildren().addAll(newPass, confirmNewPass, setPass);
		
		Scene changePassScene = new Scene(changePassRoot, windowX, windowY);
		return changePassScene;
	}

	public Scene deleteUser() {

		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user id")

		VBox deleteUserRoot = new VBox(20);

		deleteUserRoot.getChildren().add(enterUserID);

		Scene deleteUserScene = new Scene(deleteUserRoot, windowX, windowY);
		return deleterUserScene;

	}

	public Scene addRoleToUser() {

		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user (ID/name)");

		addAdmin = new CheckBox();
		addAdmin.setPromptText("Admin");

		addInstructor = new CheckBox();
		addInstructor.setPromptText("Instructor");

		addStudent = new CheckBox();
		addStudent.setPromptText("Student");

		addRole = new Button();
		addRole.setText("Add");

		VBox addRoleRoot = new VBox(20);
		
		addRoleRoot.getChildren().addAll(enterUserID, addAdmin, addInstructor, addStudent, addRole);
		
		Scene addRoleScene = new Scene(addRoleRoot, windowX, windowY);
		return addRoleScene;
	}
	
	public Scene removeRoleFromUser() {

		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the user (ID/name)");

		removeAdmin = new CheckBox();
		removeAdmin.setPromptText("Admin");

		removeInstructor = new CheckBox();
		removeInstructor.setPromptText("Instructor");

		removeStudent = new CheckBox();
		removeStudent.setPromptText("Student");

		removeRole = new Button();
		removeRole.setText("Remove");

		VBox removeRoleRoot = new VBox(20);
		
		removeRoleRoot.getChildren().addAll(enterUserID, removeAdmin, removeInstructor, removeStudent, removeRole);
		
		Scene removeRoleScene = new Scene(addRoleRoot, windowX, windowY);
		return removeRoleScene;
	}

	public Scene instructorHomePage() {
		
		instructorLogout = new Button();
		instructorLogout.setText("Instructor");
		
		VBox instructorHomeRoot = new VBox(20);
		
		instructorHomeRoot.getChildren().addAll(instructorLogout);

		Scene instructorHomeScene = new Scene(instructorHomeRoot, windowX, windowY);
		return instructorHomeScene;
	}
	
	
	public Scene studentHomePage() {
		
		studentLogout = new Button();
		studentLogout.setText("Student");
		
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

			createUsername.clear();
			createPassword.clear();
			confirmPassword.clear();

			helloApp.createUser();

		}

		// Login Handling 
		else if (event.getSource() == login) {
			String username = enterUsername.getText();
            String password = enterPassword.getText();

            enterUsername.clear();
            enterPassword.clear();

			helloApp.HandleLoginAttempt(username, password);
		}

		// Register Handling (Submitting Invitation Code)
		else if (event.getSource() == register) {
            String code = enterCode.getText();
			enterCode.clear();

            helloApp.handleInviteCodeAttempt(); 
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
			switchScene(adminHomePage());
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
			*/
			
			// Just testing to get alert to work 

			//helloApp.createInviteCode(roles);
			codeAlert.setTitle("Invitation Code!");
			codeAlert.setContentText("Code");
			
			codeAlert.showAndWait();

		}
		
		
		else if (event.getSource() == resetUserPassword) {
			// check username exists?
			String userID = enterUserID.getText();
			helloApp.adminResetPassword(userID);
		}

		else if (event.getSource() == setPass) {
			// Check if passwords match
			String newPassword = newPass.getText();
			resetUserPassword(newPassword);
		}

		else if (event.getSource() == deleteUser) {
			String userToDelete = enterUserID.getText();

			// I'll fix alert
			sure.setTitle("Are you sure?")
			sure.setContentText("This will be permanent. Are you sure you want to delete user " + userToDelete + "?");
			sure.showAndWait();
			// Are you sure alert
			// If yes, delete
			// If no, 
		}
		else if (event.getSource() == listUsers) {
			// list 
		}
		else if (event.getSource() == addRoleToUser) {
			String userID = enterUserID.getText();
			// add
		}
		else if (event.getSource() == removeRoleFromUser) {
			String userID = enterUserID.getText();
			// remove
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