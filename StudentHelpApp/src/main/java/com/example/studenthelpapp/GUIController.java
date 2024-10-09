package com.example.studenthelpapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.studenthelpapp.DatabaseController.RoleCodes;

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
import javafx.scene.control.ButtonType;


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
		//switchScene(login_page()); // Placeholder 
		switchScene(adminHomePage()); //TODO FOR TEST. MUST REMOVE
    }
    
    /**
     * Create account 
     */
    public Scene create_account(String invite_code) {		
			//For when a user has submitted a valid invite code,
    		//We allow them to enter a username and password (has to match twice)
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
		//Gives the user the option to login with a username and password, or enter an invite code they were provided
		//which will take them to a page that allows them to setup an account
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
		//For when a user logs in and needs to finish registration. Allows them to enter
		//email, first name, middle name, last name, preferred name
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
		//Scene for when someone with multiple roles logs in. Provides them a button for each role they have
		//And will choose which role they are logged in as for this session, and take them to the associated homepage.
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
		
		//Admin homepage, which allows them to
		//Logout, Generate an invite code, Reset a user's password, Delete a user, List users,
		//Add a role to a user, or remove a role from a user.
		
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
		//For when the admin wants to generate an invite code,
		//and has to select the associated roles
		
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


	
	
	//Small class for loading user data into the table
	public static class User {
		private String username;
		private String firstName;
		private String codes;
		
		public User(String username, String firstName, String codes) {
			this.username = username;
			this.firstName = firstName;
			this.codes = codes;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getCodes() {
			return codes;
			
		}
		
	}
	
	/**
	 * Finish list 
	 */
	public Scene listUsers(/*List<User> users*/) { 
		//Scene for when the admin wants to list users.
		//Will list all users in a table
		//Only lists users username, First and Last name, and role ids
		
		
		// users from Users class 
		TableView<User> userTable = new TableView<>();
		List<User> users = new ArrayList<>();
		String userString = helloApp.listUsers();
		String[] rows = userString.split("\n"); //Split the string on \n, so each user is a row
		for(String row: rows) {
			if(row.trim().isEmpty()) //For last substring, potentially empty
			{
				continue;
			}
			String[] parts = row.split(",",3);//Split on the first 2 commas, but not the ones in the square brackets
			String username = parts[0].trim();
			String name = parts[1].trim();
			String codes = parts[2].trim();
			users.add(new User(username, name, codes)); //Add to a list
		}
		
		
		userTable.getItems().addAll(users);
		//Setup table columns, which will fetch using the get functions.
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
		//Scene for when the admin wants to trigger a password reset for a given user.
		//Will provide the admin with a temporary password, which they will give to the user. 
		//The user has 7 days to login after the password is generated, and they will be forced to change it.  
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter Username for user to password reset: ");
		
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
		//For when a user is being forced to change their password, after an admin initiated a password reset on them
		newPass = new TextField();
		newPass.setPromptText("Enter your new password: ");
		
		confirmNewPass = new TextField(); 
		confirmNewPass.setPromptText("Confirm your password: ");
		
		setPass = new Button();
		setPass.setText("Change Password");
		setPass.setOnAction(this);
		
		//Removed back button from this, as it is a forced password reset.
		//If we want a user initiated password reset, we can make a new scene.
		
		VBox changePassRoot = new VBox(20);
		
		changePassRoot.getChildren().addAll(newPass, confirmNewPass, setPass);
		
		Scene changePassScene = new Scene(changePassRoot, windowX, windowY);
		return changePassScene;
	}

	public Scene deleteUser() {
		//Scene for when the admin wants to delete a user, (has to provide their username)
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the Username of the user to delete");
		
		deleteUserButton = new Button();
		deleteUserButton.setText("Delete");
		deleteUserButton.setOnAction(this);
		
		backButton = new Button();
		backButton.setText("Back");	
		backButton.setOnAction(this);

		VBox deleteUserRoot = new VBox(20);

		deleteUserRoot.getChildren().addAll(enterUserID, deleteUserButton, backButton);

		Scene deleteUserScene = new Scene(deleteUserRoot, windowX, windowY);
		return deleteUserScene;

	}

	public Scene addRoleToUser() {
		//Scene for when the admin wants to add a role to a user, (has to provide their username)
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the Username");

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
		//Scene for when the admin wants to remove a role from a user, (has to provide their username)
		enterUserID = new TextField();
		enterUserID.setPromptText("Enter the Username");

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
		//The homepage for instructors (or multi-roles that chose instructors)
				//Only has logout as of now
		instructorLogout = new Button();
		//instructorLogout.setText("Instructor");
		instructorLogout.setOnAction(this);
		
		

		instructorLogout.setText("Logout");

		
		VBox instructorHomeRoot = new VBox(20);
		
		instructorHomeRoot.getChildren().addAll(instructorLogout);

		Scene instructorHomeScene = new Scene(instructorHomeRoot, windowX, windowY);
		return instructorHomeScene;
	}
	
	
	public Scene studentHomePage() {
		//The homepage for students (or multi-roles that chose students)
		//Only has logout as of now
		studentLogout = new Button();
		//studentLogout.setText("Student");
		studentLogout.setOnAction(this);
		studentLogout.setText("Logout");

		
		VBox studentHomeRoot = new VBox(20);
		
		studentHomeRoot.getChildren().addAll(studentLogout);

		Scene studentHomeScene = new Scene(studentHomeRoot, windowX, windowY);
		return studentHomeScene;
	}
	
	
	/**
	 * Switch Scene
	 * @param newScene
	 */
	public void switchScene(Scene newScene) {
		//Switches displayed scene to the provided scene
	    mainStage.setScene(newScene);
	    mainStage.show();
	}
	
	/**
	 * Set title
	 * @param title
	 */
	public void setTitle(String title) {
		//Sets the window title to provided string.
		mainStage.setTitle(title);
	}
	
	
	
	
	/**
	 * Handle button
	 */
	public void handle(ActionEvent event) {
		//Function handles all button presses
		
		// Create Account Handling 
		if(event.getSource() == createAccount) {
			String username = createUsername.getText();
			String password1 = createPassword.getText();
			String password2 = confirmPassword.getText();

			
			
			// check passwords match
			if(password1.equals(password2)) {
				createUsername.clear();
				createPassword.clear();
				confirmPassword.clear();
				//Calls createUser for logic handling
				helloApp.createUser(this.inviteCode, username, password1);
				this.inviteCode = null; 
				//Removes storage of inviteCode, no longer needed in GUIController at this point
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
           
            
            //Passes typed username and password to helloApp for logic.
        	helloApp.handleLoginAttempt(username, password);

		}

		// Register Handling (Submitting Invitation Code)
		else if (event.getSource() == register) {
           
			String code = enterCode.getText();
            enterCode.clear();
            // Calls handleInviteCodeAttempt, which will check the validity and manage next steps
            
            helloApp.handleInviteCodeAttempt(code);
		}

		// Finish Set Up Handling 
		else if (event.getSource() == finish) {
			//For when the user has entered their email on the finish registration scene
            String email = enterEmail.getText();
            String firstName = enterFirstName.getText();
            String middleName = enterMiddleName.getText();
            String lastName = enterLastName.getText();
            String preferredName = enterPreferredName.getText();
            
           
            //Call email_check, and display various alerts for various access codes
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
            
            //Call name_check
            Name.NameResult name_check;
            Name.NameResult.Position[] positionsToCheck;
            if(preferredName.trim().isEmpty()) {
            	//Checks if there is a preferred name entered, to choose which function to call
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
            	//StringBuilder to build a potentially long string listing all the requirements not met.
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
            
            //Call the userSetup function
            helloApp.finishUserSetup(email, firstName, middleName, lastName, preferredName);
		}

		
		else if (event.getSource() == admin) {
			//For when the user with the admin role presses admin, to login as a admin this session
			switchScene(adminHomePage());
		}
		
		else if (event.getSource() == instructor) {
			//For when the user with the instructor role presses instructor, to login as a instructor this session
			switchScene(instructorHomePage());
		}
		
		else if (event.getSource() == student) {
			//For when the user with the student role presses student, to login as a student this session
			switchScene(studentHomePage());
		}
		
		else if (event.getSource() == invite) {
			//For when the admin pressed the Invite button on the admin homepage
			switchScene(inviteUser());
		}
		
		else if (event.getSource() == confirmRoles) {
			//For when the admin has chosen what roles for the invite code
			boolean adminSelected = selAdmin.isSelected();
			boolean instructorSelected = selInstructor.isSelected();
			boolean studentSelected = selStudent.isSelected();
			
			//Ensure they have selected a role
			if((!adminSelected) && (!instructorSelected) && (!studentSelected)) {
				showAlert("Must select at least 1 role");
				return;
			}
			
			codeAlert = new Alert(AlertType.INFORMATION);
			List<Integer> roles = new ArrayList<>();
			//Add the selected roles to a list
			if(adminSelected) {
				roles.add(RoleCodes.ADMIN.get());
			}
			if(instructorSelected) {
				roles.add(RoleCodes.INSTRUCTOR.get());
			}
			if(studentSelected) {
				roles.add(RoleCodes.STUDENT.get());
			}
			int[] roleArr = roles.stream().mapToInt(i->i).toArray(); 
			//Create the invite code
			String code = helloApp.createInviteCode(roleArr);
			//Display the invite code
			codeAlert.setTitle("Invitation Code!");
			codeAlert.setContentText("The invitation code is: "+code);
			
			codeAlert.showAndWait();
			
			switchScene(adminHomePage());
		}
		
		
		else if (event.getSource() == resetPass) {
			//Get the userId for the username the admin entered
			String username = enterUserID.getText();
			Integer id = helloApp.getUsernameId(username);
			if(id != null) {
				//if the id is valid, reset that user's password
				enterUserID.clear();
				helloApp.adminResetPassword(id);
				switchScene(adminHomePage());
			} else {
				showAlert("User with that Username not found");
			}
		}

		else if (event.getSource() == setPass) {
			//For when the user is forced to reset their password by an admin doing resetPassword on them
			
			// Check if passwords match
			String newPassword = newPass.getText();
			String copyPassword = confirmNewPass.getText();
			if(newPassword.equals(copyPassword))
			{
				//Reset their password to the new password
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
			//Get the userID of that username and check if its valid
			String userToDelete = enterUserID.getText();
			Integer id = helloApp.getUsernameId(userToDelete);
			if(id != null) {
				//Get confirmation that they want to delete this user
				sure = new Alert(AlertType.CONFIRMATION);
				sure.setTitle("Are you sure?");
				sure.setContentText("This will be permanent. Are you sure you want to delete user " + userToDelete + "?");
				ButtonType yesButton = new ButtonType("Yes");
				ButtonType noButton = new ButtonType("No");
				sure.getButtonTypes().setAll(yesButton, noButton);
				Optional<ButtonType> result = sure.showAndWait();
				if(result.isPresent() && (result.get() == yesButton)) {
					//If they press yes, delete the user
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
			//When an admin presses the listUsers button on the admin homepage
			switchScene(listUsers()); //Switch to the listUsers scene
		}
		else if (event.getSource() == addRoleToUser) {
			//When an admin presses the addRoleToUser button on the admin homepage
			//Switch to the addRole scene
			switchScene(addRoleToUser());
		} else if (event.getSource() == addRole) {
			//Gets the provided username and checkbox selections
			String username = enterUserID.getText();
			boolean adminSelected = addAdmin.isSelected();
			boolean instructorSelected = addInstructor.isSelected();
			boolean studentSelected = addStudent.isSelected();
			//If the username is valid, add the role codes to a list
			Integer id = helloApp.getUsernameId(username);
			if(id != null) {
				enterUserID.clear();
				List<Integer> roles = new ArrayList<>();
				if(adminSelected) {
					roles.add(RoleCodes.ADMIN.get());
				}
				if(instructorSelected) {
					roles.add(RoleCodes.INSTRUCTOR.get());
				}
				if(studentSelected) {
					roles.add(RoleCodes.STUDENT.get());
				}
			
				int[] roleArr = roles.stream().mapToInt(i->i).toArray(); 
				//Add those roles from the id for that username
				helloApp.addRoles(id, roleArr);
				//Display a success
				showAlert("Roles added");
				//Go to the homepage
				switchScene(adminHomePage());
			} else {
				showAlert("No user found with that username");
			}
			
		}
		
		else if (event.getSource() == removeRoleFromUser) {
			//When an admin presses the removeRoleFromUser button on the admin homepage
			//Switch to the removeRole scene
			switchScene(removeRoleFromUser());
		}
		else if (event.getSource() == removeRole) {
			//Gets the provided username and checkbox selections
			String username = enterUserID.getText();
			boolean adminSelected = removeAdmin.isSelected();
			boolean instructorSelected = removeInstructor.isSelected();
			boolean studentSelected = removeStudent.isSelected();
			//If the username is valid, add the role codes to a list
			Integer id = helloApp.getUsernameId(username);
			if(id != null) {
				enterUserID.clear();
				List<Integer> roles = new ArrayList<>();
				if(adminSelected) {
					roles.add(RoleCodes.ADMIN.get());
				}
				if(instructorSelected) {
					roles.add(RoleCodes.INSTRUCTOR.get());
				}
				if(studentSelected) {
					roles.add(RoleCodes.STUDENT.get());
				}
				int[] roleArr = roles.stream().mapToInt(i->i).toArray(); 
				//Remove those roles from the id for that username
				helloApp.removeRoles(id, roleArr);
				//Display a success
				showAlert("Roles removed");
				//Go to the homepage
				switchScene(adminHomePage());
			} else {
				showAlert("No user found with that username");
			}
		}
		
		
		else if (event.getSource() == backButton) {
			//When the back button is pressed on any admin functionality page
			//Take them back to the admin homepage
			switchScene(adminHomePage());
		}
		else if (event.getSource() == adminLogout) {
			//When the logout button is pressed, log out the user
			helloApp.logoutCurrentUser();
			switchScene(login_page());
			
		}
		else if (event.getSource() == instructorLogout) {
			//When the logout button is pressed, log out the user
			helloApp.logoutCurrentUser();
			switchScene(login_page());
		}
		else if (event.getSource() == studentLogout) {
			//When the logout button is pressed, log out the user
			helloApp.logoutCurrentUser();
			switchScene(login_page());
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

