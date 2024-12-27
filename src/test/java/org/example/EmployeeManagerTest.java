package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagerTest {
    private EmployeeManager employeeManager;

    @BeforeEach
    void setUp() {
        employeeManager = new EmployeeManager();
    }

    @Test
    void testAddEmployee() throws Exception {
        DefaultTableModel tableModel = getTableModel();
        Map<String, EmployeeManager.EmployeeDetails> detailsMap = getDetailsMap();

        // Simulate adding an employee
        SwingUtilities.invokeAndWait(() -> {  // Menunggu eksekusi selesai
            tableModel.addRow(new Object[]{"John Doe", "Developer", 50000.0});
            detailsMap.put("John Doe", new EmployeeManager.EmployeeDetails(
                    "John Doe", "Developer", 50000.0, new ImageIcon()
            ));
        });

        // Verifikasi setelah perubahan selesai
        assertEquals(1, tableModel.getRowCount());
        assertTrue(detailsMap.containsKey("John Doe"));
    }

    @Test
    void testRemoveEmployee() {
        DefaultTableModel tableModel = getTableModel();
        Map<String, EmployeeManager.EmployeeDetails> detailsMap = getDetailsMap();

        // Add and remove an employee
        SwingUtilities.invokeLater(() -> {
            tableModel.addRow(new Object[]{"Jane Doe", "Manager", 70000.0});
            detailsMap.put("Jane Doe", new EmployeeManager.EmployeeDetails(
                    "Jane Doe", "Manager", 70000.0, new ImageIcon()
            ));

            tableModel.removeRow(0);
            detailsMap.remove("Jane Doe");
        });

        assertEquals(0, tableModel.getRowCount());
        assertFalse(detailsMap.containsKey("Jane Doe"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        DefaultTableModel tableModel = getTableModel();
        Map<String, EmployeeManager.EmployeeDetails> detailsMap = getDetailsMap();

        // Add and update an employee
        SwingUtilities.invokeAndWait(() -> {  // Menunggu eksekusi selesai
            tableModel.addRow(new Object[]{"Alice", "Designer", 60000.0});
            detailsMap.put("Alice", new EmployeeManager.EmployeeDetails(
                    "Alice", "Designer", 60000.0, new ImageIcon()
            ));

            tableModel.setValueAt("Alice Smith", 0, 0);
            tableModel.setValueAt("Senior Designer", 0, 1);
            tableModel.setValueAt(75000.0, 0, 2);

            detailsMap.put("Alice Smith", new EmployeeManager.EmployeeDetails(
                    "Alice Smith", "Senior Designer", 75000.0, new ImageIcon()
            ));
            detailsMap.remove("Alice");
        });

        // Verifikasi setelah perubahan selesai
        assertEquals("Alice Smith", tableModel.getValueAt(0, 0));
        assertEquals("Senior Designer", tableModel.getValueAt(0, 1));
        assertEquals(75000.0, tableModel.getValueAt(0, 2));
        assertTrue(detailsMap.containsKey("Alice Smith"));
        assertFalse(detailsMap.containsKey("Alice"));
    }
    // Helper methods to access private fields
    private DefaultTableModel getTableModel() {
        return (DefaultTableModel) employeeManager.employeeTable.getModel();
    }

    private Map<String, EmployeeManager.EmployeeDetails> getDetailsMap() {
        return employeeManager.employeeDetailsMap;
    }
}
