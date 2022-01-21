/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author ANA
 */
public class Medicine implements Serializable, ModelElement{

    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal availableQuantity;
    private MeasurementUnit measurementUnit;

    public Medicine() {
    }

    public Medicine(Long id, String name, BigDecimal price, BigDecimal availableQuantity, MeasurementUnit measurementUnit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.measurementUnit = measurementUnit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.price);
        hash = 23 * hash + Objects.hashCode(this.availableQuantity);
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
        final Medicine other = (Medicine) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.availableQuantity, other.availableQuantity)) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    @Override
    public String getAtrValues() {
        return "'" + name + "'" +", " + price + ", " + availableQuantity +", " + "'" + measurementUnit.toString() +"'";
    }

    @Override
    public String setAtrValues() {
        return "name = " + "'" +name + "'" + ", " + "price = "+ price +", "+" availableQuantity = "+ availableQuantity +", "+"measurementUnit= "+ "'" + measurementUnit.toString()+"'";
    }

    @Override
    public String getDbChangeableColumns() {
        return "name, price, availableQuantity, measurementUnit";
    }
    
    @Override
    public String getTableName() {
        return "medicines";
    }

    @Override
    public String getWhereCondition() {
        return "id= "+id;
    }

    @Override
    public String getNameByColumn(int column) {
        String[] names = new String[]{"id","name","price","availableQuantity","measurementUnit"};
        return names[column];
    }

    @Override
    public ModelElement getNewRecord(ResultSet rs) throws SQLException {
        Medicine m = new Medicine();
        m.setId(rs.getLong("id"));
        m.setName(rs.getString("name"));
        m.setPrice(rs.getBigDecimal("price"));
        m.setMeasurementUnit(MeasurementUnit.valueOf(rs.getString("measurementUnit")));
        m.setAvailableQuantity(rs.getBigDecimal("availableQuantity"));
        return m;
    }

    @Override
    public void setId(ResultSet rs) throws Exception {
        this.id = rs.getLong(1);
    }
    
}
