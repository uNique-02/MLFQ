import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Queues {
    int allotedTime;
    Scheduler scheduler;
    int priority;
    Queue<Process> processes = new LinkedList<>();

    public void addProcess(Process process){
        processes.add(process);
    }
}
