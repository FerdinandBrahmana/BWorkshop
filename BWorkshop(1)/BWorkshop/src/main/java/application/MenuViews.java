package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import model.UserModel;

public class MenuViews {
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem buySparepartMenuItem = new MenuItem("Buy Sparepart");
    private MenuItem manageSparepartMenuItem = new MenuItem("Manage Sparepart");
    private MenuItem allTransactionMenuItem = new MenuItem("View Transactions");
    private MenuItem transactionMenuItem = new MenuItem("View Transactions");
    private MenuItem userMenuItem = new MenuItem("Manage Users");
    private MenuItem logoutMenuItem = new MenuItem("Logout");

    public MenuViews(){
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        UserModel user = App.getUser();

        // add menuitem to menu depend on user's role
        if(user.role.equals("admin")){
            menu.getItems().addAll(manageSparepartMenuItem,userMenuItem,allTransactionMenuItem,logoutMenuItem);
        }else{
            menu.getItems().addAll(buySparepartMenuItem,transactionMenuItem,logoutMenuItem);
        }
        
        manageSparepartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                   App.setRoot(new VBox(new MenuViews().getWidget(),new SparePartViews().getWidget()));
                }catch(Exception err){
                    System.out.println(err);
                }
            }
        });
        userMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                   App.setRoot(new VBox(new MenuViews().getWidget(),new UserViews().getWidget()));
                }catch(Exception err){
                    System.out.println(err);
                }
            }
        }); 
        buySparepartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                   App.setRoot(new VBox(new MenuViews().getWidget(),new BuySparepartViews().getWidget()));
                }catch(Exception err){
                    System.out.println(err);
                }
            }
        });
        transactionMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                   App.setRoot(new VBox(new MenuViews().getWidget(),new TransactionViews().getWidget()));
                }catch(Exception err){
                    System.out.println(err);
                }
            }
        });
        allTransactionMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                   App.setRoot(new VBox(new MenuViews().getWidget(),new TransactionViews().getWidget()));
                }catch(Exception err){
                    System.out.println(err);
                }
            }
        });
        logoutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                    App.setUser(null);
                    App.setRoot(new LoginViews().getWidget());
                }catch(Exception err){
                    System.out.println(err);
                }
            }
        });
        menuBar.getMenus().add(menu);
    }
    public MenuBar getWidget(){
        return menuBar;
    }
}

