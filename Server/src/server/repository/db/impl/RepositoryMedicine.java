/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.repository.db.impl;

import commonlib.domain.MeasurementUnit;
import commonlib.domain.Medicine;
import java.io.IOException;
import java.math.BigDecimal;
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
public class RepositoryMedicine implements DbRepository<Medicine, Long> {

    private Connection connection;

    public RepositoryMedicine() {
    }

    @Override
    public List<Medicine> getAll() throws Exception {
        try {

            List<Medicine> medicines = new ArrayList<>();
            String upit = "SELECT id, name, price, availableQuantity, measurementUnit FROM medicines";
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);

            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setId(rs.getLong("id"));
                medicine.setName(rs.getString("name"));
                medicine.setPrice(rs.getBigDecimal("price"));
                medicine.setAvailableQuantity(rs.getBigDecimal("availableQuantity"));
                medicine.setMeasurementUnit(MeasurementUnit.valueOf(rs.getString("measurementUnit")));
                medicines.add(medicine);
            }
            rs.close();
            statement.close();
            return medicines;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void add(Medicine t) throws Exception {
        try {
            String upit = "INSERT INTO medicines (name, price, availableQuantity, measurementUnit) VALUES (?,?,?,?)";
            connection = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, t.getName());
            statement.setBigDecimal(2, t.getPrice());
            statement.setBigDecimal(3, t.getAvailableQuantity());
            statement.setString(4, t.getMeasurementUnit().toString());
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
    public void edit(Medicine t) throws Exception {
        try {
            String upit = "UPDATE medicines SET name = " + "'" + t.getName() + "', price = " + t.getPrice() + ", availableQuantity = " + t.getAvailableQuantity() + ", measurementUnit = '" + t.getMeasurementUnit().toString() + "'" + " WHERE id = " + t.getId();
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
    public void delete(Medicine t) throws Exception {
        try {
            String upit = "DELETE FROM medicines WHERE Id = " + t.getId();
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
    public Medicine getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void updateAvailableQuantity(Medicine medicine) throws Exception{
        try {
            String upit = "UPDATE medicines SET availableQuantity = " + medicine.getAvailableQuantity() + " WHERE id = " + medicine.getId();
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
