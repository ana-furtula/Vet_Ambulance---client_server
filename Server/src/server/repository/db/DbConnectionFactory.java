/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.repository.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import server.constant.MyServerConstants;

/**
 *
 * @author ANA
 */
public class DbConnectionFactory {
    private Connection conn;
    private static DbConnectionFactory instance;
    
    private DbConnectionFactory(){
    }
    
    public static DbConnectionFactory getInstance(){
        if(instance==null){
            instance = new DbConnectionFactory();
        }
        return instance;
    } 
    
    public Connection getConnection() throws SQLException, IOException{
        if (conn == null || conn.isClosed()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(MyServerConstants.DB_CONFIG_FILE_PATH));
                String url = properties.getProperty(MyServerConstants.DB_CONFIG_URL);
                String user = properties.getProperty(MyServerConstants.DB_CONFIG_USERNAME);
                String pass = properties.getProperty(MyServerConstants.DB_CONFIG_PASSWORD);
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Successful connection!");
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
                System.out.println("Error while establishing connection.");
                throw ex;
            }
        }
        return conn;
    }
}
