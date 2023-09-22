package application;

import java.sql.ResultSet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.UserModel;

public class UserViews {
    private UserModel selectedUser = null;

    private TableView<UserModel> userList = new TableView<UserModel>();
    private TableColumn<UserModel, Integer> userIDColumn = new TableColumn<UserModel, Integer>("User ID");
    private TableColumn<UserModel, String> usernameColumn = new TableColumn<UserModel, String>("Username");
    private TableColumn<UserModel, String> emailColumn = new TableColumn<UserModel, String>("Email");
    private TableColumn<UserModel, String> passwordColumn = new TableColumn<UserModel, String>("Password");
    private TableColumn<UserModel, String> roleColumn = new TableColumn<UserModel, String>("Role");

    private Text newUsernameLabel = new Text("New Username");
    private Text newPasswordLabel = new Text("New Password");

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button updateButton = new Button("Update");
    private Button deleteButton = new Button("Delete");

    private VBox pane = new VBox();
    private GridPane formPane = new GridPane();

    public UserViews(){
        userIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<UserModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().userID)).asObject();
            }
        });
        usernameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserModel, String> p) {
                return new SimpleStringProperty(p.getValue().username);
            }
        });
        emailColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserModel, String> p) {
                return new SimpleStringProperty(p.getValue().email);
            }
        });
        passwordColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserModel, String> p) {
                return new SimpleStringProperty(p.getValue().password);
            }
        });
        roleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserModel, String> p) {
                return new SimpleStringProperty(p.getValue().role);
            }
        });
        userList.getColumns().addAll(userIDColumn,usernameColumn,emailColumn,passwordColumn,roleColumn);

        formPane.add(newUsernameLabel, 0, 0);
        formPane.add(usernameField, 2, 0);
        formPane.add(newPasswordLabel, 0, 1);
        formPane.add(passwordField, 2, 1);
        formPane.add(updateButton, 0, 2);
        formPane.add(deleteButton, 1, 2);

        formPane.setVgap(30);
        formPane.setHgap(10);
        formPane.setPadding(new Insets(20, 10, 20, 10));

        pane.getChildren().addAll(userList,formPane);

        updateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, updateEH);
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, deleteEH);
        userList.addEventHandler(MouseEvent.MOUSE_CLICKED, userListEH);

        try{updateView();}catch(Exception e){System.out.println(e);}
    }

    public Parent getWidget(){return pane;}

    private void updateView() throws Exception {
        final ObservableList<UserModel> userItems = FXCollections.observableArrayList();
        ResultSet rs;
        rs = new UserModel().search("");
        while(rs.next()) userItems.add(new UserModel(rs.getInt("UserID")));
        userList.setItems(userItems);
        
        usernameField.setDisable(selectedUser == null);
        passwordField.setDisable(selectedUser == null);
        updateButton.setDisable(selectedUser == null);
        deleteButton.setDisable(selectedUser == null);

        resetFields();
    }
    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private boolean validateField(){
        int ul = usernameField.getText().length();
        int pl = passwordField.getText().length();
        if(ul == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Username Cannot Be Empty!");
            alert.show();
            return false;
        }else if(!(ul > 5 && ul < 25)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Username must between  5 - 25 characters!");
            alert.show();
            return false;
        }else if(pl == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Password Cannot Be Empty!");
            alert.show();
            return false;
        }else if(pl <= 5){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Password must greater than 5!");
            alert.show();
            return false;
        }
        return true;
    }

    EventHandler<MouseEvent> deleteEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
            alertConfirm.setContentText("Are you sure want to delete!");
            if(alertConfirm.showAndWait().get() == ButtonType.OK){
                try{
                    new UserModel().unlink("UserID = " + selectedUser.userID);
                    selectedUser = null;
                    updateView();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("User has been deleted!");
                    alert.show();
                }catch(Exception sqlerr){
                    System.out.println(sqlerr);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Something went wrong!");
                    alert.show();
                }
            }
        }
    };
    EventHandler<MouseEvent> updateEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            if(validateField()){
                try{
                    new UserModel().write("username = \""+usernameField.getText() + "\", password = \""+passwordField.getText()+"\"","UserID = \"" + selectedUser.userID+"\"");
                    selectedUser = null;
                    updateView();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Sparepart has been updated!");
                    alert.show();
                }catch(Exception sqlerr){
                    System.out.println(sqlerr);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Something went wrong!");
                    alert.show();
                }
            }
        }
    };
    EventHandler<MouseEvent> userListEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            selectedUser =  userList.getSelectionModel().getSelectedItem();
            updateButton.setDisable(selectedUser == null);
            deleteButton.setDisable(selectedUser == null);
            usernameField.setDisable(selectedUser == null);
            passwordField.setDisable(selectedUser == null);
        } 
     };
}
