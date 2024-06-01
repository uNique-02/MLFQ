import java.util.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.*;

public class Main {

    static JFrame frame;
    static JPanel showProcess;
    static JPanel boxPanel;
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
        queues.get(0).setScheduler(Scheduler.SRTF);
        
        new MLFQ(processes, queues, boxPanel);
    }

    static void initComponents(){

        frame = new JFrame("MLFQ Scheduling");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        showProcess = new JPanel( new BorderLayout());
        showProcess.setBorder(BorderFactory.createTitledBorder("Show Process"));
        showProcess.setLayout(new BorderLayout());
        showProcess.setPreferredSize(new Dimension(400, 500));

        // Create a panel to hold the boxes with FlowLayout
        boxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 10 pixels gap
        boxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 10 pixels padding

        JScrollPane scrollPaneProcess = new JScrollPane(boxPanel);
        scrollPaneProcess.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Ensure vertical scrollbar never appears

        showProcess.add(scrollPaneProcess, BorderLayout.CENTER);

        // Set preferred size for showProcess panel (optional)
        showProcess.setPreferredSize(new Dimension(400, 200)); // Adjust size as needed

        frame.add(showProcess, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
}
