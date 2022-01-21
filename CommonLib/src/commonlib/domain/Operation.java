/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author ANA
 */
public class Operation implements Serializable, ModelElement{

    private Long id;
    private String name;
    private BigDecimal price;

    public Operation() {
    }

    public Operation(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.price);
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
        final Operation other = (Operation) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal Price) {
        this.price = Price;
    }
    
    @Override
    public String getAtrValues() {
        return "'" + name + "'" +", " + price;
    }

    @Override
    public String setAtrValues() {
        return "name = " + "'" +name + "'" + ", " + "price = "+ price;
    }

    @Override
    public String getDbChangeableColumns() {
        return "name, price";
    }
    
    @Override
    public String getTableName() {
        return "operations";
    }

    @Override
    public String getWhereCondition() {
        return "id= "+id;
    }

    @Override
    public String getNameByColumn(int column) {
        String[] names = new String[]{"id","name","price"};
        return names[column];
    }

    @Override
    public ModelElement getNewRecord(ResultSet rs) throws SQLException {
        Operation o = new Operation();
        o.setId(rs.getLong("id"));
        o.setName(rs.getString("name"));
        o.setPrice(rs.getBigDecimal("price"));
        return o;
    }
    
     @Override
    public void setId(ResultSet rs) throws Exception {
        this.id = rs.getLong(1);
    }

}
