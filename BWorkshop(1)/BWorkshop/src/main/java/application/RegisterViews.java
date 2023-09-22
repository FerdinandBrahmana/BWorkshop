package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.UserModel;

import java.util.regex.Pattern;

import javafx.event.EventHandler;

public class RegisterViews  extends View {
    private Text title;
    private Text usernameLabel;
    private Text emailLabel;
    private Text passwordLabel;
    private Text confirmPasswordLabel;

    private TextField usernameField, emailField;
    private PasswordField passwordField,confirmPasswordField ;

    private Button registerButton;

    private GridPane gpane;
    private VBox pane;

    public RegisterViews(){
        App.setHeight(500.0);
        App.setWidth(420.0);
        title = new Text("Register");
        usernameLabel = new Text("Username");
        emailLabel = new Text("Email");
        passwordLabel = new Text("Password");
        confirmPasswordLabel = new Text("Confirm Password");
        title.setFont(headerFont);
        usernameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        confirmPasswordLabel.setFont(labelFont);
        title.setFill(Color.WHITE);
        usernameLabel.setFill(Color.WHITE);
        emailLabel.setFill(Color.WHITE);
        passwordLabel.setFill(Color.WHITE);
        confirmPasswordLabel.setFill(Color.WHITE);

        registerButton = new Button("Register");
        registerButton.setFont(buttonFont);
        registerButton.setPrefHeight(30);
        registerButton.setPrefWidth(110);

        usernameField = new TextField(); 
        emailField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        gpane = new GridPane();
        gpane.setAlignment(Pos.CENTER);
        gpane.setHgap(10);
        gpane.setVgap(10);
        
        gpane.add(usernameLabel, 0,0);
        gpane.add(usernameField, 1, 0);
        gpane.add(emailLabel, 0, 1);
        gpane.add(emailField, 1, 1);
        gpane.add(passwordLabel,0,2);
        gpane.add(passwordField,1,2);
        gpane.add(confirmPasswordLabel,0,3);
        gpane.add(confirmPasswordField,1,3);
        gpane.setVgap(30);

        pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        pane.setSpacing(50);
        pane.getChildren().addAll(title,gpane,registerButton);
        registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, registerEH);
    }   

    public VBox getWidget(){
        return pane;
    }
    EventHandler<MouseEvent> registerEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            if(validate()){
                try{
                    UserModel user = new UserModel();
                    user.create(
                        "("+user.getNewID()+
                        ",'"+usernameField.getText()+
                        "','"+emailField.getText()+
                        "','"+passwordField.getText()+
                        "','customer')"
                    );
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Account created!");
                    alert.show();
                    App.setRoot(new LoginViews().getWidget());
                }catch(Exception err){
                    System.out.println(err);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Something went wrong!");
                    alert.show();
                }
            }
        } 
    }; 

    private boolean validate(){
        int ul = usernameField.getText().length();
        Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
        if(ul == 0 || passwordField.getText().length() == 0 || emailField.getText().length() == 0 || confirmPasswordField.getText().length() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill all fields");
            alert.show();
            return false;
        }else if(!(ul > 5 && ul < 25)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Username length must between 5 - 25 Characters long");
            alert.show();
            return false;
        }else if(!(pattern.matcher(emailField.getText()).find())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email must contains of @ character, it cannot be infront and must ends with .com");
            alert.show();
            return false;
        }else if(passwordField.getText().length() <= 5){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Password length must be at least 5 characters");
            alert.show();
            return false;
        }else if(!(passwordField.getText().equals(confirmPasswordField.getText()))){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Confirm Password must be same to the Password field");
            alert.show();
            return false;
        }
        return true;
    }

}
