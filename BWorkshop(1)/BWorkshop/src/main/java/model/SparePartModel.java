package model;
import java.sql.ResultSet;
public class SparePartModel extends Model{
    public String tableName = "sparepart";

    public String sparePartID;
    public String sparePartName;
    public String brand;
    public int price;
    public int stock;

    public SparePartModel(){}
    public SparePartModel(String id){
        ResultSet res;
        try{
            res = this.search("SparePartID = \""+id+"\"");
            res.next();
            this.sparePartID = res.getString("SparePartID");
            this.sparePartName = res.getString("SparePartName");
            this.brand = res.getString("Brand");
            this.price = res.getInt("Price");
            this.stock = res.getInt("Stock");
        }catch(Exception err){
            System.out.println(err);
        }
    }
    public String getNewID() throws Exception{
        ResultSet rs = this.search("");
        Integer id = 0;
        while(rs.next()){
            String sid = rs.getString("SparePartID");
            Integer cid = sid.contains("SP")? Integer.valueOf(sid.split("SP")[1]):0;
            id = cid > id?cid:id;
        }
        id = id + 1;
        String sid = id.toString();
        return "SP"+ (sid.length() == 3 ? id:sid.length() == 2?("0"+id):("00"+id));
    }
}
