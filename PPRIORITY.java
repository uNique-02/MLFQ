import java.util.Arrays;
import java.util.Comparator;

public class PPRIORITY {
    private Process[] processes;

    public PPRIORITY(Process[] processes) {
        this.processes = processes;
    }

    public void schedule() {
        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.length;
        Process currentProcess = null;

        while (completedProcesses < n) {
            // Sort processes by remaining time and priority
            Arrays.sort(processes, Comparator.comparingInt(Process::getPriority)
                                             .thenComparingInt(Process::getArrivalTime));

            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0) {
                    currentProcess = process;
                    currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                    if (currentProcess.getRemainingTime() == 0) {
                        currentProcess.setCompletionTime(currentTime + 1);
                        currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                        completedProcesses++;
                        currentProcess = null;
                    }
                    break; // Process only one at a time
                }
            }
            currentTime++;
        }
    }

    public void printSchedule() {
        System.out.println("ID\tArrival\tBurst\tPriority\tCompletion\tTurnAround\tWaiting");
        for (Process process : processes) {
            System.out.println(process.getId() + "\t" + process.getArrivalTime() + "\t" +
                               process.getBurstTime() + "\t" + process.getPriority() + "\t\t" +
                               process.getCompletionTime() + "\t\t" + process.getTurnAroundTime() + "\t\t" +
                               process.getWaitingTime());
        }
    }

}
