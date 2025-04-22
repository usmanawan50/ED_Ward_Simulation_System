package App;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationGUI extends JFrame {
    private final JTextField simulationTimeField, patientsPerMinuteField, bedsField, doctorsField, interneeDoctorsField;
    private final JButton startButton, clearButton;
    private final JTextArea logArea;

    public SimulationGUI() {
        // Set up the frame
        setTitle("Emergency Department Simulation");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Top panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 128, 0));

        inputPanel.add(new JLabel("Simulation Time(in months): "));
        simulationTimeField = new JTextField();
        simulationTimeField.setBackground(new Color(128, 255, 0));
        inputPanel.add(simulationTimeField);

        inputPanel.add(new JLabel("Patients Density"));
        patientsPerMinuteField = new JTextField();
        patientsPerMinuteField.setBackground(new Color(128, 255, 0));
        inputPanel.add(patientsPerMinuteField);

        inputPanel.add(new JLabel("Number of Beds: "));
        bedsField = new JTextField();
        bedsField.setBackground(new Color(128, 255, 0));
        inputPanel.add(bedsField);

        inputPanel.add(new JLabel("Number of Doctors: "));
        doctorsField = new JTextField();
        doctorsField.setBackground(new Color(128, 255, 0));
        inputPanel.add(doctorsField);

        inputPanel.add(new JLabel("Number of Internee Doctors: "));
        interneeDoctorsField = new JTextField();
        interneeDoctorsField.setBackground(new Color(128, 255, 0));
        inputPanel.add(interneeDoctorsField);

        // Add "Start Simulation" and "Clear All" buttons
        startButton = new JButton("Start Simulation");
        startButton.setBackground(new Color(255, 0, 0));

        clearButton = new JButton("Clear All");
        clearButton.setBackground(new Color(255, 0, 0));
        inputPanel.add(startButton);
        inputPanel.add(clearButton);

        getContentPane().add(inputPanel, BorderLayout.NORTH);

        // Center panel for the log area
        logArea = new JTextArea();
        logArea.setBackground(new Color(128, 255, 0));
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(logArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Action listeners for buttons
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllFields();
            }
        });

        // Make the GUI visible
        setVisible(true);
    }

    private void startSimulation() {
        try {
            // Parse user inputs
            int simulationTime = Integer.parseInt(simulationTimeField.getText()) * 30; // Convert months to days
            int patientsPerMinute = Integer.parseInt(patientsPerMinuteField.getText());
            int beds = Integer.parseInt(bedsField.getText());
            int doctors = Integer.parseInt(doctorsField.getText());
            int interneeDoctors = Integer.parseInt(interneeDoctorsField.getText());

            // Validate inputs
            if (simulationTime <= 0 || patientsPerMinute <= 0 || beds <= 0 || doctors <= 0 || interneeDoctors <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter positive numeric values!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Initialize resources
            ResourcePool resources = new ResourcePool(beds, doctors, interneeDoctors);

            // Run simulation
            SimulationManager manager = new SimulationManager(simulationTime, patientsPerMinute / 60.0, resources, logArea);
            manager.runSimulation();

            JOptionPane.showMessageDialog(this, "Simulation complete! Report saved to simulation_log.txt.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAllFields() {
        // Clear all input fields and the log area
        simulationTimeField.setText("");
        patientsPerMinuteField.setText("");
        bedsField.setText("");
        doctorsField.setText("");
        interneeDoctorsField.setText("");
        logArea.setText("");
    }

    public static void main(String[] args) {
    	try {
    		SwingUtilities.invokeLater(SimulationGUI::new);
    	}
        catch (Exception e) {
        	System.out.println("Error!");
        	e.printStackTrace();
        }
    }
}
