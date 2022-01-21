/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author ANA
 */
public class Employee implements Serializable, ModelElement{

    private Long id;
    private String firstName;
    private String lastName;
    private String JMBG;
    private String username;
    private String password;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String JMBG, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.JMBG = JMBG;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.JMBG);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.JMBG, other.JMBG)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String getAtrValues() {
        return "'" + firstName + "'" +", " + lastName + ", " + "'" + JMBG +"'" + ", " + "'" + username +"'" + ", " + "'" + password +"'";
    }

    @Override
    public String setAtrValues() {
        return "firstname = " + "'" +firstName + "'" + ", " + "lastname = " + "'" +lastName + "'" + ", " + "jmbg = " + "'" +JMBG + "'" + ", " + "username = " + "'" +username + "'" + ", " + "password = " + "'" +password + "'" ; 
    }

    @Override
    public String getDbChangeableColumns() {
        return "firstname, lastname, jmbg, username, password";
    }
    
    @Override
    public String getTableName() {
        return "employees";
    }

    @Override
    public String getWhereCondition() {
        return "id= "+id;
    }

    @Override
    public String getNameByColumn(int column) {
        String[] names = new String[]{"id","firstname","lastname","jmbg", "username", "password"};
        return names[column];
    }

    @Override
    public ModelElement getNewRecord(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setFirstName(rs.getString("firstname"));
        employee.setLastName(rs.getString("lastname"));
        employee.setJMBG(rs.getString("jmbg"));
        employee.setUsername(rs.getString("username"));
        employee.setPassword(rs.getString("password"));
        return employee;
    }

    @Override
    public void setId(ResultSet rs) throws Exception {
        this.id = rs.getLong(1);
    }
    
}
