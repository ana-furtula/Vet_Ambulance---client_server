/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author ANA
 */
public class Client implements Serializable, ModelElement{

    private Long id;
    private String firstName;
    private String lastName;
    private String JMBG;

    public Client() {
    }

    public Client(Long id, String firstName, String lastName, String JMBG) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.JMBG = JMBG;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.JMBG);
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
        final Client other = (Client) obj;
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
        return firstName + " " + lastName + ", JMBG: " + JMBG;
    }
    
    @Override
    public String getAtrValues() {
        return "'" + firstName + "'" +", " + lastName + ", " + "'" + JMBG +"'";
    }

    @Override
    public String setAtrValues() {
        return "firstname = " + "'" +firstName + "'" + ", " + "lastname = " + "'" +lastName + "'" + ", " + "jmbg = " + "'" +JMBG + "'" ; 
    }

    @Override
    public String getDbChangeableColumns() {
        return "firstname, lastname, jmbg";
    }
    
    @Override
    public String getTableName() {
        return "clients";
    }

    @Override
    public String getWhereCondition() {
        return "id= "+id;
    }

    @Override
    public String getNameByColumn(int column) {
        String[] names = new String[]{"id","firstname","lastname","jmbg"};
        return names[column];
    }

    @Override
    public ModelElement getNewRecord(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id"));
        client.setFirstName(rs.getString("firstname"));
        client.setLastName(rs.getString("lastname"));
        client.setJMBG(rs.getString("jmbg"));
        return client;
    }

    @Override
    public void setId(ResultSet rs) throws Exception {
        this.id = rs.getLong(1);
    }

}
