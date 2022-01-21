/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.components;

import commonlib.domain.MeasurementUnit;
import commonlib.domain.Medicine;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ANA
 */
public class TableModelMedicines extends AbstractTableModel {

    private List<Medicine> medicines;
    private final String[] columnNames = new String[]{"ID", "Naziv", "Cijena", "Dostupna količina", "Mjerna jedinica"};
    private final Class[] columnClass = new Class[]{Long.class, String.class, BigDecimal.class, BigDecimal.class, MeasurementUnit.class};

    public TableModelMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    @Override
    public int getRowCount() {
        if (medicines != null) {
            return medicines.size();
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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > columnClass.length) {
            return Object.class;
        } else {
            return columnClass[columnIndex];
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Medicine medicine = medicines.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return medicine.getId();
            case 1:
                return medicine.getName();
            case 2:
                return medicine.getPrice();
            case 3:
                return medicine.getAvailableQuantity();
            case 4:
                return medicine.getMeasurementUnit();
            default:
                return "N/A";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Medicine medicine = medicines.get(rowIndex);
        switch (columnIndex) {
            case 1:
                medicine.setName(aValue.toString());
                break;
            case 2:
                try {
                    medicine.setPrice(new BigDecimal(aValue.toString()));
                } catch (Exception ex) {
                    medicine.setPrice(BigDecimal.ZERO);
                }
                break;
            case 3:
                try {
                    medicine.setAvailableQuantity(new BigDecimal(aValue.toString()));
                } catch (Exception ex) {
                    medicine.setAvailableQuantity(BigDecimal.ZERO);
                }
                break;
            case 4:
                try {
                    medicine.setMeasurementUnit(MeasurementUnit.valueOf(aValue.toString()));
                } catch (Exception ex) {
                    medicine.setMeasurementUnit(MeasurementUnit.pcs);
                }
                break;
        }
    }

    public void updateTable(List<Medicine> medicines) {
        this.medicines = medicines;
        fireTableDataChanged();
    }

    public void removeMedicine(int row) {
        medicines.remove(row);
        fireTableDataChanged();
    }

    public Medicine getMedicine(int row) {
        return medicines.get(row);
    }

    public void add(Medicine medicine) {
        medicines.add(medicine);
        fireTableRowsInserted(medicines.size() - 1, medicines.size() - 1);
    }

    public void change(Medicine medicine) {
        try {
            int row = 0;
            for (Medicine med : medicines) {
                if (med.getId().compareTo(medicine.getId()) == 0) {
                    med.setAvailableQuantity(medicine.getAvailableQuantity());
                    med.setMeasurementUnit(medicine.getMeasurementUnit());
                    med.setName(medicine.getName());
                    med.setPrice(medicine.getPrice());
                    fireTableCellUpdated(row,1);
                    fireTableCellUpdated(row,2);
                    fireTableCellUpdated(row,3);
                    fireTableCellUpdated(row,4);
                    break;
                }
                row++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void delete(Medicine medicine) {
        try {
            for (Medicine med : medicines) {
                if (med.getId().compareTo(medicine.getId()) == 0) {
                    medicines.remove(med);
                    break;
                }
            }
            fireTableDataChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }
}
