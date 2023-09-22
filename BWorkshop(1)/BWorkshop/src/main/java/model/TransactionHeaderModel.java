package model;

import java.sql.Date;
import java.sql.ResultSet;

public class TransactionHeaderModel extends Model {
    public String tableName = "transactionheader";

    public int transactionID;
    public UserModel UserID;
    public Date transactionDate;

    public TransactionHeaderModel(){}
    public TransactionHeaderModel(int id){
        ResultSet res;
        try{
            res = this.search("TransactionID = "+id);
            res.next();
            this.transactionID = res.getInt("TransactionID");
            this.UserID = new UserModel(res.getInt("UserID"));
            this.transactionDate = res.getDate("TransactionDate", null);
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
    public int getNewID() throws Exception{
        ResultSet rs = this.search("");
        int id = 0;
        while(rs.next()){
            int cid = rs.getInt("TransactionID");
            id = cid > id?cid:id;
        }
        return id + 1;
    }

}
