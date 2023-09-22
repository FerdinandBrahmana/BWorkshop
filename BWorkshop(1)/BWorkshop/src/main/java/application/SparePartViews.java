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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.SparePartModel;
public class SparePartViews {
    SparePartModel selectedSparePart = null;
    private TableView<SparePartModel> sparepartList;
    private TableColumn<SparePartModel,String> sparepartIDColumn;
    private TableColumn<SparePartModel,String> sparepartNameColumn;
    private TableColumn<SparePartModel,String> sparepartBrandColumn;
    private TableColumn<SparePartModel,Integer> sparepartPriceColumn;
    private TableColumn<SparePartModel,Integer> sparepartStockColumn;

    private Text namaLb = new Text("Name");
    private Text brandLb = new Text("Brand");
    private Text priceLb = new Text("Price");
    private Text stockLb = new Text("Stock");
    private Text qtyLb = new Text("Quantity");

    private TextField namaTF = new TextField();
    private TextField merekTF = new TextField();
    private Spinner<Integer> quantityField, priceField, stockField;

    private Button insertBtn = new Button("Insert");
    private Button updateBtn = new Button("Update");
    private Button deleteBtn = new Button("Delete");

    private VBox pane = new VBox();
    private GridPane formView = new GridPane();
    private GridPane controlView = new GridPane();
    private HBox controlPane = new HBox();

    public SparePartViews(){
        sparepartList = new TableView<SparePartModel>();
        sparepartIDColumn = new TableColumn<SparePartModel,String>("Name");
        sparepartNameColumn = new TableColumn<SparePartModel,String>("Name");
        sparepartBrandColumn = new TableColumn<SparePartModel,String>("Brand");
        sparepartPriceColumn = new TableColumn<SparePartModel,Integer>("Price");
        sparepartStockColumn = new TableColumn<SparePartModel,Integer>("Stock");
        sparepartIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SparePartModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SparePartModel, String> p) {
                return new SimpleStringProperty(p.getValue().sparePartID);
            }
        });
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
        sparepartList.getColumns().addAll(sparepartIDColumn,sparepartNameColumn,sparepartBrandColumn,sparepartPriceColumn,sparepartStockColumn);

        quantityField = new Spinner<Integer>(1,100,0,1);
        stockField = new Spinner<Integer>(0,100,0,1);
        priceField = new Spinner<Integer>(10000,Integer.MAX_VALUE,0,1000);

        formView.add(namaLb, 0, 0);
        formView.add(namaTF, 2, 0);
        formView.add(brandLb, 0, 1);
        formView.add(merekTF, 2, 1);
        formView.add(qtyLb, 0, 2);
        formView.add(quantityField, 2, 2);
        formView.add(priceLb, 0, 3);
        formView.add(priceField, 2, 3);
        formView.add(insertBtn, 1, 4);
        formView.setHgap(10);
        formView.setVgap(10);

        controlView.add(stockLb, 0, 0);
        controlView.add(stockField, 2, 0);
        controlView.add(updateBtn, 0, 1);
        controlView.add(deleteBtn, 1, 1);
        controlView.setHgap(15);
        controlView.setVgap(10);

        insertBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, insertEH);
        updateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, updateEH);
        deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, deleteEH);
        sparepartList.addEventHandler(MouseEvent.MOUSE_CLICKED, sparepartListEH);

        
        controlPane.getChildren().addAll(formView,controlView);
        controlPane.setPadding(new Insets(10, 10, 10, 10));
        controlPane.setSpacing(20);
        pane.getChildren().addAll(sparepartList,controlPane);
        pane.setSpacing(10);
        App.setWidth(850.0);
        try{updateView();}catch(Exception e){System.out.println(e);};
    }
    public Parent getWidget(){return pane;}
    public void updateView() throws Exception{
        final ObservableList<SparePartModel> sparepartItems = FXCollections.observableArrayList();
        ResultSet rs;
        rs = new SparePartModel().search("");
        while(rs.next()) sparepartItems.add(new SparePartModel(rs.getString("SparePartID")));
        sparepartList.setItems(sparepartItems);
        
        stockField.setDisable(selectedSparePart == null);
        updateBtn.setDisable(selectedSparePart == null);
        deleteBtn.setDisable(selectedSparePart == null);

        resetField();
    }
    EventHandler<MouseEvent> sparepartListEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            selectedSparePart =  sparepartList.getSelectionModel().getSelectedItem();
            updateBtn.setDisable(selectedSparePart == null);
            deleteBtn.setDisable(selectedSparePart == null);
            stockField.setDisable(selectedSparePart == null);
        } 
     }; 
    EventHandler<MouseEvent> deleteEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
            alertConfirm.setContentText("Are you sure want to delete!");
            if(alertConfirm.showAndWait().get() == ButtonType.OK){
                try{
                    new SparePartModel().unlink("SparePartID = \"" + selectedSparePart.sparePartID+"\"");
                    selectedSparePart = null;
                    updateView();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Sparepart has been deleted!");
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
            try{
                new SparePartModel().write("Stock = "+stockField.getValue(),"SparePartID = \"" + selectedSparePart.sparePartID+"\"");
                selectedSparePart = null;
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
    };
    EventHandler<MouseEvent> insertEH = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            boolean validate = validateField();
            if (validate) {
                try{
                    String data ="( \"" + new SparePartModel().getNewID() + "\",\"" + namaTF.getText() + "\",\"" + merekTF.getText() + "\",\"" + priceField.getValue() + "\",\"" + quantityField.getValue() + "\")";
                    new SparePartModel().create(data);
                    selectedSparePart = null;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Sparepart has been inserted!");
                    alert.show();
                    updateView();
                }catch(Exception err){
                    System.out.println(err);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Something went wrong!");
                    alert.show();
                }
            }
        }
    };

    public boolean validateField(){
        int nl = namaTF.getText().length();
        if(nl == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Sparepart Name Cannot Be Empty!");
            alert.show();
            return false;
        }else if(!(nl > 5 && nl < 20)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Name must between  5 - 20 characters!");
            alert.show();
            return false;
        }else if(merekTF.getLength() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Brand Name Cannot Be Empty!");
            alert.show();
            return false;
        }
        return true;
    }
    public void resetField(){
        namaTF.setText("");
        merekTF.setText("");
        // quantityField.setValueFactory(null);
    }
}
