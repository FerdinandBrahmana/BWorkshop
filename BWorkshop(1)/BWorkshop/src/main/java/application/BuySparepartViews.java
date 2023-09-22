package application;

import javafx.event.EventHandler;

import java.sql.ResultSet;
import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.SparePartCartModel;
import model.SparePartModel;
import model.TransactionDetailModel;
import model.TransactionHeaderModel;

public class BuySparepartViews {
    private GridPane sparepartGridPane,cartListGridPane;
    private VBox pane;
    private HBox sparepartForm,cartForm;
    private TableView<SparePartModel> sparepartList;
    private TableView<SparePartCartModel> cartList;
    private TableColumn<SparePartModel,String> sparepartNameColumn;
    private TableColumn<SparePartModel,String> sparepartBrandColumn;
    private TableColumn<SparePartModel,Integer> sparepartPriceColumn;
    private TableColumn<SparePartModel,Integer> sparepartStockColumn;
    private TableColumn<SparePartCartModel,String> cartSparepartNameColumn;
    private TableColumn<SparePartCartModel,String> cartBrandColumn;
    private TableColumn<SparePartCartModel,Integer> cartQuantityColumn;
    private TableColumn<SparePartCartModel,Integer> cartPriceColumn;
    private TableColumn<SparePartCartModel,Integer> cartTotalColumn;
    private Text buySparepartLabel, quantityLabel, cartListLabel;
    private Spinner<Integer> quantitySpinner;
    private Button addToCartButton, checkoutButton, clearCartButton;

    SparePartModel selectedSparepart = null;

    public BuySparepartViews(){

        // #region sparepart List
        sparepartList = new TableView<SparePartModel>();
        sparepartNameColumn = new TableColumn<SparePartModel,String>("Name");
        sparepartBrandColumn = new TableColumn<SparePartModel,String>("Brand");
        sparepartPriceColumn = new TableColumn<SparePartModel,Integer>("Price");
        sparepartStockColumn = new TableColumn<SparePartModel,Integer>("Stock");
        sparepartNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePartModel, String> p) {
                return new SimpleStringProperty(p.getValue().sparePartName);
            }
        });
        sparepartBrandColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePartModel, String> p) {
                return new SimpleStringProperty(p.getValue().brand);
            }
        });
        sparepartPriceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().price)).asObject();
            }
        });
        sparepartStockColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().stock)).asObject();
            }
        });
        sparepartList.getColumns().addAll(sparepartNameColumn,sparepartBrandColumn,sparepartPriceColumn,sparepartStockColumn);

        cartList = new TableView<SparePartCartModel>();
        cartSparepartNameColumn = new TableColumn<SparePartCartModel,String>("Sparepart Name");
        cartBrandColumn = new TableColumn<SparePartCartModel,String>("Brand");
        cartPriceColumn = new TableColumn<SparePartCartModel,Integer>("Price");
        cartQuantityColumn = new TableColumn<SparePartCartModel,Integer>("Quantity");
        cartTotalColumn = new TableColumn<SparePartCartModel,Integer>("Total");
        cartSparepartNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartCartModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePartCartModel, String> p) {
                return new SimpleStringProperty(p.getValue().sparePartID.sparePartName);
            }
        });
        cartBrandColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartCartModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePartCartModel, String> p) {
                return new SimpleStringProperty(p.getValue().sparePartID.brand);
            }
        });
        cartPriceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartCartModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartCartModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().sparePartID.price)).asObject();
            }
        });
        cartQuantityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartCartModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartCartModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().quantity)).asObject();
            }
        });
        cartTotalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartCartModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SparePartCartModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().calculateTotal())).asObject();
            }
        });
        cartList.getColumns().addAll(cartSparepartNameColumn,cartBrandColumn,cartPriceColumn,cartQuantityColumn,cartTotalColumn);

        buySparepartLabel = new Text("Buy Sparepart");
        quantityLabel = new Text("Quantity");
        cartListLabel = new Text("Cart List");

        quantitySpinner = new Spinner<Integer>();

        addToCartButton = new Button("Add to Cart");
        checkoutButton = new Button("Checkout");
        clearCartButton = new Button("Clear cart");

        pane = new VBox();
        sparepartForm = new HBox();
        cartForm = new HBox();
        sparepartGridPane = new GridPane();
        cartListGridPane = new GridPane();

        
        sparepartGridPane.add(buySparepartLabel, 0, 0);
        sparepartGridPane.add(quantityLabel, 0, 1);
        sparepartGridPane.add(quantitySpinner, 1, 1);
        sparepartGridPane.add(addToCartButton, 0, 2);
        sparepartForm.getChildren().addAll(sparepartList,sparepartGridPane);
        
        cartListGridPane.add(cartListLabel,0,0);
        cartListGridPane.add(checkoutButton,0,1);
        cartListGridPane.add(clearCartButton,1,1);
        cartForm.getChildren().addAll(cartList,cartListGridPane);

        pane.getChildren().addAll(sparepartForm,cartForm);

        sparepartList.addEventHandler(MouseEvent.MOUSE_CLICKED, sparepartListEH);
        addToCartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, addToCartEH);
        checkoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, checkoutEH);
        clearCartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, clearCartEH);
        
        // styles
        App.setWidth(900.0);
        cartList.setPrefWidth(600);
        sparepartList.setPrefWidth(600);
        cartListGridPane.setHgap(20);
        cartListGridPane.setVgap(20);
        cartListGridPane.setPadding(new Insets(0, 0, 0, 10));
        sparepartGridPane.setVgap(20);
        sparepartGridPane.setHgap(20);
        sparepartGridPane.setPadding(new Insets(0, 0, 0, 10));
        sparepartGridPane.setAlignment(Pos.CENTER);

        try{updateView();}catch(Exception err){System.out.println(err);}
        
    }  
    
    public Parent getWidget(){
        return pane;
    }

    public void updateView() throws Exception {
        final ObservableList<SparePartModel> sparepartItems = FXCollections.observableArrayList();
        final ObservableList<SparePartCartModel> sparepartCartItems = FXCollections.observableArrayList();

        ResultSet rs;
        rs = new SparePartModel().search("");
        while(rs.next()) sparepartItems.add(new SparePartModel(rs.getString("SparePartID")));
        rs = new SparePartCartModel().search("UserID = " + App.getUser().userID);
        while(rs.next()) sparepartCartItems.add(new SparePartCartModel(rs.getString("SparePartID"),rs.getInt("UserID")));

        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100));
        quantitySpinner.setDisable(selectedSparepart == null);
        addToCartButton.setDisable(selectedSparepart == null);

        cartList.setItems(sparepartCartItems);
        sparepartList.setItems(sparepartItems);
    }
    EventHandler<MouseEvent> sparepartListEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            selectedSparepart =  sparepartList.getSelectionModel().getSelectedItem();
            System.out.println(selectedSparepart.sparePartID);
            quantitySpinner.setDisable(selectedSparepart == null);
            addToCartButton.setDisable(selectedSparepart == null);
        } 
     }; 
     EventHandler<MouseEvent> addToCartEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            if(selectedSparepart.stock < quantitySpinner.getValue()){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Quantity cannot be more than stock!");
                alert.show();
            }else{
                SparePartCartModel cart = new SparePartCartModel();
                try{
                    System.out.println("add to chart "+selectedSparepart.sparePartID);

                    cart.create("(\""+selectedSparepart.sparePartID+"\","+App.getUser().userID+","+quantitySpinner.getValue()+")");
                    selectedSparepart = null;
                    updateView();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Sparepart added to your cart!");
                    alert.show();
                }catch(Exception sqlerr){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Something went!");
                    alert.show();
                }
            }
        } 
     };

    EventHandler<MouseEvent> clearCartEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            if(cartList.getItems().size() == 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Cart is already empty!");
                alert.show();
            }else{
                try{
                    new SparePartCartModel().unlink("UserID = "+ App.getUser().userID);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Cart is cleared!");
                    alert.show();
                    updateView();
                }catch(Exception sqlerr){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Something went wrong!");
                    alert.show();
                }
            }
        }
    };
    EventHandler<MouseEvent> checkoutEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            if(cartList.getItems().size() == 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Cart is empty!");
                alert.show();
            }else{
                TransactionHeaderModel transaction = new TransactionHeaderModel();

                try{
                    int transactionID = transaction.getNewID();
                    System.out.println(transactionID);
                    transaction.create("("+transactionID+","+App.getUser().userID+",\""+LocalDate.now().toString()+"\")");
                    for(SparePartCartModel cart : cartList.getItems()){
                        new TransactionDetailModel().create("("+transactionID+",\""+cart.sparePartID.sparePartID+"\","+cart.quantity+")");
                        new SparePartModel().write("Stock = "+ (cart.sparePartID.stock - cart.quantity), "SparePartID = \""+cart.sparePartID.sparePartID+"\"");
                    }
                    new SparePartCartModel().unlink("UserID = "+ App.getUser().userID);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Checkout success!");
                    alert.show();
                    updateView();
                }catch(Exception sqlerr){
                    System.out.println(sqlerr);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Something went wrong!");
                    alert.show();
                }
            }
        }
    };

}