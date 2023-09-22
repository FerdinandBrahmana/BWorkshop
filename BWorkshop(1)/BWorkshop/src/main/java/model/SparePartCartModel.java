package model;
import java.sql.ResultSet;
public class SparePartCartModel extends Model {
    public String tableName = "sparepartcart";

    public SparePartModel sparePartID;
    public UserModel userID;
    public int quantity;

    public SparePartCartModel(){};
    public SparePartCartModel(String sid,int id){
        ResultSet res;
        try{
            res = this.search("UserID = "+id+" and SparePartID = \""+sid + "\"");
            res.next();
            this.sparePartID = new SparePartModel(res.getString("SparePartID"));
            this.userID = new UserModel(res.getInt("UserID"));
            this.quantity = res.getInt("Quantity");
        }catch(Exception err){
            System.out.println(err);
        }
    }

    public int calculateTotal(){
        return sparePartID.price * quantity;
    }
}
