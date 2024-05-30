import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = Arrays.asList(
                new Process(1, 0, 10, 1),
                new Process(2, 2, 5, 1),
                new Process(3, 4, 2, 1)
        );

        ArrayList<Queues> queues = new ArrayList<>(3);

        for(int i=0; i<3; i++){
            queues.add(new Queues());
            queues.get(i).priority = i+1;
            queues.get(i).allotedTime = (int) Math.pow(2, i+1);
            queues.get(i).scheduler = Scheduler.FCFS;
        }

        new MLFQ(processes, queues);
    }
}
