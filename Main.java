import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    static JFrame frame;
    static JPanel showProcess;
    static JPanel boxPanel;

    static Util util = new Util();
    static Process processes[];
    static JSpinner spinnerBox[];
    static JComboBox algoBox[];
    static int selectedOption;
    static ArrayList<JComboBox> comboBoxesList = new ArrayList<>(); // List to track JComboBoxes
    static ArrayList<JSpinner> spinnerBoxesList = new ArrayList<>(); // List to track JComboBoxes
    static Queues[] queue = new Queues[3];

    public static void main(String[] args) {

        initComponents();

        List<Process> processes = Arrays.asList(
                new Process(1, 0, 10, 1),
                new Process(2, 2, 5, 1),
                new Process(3, 4, 2, 1)
        );

        ArrayList<Queues> queues = new ArrayList<>(3);

        for(int i=0; i<3; i++){
            queues.add(new Queues());
            queues.get(i).setPriority(i+1);
            queues.get(i).setAllotedTime((int) Math.pow(2, i+2));
            queues.get(i).setScheduler(Scheduler.FCFS);
        }
        queues.get(0).setScheduler(Scheduler.SJF);
        
        new MLFQ(processes, queues, boxPanel);
    }

    public JComboBox createComboBox(String[] algos){

        JComboBox algoBox = new JComboBox(algos); 
        algoBox.setSelectedIndex(0);
        return algoBox;
    }

    public static void initComponents() {
        JFrame frame = new JFrame("Process Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 10));

        String[] columns = {"Process", "Arrival Time", "CPU Burst Time", "Priority"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(50, 20, 600, 200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addProcess = new JButton("Add Process");
        addProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow(model);
            }
        });

        JButton clrBtn = new JButton("Clear All");
        JPanel btnPanel = new JPanel(new BorderLayout(20, 20));
      

        JButton runBtn = new JButton("Run");

        String[] queueOptions = {"0", "1", "2", "3"};

        // Create a JComboBox with the options array
        JComboBox<String> numberofQueues = new JComboBox<>(queueOptions);
        numberofQueues.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0,10,10,10), "Number of Queues"));
        numberofQueues.setSelectedIndex(0);


        GridBagLayout grid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        btnPanel.setLayout(grid);     
        gbc.insets = new Insets(5, 10, 5, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;  

        gbc.gridx = 0;  
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        btnPanel.add(numberofQueues, gbc);

        comboBoxesList.add(util.createBox());

         // ActionListener for numberofQueues
         numberofQueues.addActionListener(e -> {
            selectedOption = Integer.parseInt(numberofQueues.getSelectedItem().toString());
            System.out.println("Selected number of queues: " + selectedOption);

            // Remove all comboBoxes from the panel and list
            for (JComboBox box : comboBoxesList) {
                btnPanel.remove(box);
            }

            // Remove all comboBoxes from the panel and list
            for (JSpinner spinner : spinnerBoxesList) {
                btnPanel.remove(spinner);
            }

            comboBoxesList.clear(); // Clear the list of comboBoxes
            spinnerBoxesList.clear(); // Clear the list of spinnerBoxes

            // Add new comboBoxes based on the selected option
            algoBox = new JComboBox[selectedOption];
            spinnerBox = new JSpinner[selectedOption];

            for (int i = 0; i < selectedOption; i++) {
                
                //queue[i] = new Queues();

                gbc.insets = new Insets(5, 10, 5, 10); 

                // Create a SpinnerModel to define the range and initial value
                SpinnerModel spinnerModel = new SpinnerNumberModel(0, // initial value
                0, // minimum value
                20, // maximum value
                1); // step size

                algoBox[i] = util.createBox();
                spinnerBox[i] = new JSpinner(spinnerModel);
                final int index = i;

                queue[i] = new Queues();

                algoBox[i].addActionListener(event -> {
                    JComboBox comboBox = (JComboBox) event.getSource();
                    Scheduler selectedAlgo = (Scheduler) comboBox.getSelectedItem();
                    queue[index].setScheduler(selectedAlgo);
                }); 

                comboBoxesList.add(algoBox[i]);
                
                spinnerBox[i].addChangeListener(event -> {
                    JSpinner source = (JSpinner) event.getSource();
                    int value = (int) source.getValue();
                    queue[index] .setAllotedTime(value);
                    queue[index].setPriority(index);

                });
                spinnerBoxesList.add(spinnerBox[i]);
                
                gbc.gridx = 0;
                gbc.gridy = i + 1; // Adjust y position based on index
                gbc.gridwidth = 2; // Take up the entire row
                btnPanel.add(algoBox[i], gbc);

                gbc.gridx = 2;
                gbc.gridy = i + 1; // Adjust y position based on index
                gbc.gridwidth = 2;
                btnPanel.add(spinnerBox[i], gbc);

                JLabel label = new JLabel("Time Quantum");
                gbc.gridx = 3;
                gbc.gridwidth = 2;
                gbc.insets = new Insets(5, 0, 5, 10); 
                btnPanel.add(label, gbc);
            }

            // Revalidate and repaint the panel
            btnPanel.revalidate();
            btnPanel.repaint();
        });

        
        gbc.gridx = 0;  
        gbc.gridy = selectedOption+6;  
        gbc.gridwidth = 2; 
        btnPanel.add(clrBtn, gbc);  

        gbc.gridx = 2;  
        gbc.gridy = selectedOption+6;   
        gbc.gridwidth = 1;  
        btnPanel.add(runBtn, gbc);  


        btnPanel.setSize(frame.getWidth(), 50);

        int paddingSize = 10; // Adjust as needed
        btnPanel.setBorder(new EmptyBorder(paddingSize, paddingSize, paddingSize, paddingSize));


        panel.add(addProcess, BorderLayout.NORTH);

        JPanel showProcess = new JPanel( new BorderLayout());
        showProcess.setBorder(BorderFactory.createTitledBorder("Show Process"));
        showProcess.setLayout(new BorderLayout());

        // Create a panel to hold the boxes with FlowLayout
        boxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 10 pixels gap

        JScrollPane scrollPaneProcess = new JScrollPane(boxPanel);
        scrollPaneProcess.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Ensure vertical scrollbar never appears

        showProcess.add(scrollPaneProcess, BorderLayout.CENTER);

        // Set preferred size for showProcess panel (optional)
        showProcess.setPreferredSize(new Dimension(400, 200)); // Adjust size as needed




        /* ============================== Run ======================================================= */

        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            /* ========================================================================================== */
                Util util = new Util();
                processes = util.createProcesses(model);
                if(model.getRowCount() == 0) {
                    util.enter_process(frame);
                    return;
                }
                System.out.println("Selected " + algoBox[0].getSelectedIndex());

                for(int i=0; i<processes.length; i++) {
                    System.out.println(processes[i].getId());
                    System.out.println(processes[i].getArrivalTime());
                    System.out.println(processes[i].getBurstTime());
                }
                /* ========================================================================================== */
                boxPanel.removeAll();
                boxPanel.revalidate();
                boxPanel.repaint();

        /* =========================================================================================== */
            }
        });


        frame.add(panel, BorderLayout.NORTH);
        frame.add(showProcess, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void addRow(DefaultTableModel model) {
        // Prompt the user to enter data for each cell of the new row
        String[] rowData = new String[model.getColumnCount()];
        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = JOptionPane.showInputDialog("Enter data for " + model.getColumnName(i));
            System.out.println("Is null? " + rowData[i]);
            while (rowData[i] == null || rowData[i].isEmpty()) {
                System.out.println("Still null " + rowData[i]);
                rowData[i] = JOptionPane.showInputDialog("Cannot be null. Enter data for " + model.getColumnName(i));
            }
        }
        // Add the new row to the table model
        model.addRow(rowData);
    }
}
