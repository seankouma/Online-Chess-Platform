package com.tco.misc;

import java.sql.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SQLDatabase{
    public String DB_URL;

    // shared user with read-only access
    public String DB_USER;
    public String DB_PASSWORD;
    public Connection con = null;
    public SQLDatabase(){
        //String useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");
        this.DB_URL = "jdbc:mysql://faure.cs.colostate.edu/cs414_team16";
        this.DB_USER = "antenehz";
        this.DB_PASSWORD = "831476612";
        try{
            // connect to the database and query
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection Successful. " + con);

        }catch (SQLException sqle) {
            System.out.println("Could not insert tuple. " + sqle);
        }
    }




    public void RegisterNewUser(String FirstName, String LastName, String email, String username, String password){
        try{

            PreparedStatement pStmt = con.prepareStatement(
                    "insert into Registration(FirstName, LastName, email, username, password) values (?,?,?,?,?)");
            pStmt.setString(1, FirstName);
            pStmt.setString(2, LastName);
            pStmt.setString(3, email);
            pStmt.setString(4, username);
            pStmt.setString(5, password);
            pStmt.executeUpdate();
            con.close();
            System.out.println("regitered new user");

        }catch (SQLException sqle) {
            System.out.println("Could not insert tuple. " + sqle);
        }catch(NumberFormatException ex){
            System.out.println("Number issue. " + ex);
        }
    }

    public String ValidateLogin(String email, String password)
    {
        try
        {
            ResultSet rs;
            PreparedStatement pStmt = con.prepareStatement(
                    "SELECT email, username, password FROM Registration");
            rs = pStmt.executeQuery();
            System.out.println("\n\n\n*****");

            while (rs.next()) {
                String mail = rs.getString("email");
                String pass = rs.getString("password");
                String user = rs.getString("username");


                if (mail.equals(email) && pass.equals(password)) {
                    this.DB_USER = user;
                    this.DB_PASSWORD = pass;

                    return "true";
                }
            }
            System.out.println("No user found");


        }catch (SQLException sqle) {
            System.out.println("Could not insert tuple. " + sqle);
        }catch(NumberFormatException ex){
            System.out.println("Number issue. " + ex);
        }

        System.out.println("Login Failed!");
        return "false";
    }

    public ArrayList<JSONObject> getAllUsers(){
        try{
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            ArrayList<JSONObject> users = new ArrayList<JSONObject>();
            String sql = "select * from Registration";
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                JSONObject record = new JSONObject();
                record.put("FirstName", rs.getString("FirstName"));
                record.put("LastName", rs.getString("LastName"));
                record.put("email", rs.getString("email"));
                record.put("username", rs.getString("username"));
                users.add(record);
            }
            return users;
        }catch (SQLException sqle) {
            System.out.println("Could not insert tuple. " + sqle);
        }catch(NumberFormatException ex){
            System.out.println("Number issue. " + ex);
        }
        return null;
    }
}




