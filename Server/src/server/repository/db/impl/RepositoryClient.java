/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.repository.db.impl;

import commonlib.domain.Client;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import server.repository.db.DbConnectionFactory;
import server.repository.db.DbRepository;

/**
 *
 * @author ANA
 */
public class RepositoryClient implements DbRepository<Client, Long>{
    Connection connection;
    
    @Override
    public List<Client> getAll() throws Exception {
        try {
            List<Client> clients =new ArrayList<>();
            String upit="SELECT id, firstname, lastname, jmbg FROM clients";
            connection=DbConnectionFactory.getInstance().getConnection();
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(upit);
            
            while(rs.next()){
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setFirstName(rs.getString("firstname"));
                client.setLastName(rs.getString("lastname"));
                client.setJMBG(rs.getString("jmbg"));
                clients.add(client);
            }
            rs.close();
            statement.close();
            return clients;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void add(Client t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Client t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Client t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
