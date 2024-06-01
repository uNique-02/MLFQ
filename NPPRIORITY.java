import java.util.Arrays;
import java.util.Comparator;

public class NPPRIORITY {
    private Process[] processes;

    public NPPRIORITY(Process[] processes) {
        this.processes = processes;
    }

    public void schedule() {
        // Sort processes by arrival time and priority
        Arrays.sort(processes, new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                if (p1.getArrivalTime() != p2.getArrivalTime()) {
                    return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                }
                return Integer.compare(p1.getPriority(), p2.getPriority());
            }
        });

        int currentTime = 0;
        for (Process process : processes) {
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }

            process.setCompletionTime(currentTime + process.getBurstTime());
            process.setTurnAroundTime(process.getCompletionTime() - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getBurstTime());

            currentTime += process.getBurstTime();
        }
    }

    public void printSchedule() {
        System.out.println("ID\tArrival\tBurst\tPriority\tCompletion\tTurnAround\tWaiting");
        for (Process process : processes) {
            System.out.println(process.getId() + "\t" + process.getArrivalTime() + "\t" + process.getBurstTime() + "\t" + process.getPriority() + "\t\t" + process.getCompletionTime() + "\t\t" + process.getTurnAroundTime() + "\t\t" + process.getWaitingTime());
        }
    }

}
