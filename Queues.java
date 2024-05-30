import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Queues {
    private int allotedTime;
    private Scheduler scheduler;
    private int priority;

    Queue<Process> processes = new LinkedList<>();

    public void addProcess(Process process){
        processes.add(process);
    }

    public int getAllotedTime() {
        return allotedTime;
    }

    public void setAllotedTime(int allotedTime) {
        this.allotedTime = allotedTime;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
