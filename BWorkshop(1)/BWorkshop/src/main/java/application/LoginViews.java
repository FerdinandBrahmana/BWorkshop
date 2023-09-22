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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.UserModel;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;

import java.sql.ResultSet;

import javafx.event.EventHandler;

public class LoginViews extends View {
    private UserModel user = new UserModel();
    private Text emailLabel;
    private Text passwordLabel ;
    private Text title ;

    private TextField  emailField ;
    private PasswordField  passwordField ;

    private Button registerButton ;
    private Button submitButton ;

    private GridPane gpane ;
    private VBox pane;
    private HBox buttonPane;

    public LoginViews(){

        pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(80.0);
        pane.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        
        emailLabel = new Text("Email");
        passwordLabel = new Text("Password");
        title = new Text("Login");
        emailLabel.setFill(Color.WHITE);
        passwordLabel.setFill(Color.WHITE);
        title.setFill(Color.WHITE);
       
        title.setFont(headerFont);
        emailLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);


        emailField = new TextField();
        passwordField = new PasswordField();
        emailField.setPrefWidth(250);
        emailField.setPrefHeight(5);
        passwordField.setPrefWidth(250);
        passwordField.setPrefHeight(5);

        registerButton = new Button("Register");
        submitButton = new Button("Submit");
        registerButton.setFont(buttonFont);
        registerButton.setPrefHeight(30);
        registerButton.setPrefWidth(110);
        submitButton.setFont(buttonFont);
        submitButton.setPrefHeight(30);
        submitButton.setPrefWidth(110);

        // add event on click register
        registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, register);
        submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, login);

        gpane = new GridPane();
        gpane.setAlignment(Pos.CENTER);
        gpane.setHgap(20);
        gpane.setVgap(30);
        
        gpane.add(emailLabel, 0, 0);
        gpane.add(emailField, 1, 0);
        gpane.add(passwordLabel,0,1);
        gpane.add(passwordField, 1, 1);

        buttonPane = new HBox(registerButton,submitButton);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(70);

        pane.getChildren().addAll(title,gpane,buttonPane);
    }
    public VBox getWidget(){
        return pane;
    }
    EventHandler<MouseEvent> register = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            try{
               App.setRoot(new RegisterViews().getWidget());
            }catch(Exception err){
                System.out.println(err);
            }
        } 
     }; 
     EventHandler<MouseEvent> login = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            if(emailField.getText().equals("") || passwordField.getText().equals("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Field cannot be empty");
                alert.show();
            }else{
                try{
                    ResultSet res = user.search("Email = \"" + emailField.getText() + "\" and Password = \"" + passwordField.getText() + "\"");
                    
                    if(res.next() == false) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Username or password incorrect");
                        alert.show();
                    }
                    else{
                        
                        user = new UserModel(res.getInt("UserID"));
                        App.setUser(user);
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setContentText("Login Success\nWelcome "+user.username);
                        alert.show();
                        try{
                            VBox home =  new VBox();
                            try{
                                home.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("bg.png")), null, null, null, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true))));
                    
                            }catch(Exception er){System.out.println(er);}
                            MenuViews menu = new MenuViews();
                            home.getChildren().addAll(menu.getWidget());
                            App.setRoot(home);
                        }catch(Exception setRootErr){
                            System.out.println(setRootErr);
                        }
                    }
                }catch(Exception err){
                    System.out.println(err);
                }
            }
        } 
     };
}