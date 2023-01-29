

import java.util.Arrays;
import java.util.List;

public class Application {
    private static final String WORKER_ADDRESS_1 = "34.125.114.243:8081/searchipn";

    public static void main(String[] args) {
        Aggregator aggregator = new Aggregator();
        String task1 = "1757600,IPN";
        String task2 = "1757600,IPN";
        String task3 = "1757600,IPN";
        String task4 = "1757600,IPN";
        
        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1),
                Arrays.asList(task1));

        for (String result : results) {
            System.out.println(result);
        }
    }
}
