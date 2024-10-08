//package com.example.studenthelpapp;

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
// Notes: Need to fix role page (shouldn't show all roles unless they have them)
// Notes: switchScene useless?
// Notes: Didn't use original button handling
// Add labels (stuff I can do myself though)
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
    
    
    // UI components for instructor home page
    private Button instructorLogout; // If all logouts do same thing, can probably just use one logout button
    
    // UI components for student home page 
    private Button studentLogout;
    
    // Show Alert
    private Button okAlert;
    private Label Alert;
    
	private String inviteCode; //Only used to store the invite code for a few functions. Be careful how you use this.
	
    
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
	            

	        VBox root = new VBox(20);
			
			root.getChildren().addAll(createUsername, createPassword, confirmPassword, createAccount);
			
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
		
			Label label_Username = new Label("Enter the username here: ");
			setupLabelUI(label_Username, "Arial", 24, windowX, 
					Pos.CENTER, windowX/2, 30);
		
			enterUsername = new TextField(); 
			setupTextUI(enterUsername, "Arial", 12, 150, Pos.BASELINE_LEFT, (windowX/8 + 20), 50, "Username: ");
			
			enterPassword = new TextField(); 
			setupTextUI(enterPassword, "Arial", 12, 150, Pos.BASELINE_LEFT, (windowX/2) + 20, 50, "Password: ");

			enterCode = new TextField(); 
			setupTextUI(enterCode, "Arial", 12, 200, Pos.BASELINE_LEFT, (windowX/4) + 20, windowY/2, "Invitation code: ");
			
	        
	        login = new Button();
	        setupButtonUI(login, "Login", 100, Pos.CENTER, (windowX/2) - 50, 100);
	        
			register = new Button();
			setupButtonUI(register, "Register", 100, Pos.CENTER, (windowX/2) - 50, (windowY/2) + 40);
			
	        login.setOnAction(this);
	        
			register.setOnAction(this);
			
			
	        Pane root = new Pane();

	        root.getChildren().addAll(login, register, enterUsername, enterPassword, enterCode);
			
			Scene loginScene = new Scene(root, windowX, windowY);
			return loginScene;

	}
	
	
	/**
	 * Finish set up Scene
	 */
	public Scene finishSetUp() {
		// Buttons and Textboxes
		
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
	public Scene adminHomePage() { // ADD Textboxes where necessary
		
		adminLogout = new Button();
 		adminLogout.setText("Logout");
		
		invite = new Button();
		invite.setText("Invite User");
		
		resetUser = new Button();
		resetUser.setText("Reset User Password");
		
		deleteUser = new Button();
		deleteUser.setText("Delete User");
		
		listUsers = new Button();
		listUsers.setText("List Users");
		
		addRoleToUser = new Button();
		addRoleToUser.setText("Add Role to User");
		
		removeRoleFromUser = new Button();
		removeRoleFromUser.setText("Remove Role from User");
		
		
		
		VBox adminHomeRoot = new VBox(20);
		
		adminHomeRoot.getChildren().addAll(adminLogout);

		Scene adminHomeScene = new Scene(adminHomeRoot, windowX, windowY);
		return adminHomeScene;
	}
	
	
	public Scene instructorHomePage() {
		
		instructorLogout = new Button();
		instructorLogout.setText("Logout");
		
		VBox instructorHomeRoot = new VBox(20);
		
		instructorHomeRoot.getChildren().addAll(instructorLogout);

		Scene instructorHomeScene = new Scene(instructorHomeRoot, windowX, windowY);
		return instructorHomeScene;
	}
	
	
	public Scene studentHomePage() {
		
		studentLogout = new Button();
		studentLogout.setText("Logout");
		
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
        
			// check that username is valid
			
			// check that username does not already exist 
			
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
            // Check if username exists
            // Check if password exists and matches username (as in, 
            // handleLogin?
            
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


		else if (event.getSource() == invite) {
			// invite user
		}

		else if (event.getSource() == resetUser) {
			// .getText
			// reset 
		}

		else if (event.getSource() == deleteUser) {
			//.getText
			// delete
		}

		else if (event.getSource() == listUsers) {
			// list 
		}

		else if (event.getSource() == addRoleToUser) {
			// .getText
			// add
		}

		else if (event.getSource() == removeRoleFromUser) {
			// .getText
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
