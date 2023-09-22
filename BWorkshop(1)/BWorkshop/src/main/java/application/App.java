package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.UserModel;


public class App extends Application {

    private static UserModel user = null;
    private static Scene scene;

    @Override
    public void start(Stage stage) {
        scene = new Scene(new LoginViews().getWidget(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    static void setRoot(Parent route) throws Exception {
        scene.setRoot(route);
    }
    static void setUser(UserModel u){
        user = u;
    }
    static UserModel getUser(){
        return user;
    }
    static void setWidth(Double width){
        scene.getWindow().setWidth(width);
    }
    static void setHeight(Double height){
        scene.getWindow().setHeight(height);
    }

    public static void main(String[] args) {
        launch();
    }

}