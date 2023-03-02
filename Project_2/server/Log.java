package Project_2.server;

import java.io.FileWriter;
import java.io.IOException;

public class Log {
    public static void generateLog(String patientName, String message) throws IOException {
        FileWriter writer = new FileWriter("logs/" + patientName + ".log", true);
        System.out.println("Logging message: " + message);
        writer.write(message + "\n");
        writer.close();
    }
}
