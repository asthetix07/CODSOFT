import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem {

    private List<Student> students = new ArrayList<>();
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, rollNumberField, gradeField;

    public StudentManagementSystem() {
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Name", "Roll Number", "Grade"}, 0);
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Roll Number:"));
        rollNumberField = new JTextField();
        panel.add(rollNumberField);

        panel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        panel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new AddStudentAction());
        panel.add(addButton);

        JButton removeButton = new JButton("Remove Student");
        removeButton.addActionListener(new RemoveStudentAction());
        panel.add(removeButton);

        frame.add(panel, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new SaveAction());
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new LoadAction());
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }

    private class AddStudentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String rollNumber = rollNumberField.getText();
            String grade = gradeField.getText();

            if (name.isEmpty() || rollNumber.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student(name, rollNumber, grade);
            students.add(student);
            tableModel.addRow(new Object[]{student.getName(), student.getRollNumber(), student.getGrade()});
            clearFields();
        }
    }

    private class RemoveStudentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                students.remove(selectedRow);
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a student to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (Student student : students) {
                        writer.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
                        writer.newLine();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class LoadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    students.clear();
                    tableModel.setRowCount(0); // Clear the table
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        String name = parts[0];
                        String rollNumber = parts[1];
                        String grade = parts[2];
                        Student student = new Student(name, rollNumber, grade);
                        students.add(student);
                        tableModel.addRow(new Object[]{student.getName(), student.getRollNumber(), student.getGrade()});
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading from file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollNumberField.setText("");
        gradeField.setText("");
    }

    private static class Student {
        private String name;
        private String rollNumber; // Changed to String
        private String grade;

        public Student(String name, String rollNumber, String grade) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public String getRollNumber() {
            return rollNumber;
        }

        public String getGrade() {
            return grade;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", rollNumber='" + rollNumber + '\'' +
                    ", grade='" + grade + '\'' +
                    '}';
        }
    }
}
