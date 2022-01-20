/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.components;

import commonlib.domain.Employee;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ANA
 */
public class TableModelLoggedUsers extends AbstractTableModel{
    private List<Employee> employees;
    private String[] columnNames = new String[]{"ID", "Ime", "Prezime", "Korisničko ime"};
    private Class[] columnClass = new Class[]{Long.class, String.class, String.class, String.class};

    public TableModelLoggedUsers(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        if (employees != null) {
            return employees.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex>columnClass.length){
            return Object.class;
        }else{
            return columnClass[columnIndex];
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return employee.getId();
            case 1:
                return employee.getFirstName();
            case 2:
                return employee.getLastName();
            case 3:
                return employee.getUsername();
            default:
                return "N/A";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void updateTable(List<Employee> employees) {
        this.employees = employees;
        fireTableDataChanged();
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        fireTableDataChanged();
    }
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
        fireTableRowsInserted(employees.size() - 1, employees.size() - 1);
    }
}
