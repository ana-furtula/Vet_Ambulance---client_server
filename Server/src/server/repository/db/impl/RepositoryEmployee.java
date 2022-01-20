/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.repository.db.impl;

import commonlib.domain.Employee;
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
public class RepositoryEmployee implements DbRepository<Employee, Long> {
     private Connection connection;

    public RepositoryEmployee() {
    }

    public List<Employee> getAll() throws Exception {
        try {
            List<Employee> employees =new ArrayList<>();
            String upit="SELECT id, firstname, lastname, username, password, jmbg FROM employees";
            connection=DbConnectionFactory.getInstance().getConnection();
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(upit);
            
            while(rs.next()){
                Employee employee=new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFirstName(rs.getString("firstname"));
                employee.setLastName(rs.getString("lastname"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setJMBG(rs.getString("jmbg"));
                employees.add(employee);
            }
            rs.close();
            statement.close();
            return employees;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void add(Employee t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Employee t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Employee t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Employee getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void changePassword(Employee employee) throws Exception {
        try {
            String upit="UPDATE employees SET password = '"+employee.getPassword()+"' WHERE id="+employee.getId();
            connection=DbConnectionFactory.getInstance().getConnection();
            Statement statement=connection.createStatement();
            statement.executeUpdate(upit);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
