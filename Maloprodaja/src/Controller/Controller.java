/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Suad's Laptop
 */
public class Controller {
    protected final String      dbPath ;
    protected Statement         statement;   
    protected PreparedStatement preparedStatement;
    protected Connection        connection;
    
    public Controller() {
        dbPath = "jdbc:sqlite:" + PathFromApp()+"\\db\\maloprodaja_db.db";
        try  {            
            System.out.println(dbPath);
            Class.forName("org.sqlite.JDBC").newInstance();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException  ex){           
            System.err.println("Greska u ocitavanju SQLite driver-a...");          
            System.exit(0);
        }        
    }
    
    protected Connection getConn() throws SQLException {        
        connection = DriverManager.getConnection(dbPath);
        System.out.println("Konekcija uspostavljena s bazom podataka." );
        return connection;        
    }
    
    public boolean InsDelUpd (String sql) throws SQLException{
        try{ 
            connection = DriverManager.getConnection(dbPath);
            connection.setAutoCommit(false);
            statement = connection.createStatement();           
            statement.execute(sql);
            try{
                statement.close();             
            }
            finally{statement = null;}
            connection.commit();
            return true;
        }
        catch (SQLException ex){
            connection.rollback();
            return false;
        }
        finally {
            if(statement != null){
                try{statement.close();}
                catch (SQLException ex){} //do nothing                
            }
            System.out.println("Prekida se konekcija s bazom podataka.");           
            connection.close();
        }
    }
    
    public void closeConnection() throws SQLException{
        connection.close();
        System.out.println("Prekida se konekcija s bazom podataka.");
    }
    
    private String Path(){
         try (InputStream input = new FileInputStream("connection.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value           
            return prop.getProperty("Path");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    private String PathFromApp(){
        String path= null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return path;
    }
}
