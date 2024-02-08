package prueba;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JTextField textField1, textField2, textField3;
    private JComboBox<String> comboBox;

    public MainFrame() {
        setTitle("GridBagLayout Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JButton button = new JButton("Submit");
        textField1 = new JTextField(20);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        String[] options = {"Option 1", "Option 2", "Option 3"};
        comboBox = new JComboBox<>(options);

        // Create panel and set layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5); // Padding
        constraints.anchor = GridBagConstraints.WEST; // Align components to the left

        // Add components to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Field 1:"), constraints);

        constraints.gridy = 1;
        panel.add(new JLabel("Field 2:"), constraints);

        constraints.gridy = 2;
        panel.add(new JLabel("Field 3:"), constraints);

        constraints.gridy = 3;
        panel.add(new JLabel("Select Option:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(textField1, constraints);

        constraints.gridy = 1;
        panel.add(textField2, constraints);

        constraints.gridy = 2;
        panel.add(textField3, constraints);

        constraints.gridy = 3;
        panel.add(comboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(button, constraints);

        // Add panel to frame
        getContentPane().add(panel, BorderLayout.WEST); // Align panel to the left

        // Set preferred size of the frame
        setPreferredSize(new Dimension(1500, 1000));

        // Add action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform action here
                String field1Value = textField1.getText();
                String field2Value = textField2.getText();
                String field3Value = textField3.getText();
                int selectedOption = comboBox.getSelectedIndex();

                // Example: print the values
                System.out.println("Field 1: " + field1Value);
                System.out.println("Field 2: " + field2Value);
                System.out.println("Field 3: " + field3Value);
                System.out.println("Selected Option: " + selectedOption);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
