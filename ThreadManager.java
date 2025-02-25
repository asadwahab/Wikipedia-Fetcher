import utility.TaskManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    public static void main(String[] args) {
        TaskManager taskManager=new TaskManager(100);
        FileReaderRunnable National=new FileReaderRunnable("C:/Users/user/IdeaProjects/TechCodingMafia/data/file/"+"NationalAnthem.txt");
        taskManager.addTask(National);
    }

}
