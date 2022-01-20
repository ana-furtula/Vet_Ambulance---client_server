/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.repository.db.impl;

import commonlib.domain.Operation;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class RepositoryOperation implements DbRepository<Operation, Long> {

    Connection connection;

    public RepositoryOperation() {
    }

    @Override
    public List<Operation> getAll() throws Exception {
        try {
            List<Operation> operations = new ArrayList<>();
            String upit = "SELECT id, name, price FROM operations";
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);

            while (rs.next()) {
                Operation operation = new Operation();
                operation.setId(rs.getLong("id"));
                operation.setName(rs.getString("name"));
                operation.setPrice(rs.getBigDecimal("price"));
                operations.add(operation);
            }
            rs.close();
            statement.close();
            return operations;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void add(Operation t) throws Exception {
        try {
            String upit = "INSERT INTO operations (name, price) VALUES (?,?)";
            connection = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, t.getName());
            statement.setBigDecimal(2, t.getPrice());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            t.setId(rs.getLong(1));
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void edit(Operation t) throws Exception {
        try {
            String upit = "UPDATE operations SET name = " + "'" + t.getName() + "', price = " + t.getPrice() + " WHERE id = " + t.getId();
            connection = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(upit);
            int affectedRows = statement.executeUpdate();
            statement.close();
            if(affectedRows <1){
                throw new Exception("Nijedna operacija nije promijenjena.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void delete(Operation t) throws Exception {
        try {
            String upit = "DELETE FROM operations WHERE Id = " + t.getId();
            connection = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(upit);
            int affectedRows = statement.executeUpdate();
            statement.close();
            if(affectedRows<1){
                throw new Exception("Nijedna operacija nije izbrisana.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Operation getById(Long k) throws Exception {
        try {
            Operation operation = new Operation();
            String upit = "SELECT id, name, price FROM operations WHERE id = " + k;
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            if (rs.next()) {
                operation.setId(rs.getLong("id"));
                operation.setName(rs.getString("name"));
                operation.setPrice(rs.getBigDecimal("price"));
            } else {
                throw new Exception("Ne postoji operacija sa ID = " + k);
            }
            rs.close();
            statement.close();
            return operation;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
