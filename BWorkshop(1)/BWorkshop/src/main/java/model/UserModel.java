package model;

import java.sql.ResultSet;

public class UserModel extends Model {
    public String tableName = "user";

    public int userID;
    public String username;
    public String email;
    public String password;
    public String role;

    public UserModel(){}
    public UserModel(int id){
        ResultSet res;
        try{
            res = this.search("UserID = "+id);
            res.next();
            this.userID = res.getInt("UserID");
            this.username = res.getString("Username");
            this.email = res.getString("Email");
            this.password = res.getString("Password");
            this.role = res.getString("Role");
        }catch(Exception err){
            System.out.println(err);
        }
    }
    public int getNewID() throws Exception{
        ResultSet rs = this.search("");
        int id = 0;
        while(rs.next()){
            int cid = rs.getInt("UserID");
            id = cid > id?cid:id;
        }
        return id + 1;
    }
    
}
