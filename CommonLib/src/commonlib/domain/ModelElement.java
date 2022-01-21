/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ANA
 */
public interface ModelElement {
    
    String getAtrValues();

    String setAtrValues();
    
    String getDbChangeableColumns();

    String getTableName();

    String getWhereCondition();

    String getNameByColumn(int column);

    ModelElement getNewRecord(ResultSet rs) throws SQLException;
    
    default void setId(ResultSet rs) throws Exception{}
    
}
