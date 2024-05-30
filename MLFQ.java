import java.util.ArrayList;
import java.util.List;

public class MLFQ {
    ArrayList<Queues> queues; //declare array of queues

    MLFQ(List<Process> processes, ArrayList<Queues> queues) {
        this.queues = queues;
        for(int i=0; i<processes.size()-1; i++) {
            queues.add(new Queues()); // Add a new Queues object to the ArrayList
            queues.get(0).addProcess(processes.get(i));
        }

        scheduler();
    }

    public void scheduler(){
        int queueCounter = 0;

        while(queueCounter!=3){
            while(!queues.get(queueCounter).processes.isEmpty()){
                
            } 
        }
    }
}
