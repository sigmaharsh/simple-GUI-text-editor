import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea;
    JFileChooser fileChooser;

    // Constructor to set up the GUI components
    public TextEditor() {
        // Set up the text area
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Set up the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Set up the JFrame
        setTitle("Simple Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(scrollPane);
        setVisible(true);

        // Initialize file chooser
        fileChooser = new JFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    // Open a file and display its contents in the text area
    private void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Save the text area content to a file
    private void saveFile() {
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Main method to launch the editor
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}
