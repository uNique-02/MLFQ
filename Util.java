
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;

public class Util {

    DefaultTableModel model;

    public Util(){
        
    }
    
    public void enter_process(JFrame frame){
        JDialog dialog = new JDialog(frame, "No Process Error", true);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(frame);
        JLabel label = new JLabel("Enter process first!");
        label.setHorizontalAlignment(JLabel.CENTER);
        dialog.add(label);
        dialog.setVisible(true);
    }

    public Process[] createProcesses(DefaultTableModel model) {
        this.model = model;
        ArrayList<Process> processes = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            System.out.println("Row count: " + model.getRowCount());
            int id = Integer.parseInt((String) model.getValueAt(i, 0)); // Assuming the first column is for ID
            //int priority = Integer.parseInt((String) model.getValueAt(i, 1)); // Assuming the second column is for priority
            int arrivalTime = Integer.parseInt((String) model.getValueAt(i, 1)); // Assuming the third column is for arrival time
            int burstTime = Integer.parseInt((String) model.getValueAt(i, 2)); // Assuming the fourth column is for burst time

            Process newProcess = new Process();
            newProcess.setId(id);
            newProcess.setBurstTime(burstTime);
            newProcess.setArrivalTime(arrivalTime);
            processes.add(newProcess);
        }
        return processes.toArray(new Process[0]); // Convert ArrayList to array
    }

    public JComboBox createBox(){
        final String algos[] = {"First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Round-Robin", "Priority Scheduler", ""};

        JComboBox algoBox = new JComboBox(Scheduler.values()); 
        algoBox.setSelectedIndex(0);
        algoBox.setEditable(false);
        return algoBox;
    }
}
