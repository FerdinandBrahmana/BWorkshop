package application;

import java.sql.ResultSet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.TransactionDetailModel;

public class TransactionViews  extends View  {
    private TableView<TransactionDetailModel> transactionList = new TableView<TransactionDetailModel>();
    private TableColumn<TransactionDetailModel, Integer> transactionIDColumn = new TableColumn<TransactionDetailModel,Integer>("ID");
    private TableColumn<TransactionDetailModel, String> sparepartColumn = new TableColumn<TransactionDetailModel,String>("Sparepart");
    private TableColumn<TransactionDetailModel, String> brandColumn = new TableColumn<TransactionDetailModel,String>("Brand");
    private TableColumn<TransactionDetailModel, String> usernameColumn = new TableColumn<TransactionDetailModel,String>("Username");
    private TableColumn<TransactionDetailModel, Integer> quantityColumn = new TableColumn<TransactionDetailModel,Integer>("QUantity");
    private TableColumn<TransactionDetailModel, Integer> sparepartPriceColumn = new TableColumn<TransactionDetailModel,Integer>("Sparepart Price");
    public TransactionViews(){
        transactionIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransactionDetailModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<TransactionDetailModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().transactionID.transactionID)).asObject();
            }
        });
        sparepartColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransactionDetailModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TransactionDetailModel, String> p) {
                return new SimpleStringProperty(p.getValue().sparePartID.sparePartName);
            }
        });
        brandColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransactionDetailModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TransactionDetailModel, String> p) {
                return new SimpleStringProperty(p.getValue().sparePartID.brand);
            }
        });
        usernameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransactionDetailModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TransactionDetailModel, String> p) {
                return new SimpleStringProperty(p.getValue().transactionID.UserID.username);
            }
        });
        quantityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransactionDetailModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<TransactionDetailModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().quantity)).asObject();
            }
        });
        sparepartPriceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransactionDetailModel, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<TransactionDetailModel, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().sparePartID.price)).asObject();
            }
        });

        if(App.getUser().role.equals("admin")){
            transactionList.getColumns().addAll(transactionIDColumn,usernameColumn,sparepartColumn,quantityColumn,sparepartPriceColumn);
        }else{
            transactionList.getColumns().addAll(transactionIDColumn,sparepartColumn,brandColumn,quantityColumn,sparepartPriceColumn);
        }
        try{updateView();}catch(Exception e){};
    }
    public Parent getWidget(){return transactionList;}
    private void updateView() throws Exception {
        final ObservableList<TransactionDetailModel> items = FXCollections.observableArrayList();
        ResultSet rs;

        rs = App.getUser().role.equals("admin")? new TransactionDetailModel().search(""):new TransactionDetailModel().query("SELECT d.* FROM transactiondetail AS d INNER JOIN transactionheader AS h ON h.TransactionID = d.TransactionID WHERE h.UserID = " + App.getUser().userID);
        while(rs.next()) items.add(new TransactionDetailModel(rs.getString("SparePartID"),rs.getInt("TransactionID")));
        transactionList.setItems(items);
    }
}
