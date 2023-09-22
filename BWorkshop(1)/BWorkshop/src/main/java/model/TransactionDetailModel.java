package model;
import java.sql.ResultSet;
public class TransactionDetailModel extends Model {
    public String tableName = "transactiondetail";

    public TransactionHeaderModel transactionID;
    public SparePartModel sparePartID;
    public int quantity;

    public TransactionDetailModel(){}
    public TransactionDetailModel(String sid,int id){
        ResultSet res;
        try{
            res = this.search("TransactionID = "+id+" and SparePartID = \""+sid+"\"");
            res.next();
            this.transactionID = new TransactionHeaderModel(res.getInt("TransactionID"));
            this.sparePartID = new SparePartModel(res.getString("SparePartID"));
            this.quantity = res.getInt("Quantity");
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
}
