/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.repository.db.impl;

import commonlib.domain.ModelElement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import server.repository.db.DbConnectionFactory;
import server.repository.db.DbRepository;

/**
 *
 * @author ANA
 */
public class Repository implements DbRepository<ModelElement, ModelElement> {

    private Connection connection;

    @Override
    public List<ModelElement> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(ModelElement el) throws Exception {
        try {
            String upit = "INSERT INTO " + el.getTableName() + "(" + el.getDbChangeableColumns() + ")" + " VALUES (" + el.getAtrValues() + ")";
            connection = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            el.setId(rs);
            rs.close();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void edit(ModelElement el) throws Exception {
        try {
            String upit = "UPDATE " + el.getTableName() + " SET " + el.setAtrValues() + " WHERE " + el.getWhereCondition();
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void delete(ModelElement el) throws Exception {
        try {
            String upit = "DELETE FROM " + el.getTableName() + " WHERE " + el.getWhereCondition();
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public ModelElement getById(ModelElement el) throws Exception {
        try {
            String upit = "SELECT * FROM " + el.getTableName() + " WHERE " + el.getWhereCondition();
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement st = connection.prepareStatement(upit);
            ResultSet rs = st.executeQuery(upit);
            if (rs.next()) {
                el = el.getNewRecord(rs);
            } else {
                el = null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return el;
    }

}
