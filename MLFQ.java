import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.LinkedList;

public class MLFQ {
    private List<Process> processes;
    private List<Queues> queues;

    JPanel boxPanel;

    public MLFQ(List<Process> processes, List<Queues> queues, JPanel boxPanel) {
        this.boxPanel = boxPanel;
        this.processes = processes;
        this.queues = queues;
        for (Process process : processes) {
            queues.get(0).addProcess(process);
        }
        schedule();
    }

    public void schedule() {
        int currentTime = 0;

        System.out.println("Starting MLFQ Scheduling...");
        
        while (!allQueuesEmpty()) {
            for (Queues queue : queues) {
                System.out.println("Checking Queue with Priority: " + queue.getPriority());
                
                Queue<Process> currentQueueProcesses = new LinkedList<>(queue.processes);

                while (!currentQueueProcesses.isEmpty()) {
                    Process currentProcess = currentQueueProcesses.poll();
                    queue.removeProcess(currentProcess); // Remove from the current queue
                    System.out.println("Processing: " + currentProcess.getId() + " from Queue: " + queue.getPriority());

                    int timeSlice = Math.min(queue.getAllotedTime(), currentProcess.getRemainingTime());
                    System.out.println("Time Slice for Process " + currentProcess.getId() + ": " + timeSlice);
                    
                    for (int i = 0; i < timeSlice; i++) {
                        currentProcess.decrementRemainingTime();
                        currentTime++;
                        System.out.println("Current Time: " + currentTime + " | Remaining Time for Process " + currentProcess.getId() + ": " + currentProcess.getRemainingTime());

                        Random random = new Random();
                        Color color = color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

                        // Add a 1-second delay
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println("Thread was interrupted, failed to complete operation");
                        }

                        JPanel box = new JPanel();
                        JLabel label = new JLabel("" + (currentProcess.getId()));
                        box.setLayout(new BorderLayout());
                        box.add(label, BorderLayout.CENTER);
                        box.setPreferredSize(new Dimension(12, 30));
                        box.setBorder(BorderFactory.createLineBorder(color));
                        box.setBackground(color);
                        boxPanel.add(box); // Add the box to the panel
                        boxPanel.revalidate(); // Revalidate the panel to reflect the changes

                        // Check if any new processes have arrived and need to be added to the first queue
                        for (Process process : processes) {
                            if (process.getArrivalTime() == currentTime) {
                                queues.get(0).addProcess(process);
                                System.out.println("Process " + process.getId() + " arrived and added to Queue 1");
                            }
                        }

                        // Exit loop early if process is complete
                        if (currentProcess.getRemainingTime() == 0) {
                            break;
                        }
                    }

                    // If the process has finished, set its completion, turnaround, and waiting times
                    if (currentProcess.getRemainingTime() == 0) {
                        currentProcess.setCompletionTime(currentTime);
                        currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                        queue.removeProcess(currentProcess);
                        System.out.println("Process " + currentProcess.getId() + " completed at time " + currentProcess.getCompletionTime());
                    } else {
                        // If the process hasn't finished, move it to the next lower-priority queue
                        int nextQueueIndex = queue.getPriority();
                        if (nextQueueIndex + 1 < queues.size()) {
                            queues.get(nextQueueIndex + 1).addProcess(currentProcess);
                            System.out.println("Process " + currentProcess.getId() + " moved to Queue " + (nextQueueIndex + 1));
                        } else {
                            queue.addProcess(currentProcess);
                            System.out.println("Process " + currentProcess.getId() + " re-queued in the same Queue " + queue.getPriority());
                        }
                    }
                }
            }
        }

        printSchedule();
    }

    private boolean allQueuesEmpty() {
        for (Queues queue : queues) {
            if (!queue.processes.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void printSchedule() {
        System.out.println("\nFinal Schedule:");
        System.out.println("ID\tArrival\tBurst\tCompletion\tTurnAround\tWaiting");
        for (Process process : processes) {
            System.out.println(process.getId() + "\t" + process.getArrivalTime() + "\t" + process.getBurstTime() + "\t" + process.getCompletionTime() + "\t\t" + process.getTurnAroundTime() + "\t\t" + process.getWaitingTime());
        }
    }
}
