package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;

class EmployeeManager {


    public JFrame frame;
    public JTable employeeTable;
    public DefaultTableModel tableModel;
    public Map<String, EmployeeDetails> employeeDetailsMap;

    public EmployeeManager() {
        frame = new JFrame("Manajemen Karyawan");
        tableModel = new DefaultTableModel(new String[]{"Nama", "Posisi", "Gaji"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeTable = new JTable(tableModel);
        employeeDetailsMap = new HashMap<>();

        setupGUI();
    }

    public void setupGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        // Panel tabel untuk daftar karyawan
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 1), "Daftar Karyawan", 0, 0, new Font("Arial", Font.BOLD, 14), Color.BLUE));

        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setRowHeight(30);
        employeeTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel bawah untuk input dan tombol
        JPanel bottomPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Form Input"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel nameLabel = new JLabel("Nama Karyawan:");
        JTextField nameField = new JTextField();

        JLabel positionLabel = new JLabel("Posisi:");
        JTextField positionField = new JTextField();

        JLabel salaryLabel = new JLabel("Gaji:");

        JTextField salaryField = new JTextField();

        JLabel imageLabel = new JLabel("Foto Path:");
        JTextField imageField = new JTextField();
        JButton browseButton = new JButton("Browse...");

        JButton addButton = new JButton("Tambah Karyawan");
        JButton removeButton = new JButton("Hapus Karyawan");
        JButton updateButton = new JButton("Update Karyawan");
        JButton readButton = new JButton("Detail Karyawan");

        // Menyesuaikan tampilan tombol
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        addButton.setFont(buttonFont);
        removeButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        readButton.setFont(buttonFont);

        bottomPanel.add(nameLabel);
        bottomPanel.add(nameField);
        bottomPanel.add(positionLabel);
        bottomPanel.add(positionField);
        bottomPanel.add(salaryLabel);
        bottomPanel.add(salaryField);
        bottomPanel.add(imageLabel);

        JPanel imageInputPanel = new JPanel(new BorderLayout());
        imageInputPanel.add(imageField, BorderLayout.CENTER);
        imageInputPanel.add(browseButton, BorderLayout.EAST);
        bottomPanel.add(imageInputPanel);
        bottomPanel.add(addButton);
        bottomPanel.add(removeButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(readButton);

        // Menambahkan panel ke frame
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Aksi tombol Browse
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imageField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Aksi tombol Tambah
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String position = positionField.getText().trim();
            String salaryStr = salaryField.getText().trim();
            String imagePath = imageField.getText().trim();

            if (name.isEmpty() || position.isEmpty() || salaryStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua data harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double salary = Double.parseDouble(salaryStr);
                ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));

                tableModel.addRow(new Object[]{name, position, salary});
                employeeDetailsMap.put(name, new EmployeeDetails(name, position, salary, icon));

                nameField.setText("");
                positionField.setText("");
                salaryField.setText("");
                imageField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Gaji harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Gagal memuat foto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Aksi tombol Hapus
        removeButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedName = (String) tableModel.getValueAt(selectedRow, 0);
                tableModel.removeRow(selectedRow);
                employeeDetailsMap.remove(selectedName);
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih karyawan yang ingin dihapus!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Aksi tombol Update
        updateButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                String oldName = (String) tableModel.getValueAt(selectedRow, 0);

                String name = nameField.getText().trim();
                String position = positionField.getText().trim();
                String salaryStr = salaryField.getText().trim();
                String imagePath = imageField.getText().trim();

                if (name.isEmpty() || position.isEmpty() || salaryStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Semua data harus diisi untuk update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double salary = Double.parseDouble(salaryStr);
                    ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));

                    tableModel.setValueAt(name, selectedRow, 0);
                    tableModel.setValueAt(position, selectedRow, 1);
                    tableModel.setValueAt(salary, selectedRow, 2);

                    employeeDetailsMap.remove(oldName);
                    employeeDetailsMap.put(name, new EmployeeDetails(name, position, salary, icon));

                    nameField.setText("");
                    positionField.setText("");
                    salaryField.setText("");
                    imageField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Gaji harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Gagal memuat foto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih karyawan yang ingin diupdate!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Aksi tombol Detail
        readButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedName = (String) tableModel.getValueAt(selectedRow, 0);
                if (employeeDetailsMap.containsKey(selectedName)) {
                    showEmployeeDetails(selectedName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Detail karyawan tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih karyawan yang ingin dilihat!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    public void showEmployeeDetails(String name) {
        EmployeeDetails details = employeeDetailsMap.get(name);

        JFrame detailsFrame = new JFrame("Detail Karyawan - " + name);
        detailsFrame.setSize(450, 550);
        detailsFrame.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Nama: " + details.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel positionLabel = new JLabel("Posisi: " + details.getPosition());
        positionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel salaryLabel = new JLabel("Gaji: " + details.getSalary());
        salaryLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        detailsPanel.add(nameLabel);
        detailsPanel.add(positionLabel);
        detailsPanel.add(salaryLabel);

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(scaleImageIcon(details.getPhoto(), 300, 300));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        detailsFrame.add(detailsPanel, BorderLayout.NORTH);
        detailsFrame.add(imageLabel, BorderLayout.CENTER);
        detailsFrame.setVisible(true);
    }

    public ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeManager::new);
    }

    static class EmployeeDetails {
        public String name;
        public String position;
        public double salary;
        public ImageIcon photo;

        public EmployeeDetails(String name, String position, double salary, ImageIcon photo) {
            this.name = name;
            this.position = position;
            this.salary = salary;
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public String getPosition() {
            return position;
        }

        public double getSalary() {
            return salary;
        }

        public ImageIcon getPhoto() {
            return photo;
        }
    }
}
