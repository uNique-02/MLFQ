import java.util.List;
import java.util.Queue;

public class MLFQ {
    private List<Process> processes;
    private List<Queues> queues;

    public MLFQ(List<Process> processes, List<Queues> queues) {
        this.processes = processes;
        this.queues = queues;
        for(int i=0; i<processes.size(); i++){
            queues.get(0).addProcess(processes.get(i));
        }
        schedule();
    }

    public void schedule() {
        int currentTime = 0;

        System.out.println("Starting MLFQ Scheduling...");
        
        while (!allQueuesEmpty()) {
            for (Queues queue : queues) {
                System.out.println("Checking Queue with Priority: " + queue.getPriority());
                
                while (!queue.processes.isEmpty()) {
                    Process currentProcess = queue.processes.poll();
                    System.out.println("Processing: " + currentProcess.getId() + " from Queue: " + queue.getPriority());

                    int timeSlice = Math.min(queue.getAllotedTime(), currentProcess.getRemainingTime());
                    System.out.println("Time Slice for Process " + currentProcess.getId() + ": " + timeSlice);
                    
                    for (int i = 0; i < timeSlice; i++) {
                        currentProcess.decrementRemainingTime();
                        currentTime++;
                        System.out.println("Current Time: " + currentTime + " | Remaining Time for Process " + currentProcess.getId() + ": " + currentProcess.getRemainingTime());

                        // Check if any new processes have arrived and need to be added to the first queue
                        for (Process process : processes) {
                            if (process.getArrivalTime() == currentTime) {
                                queues.get(0).addProcess(process);
                                System.out.println("Process " + process.getId() + " arrived and added to Queue 1");
                            }
                        }
                        if(i==timeSlice){
                            break;
                        }
                    }

                    // If the process has finished, set its completion, turnaround, and waiting times
                    if (currentProcess.getRemainingTime() == 0) {
                        currentProcess.setCompletionTime(currentTime);
                        currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                        System.out.println("Process " + currentProcess.getId() + " completed at time " + currentProcess.getCompletionTime());
                    } else {
                        // If the process hasn't finished, move it to the next lower-priority queue
                        int nextQueueIndex = Math.min(queue.getPriority(), queues.size() - 1);
                        queues.get(nextQueueIndex).addProcess(currentProcess);
                        System.out.println("Process " + currentProcess.getId() + " moved to Queue " + (nextQueueIndex + 1));
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
