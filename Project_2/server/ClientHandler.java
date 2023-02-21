package Project_2.server;
import java.io.*;
import javax.net.ssl.*;



public class ClientHandler implements Runnable {
  
    private final SSLSocket client;
    private final String role;

    public ClientHandler(SSLSocket client, String role) {
        this.client = client;
        this.role = role;
    }

  public void run() {
    try {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        // Read client message
        String clientMsg = in.readLine();
        System.out.println("Received message from client: " + clientMsg);

        System.out.println("Client's organization: " + role);

        // Check permissions based on organization
        if (role.equals("doctor")) {
            handleDoctorRequest(clientMsg, out);
        } else if (role.equals("patient")) {
            handlePatientRequest(clientMsg, out);
        } else if (role.equals("nurse")) {
            handleNurseRequest(clientMsg, out);
        } else {
            out.println("Error: unknown user");
        }
        in.close();
        out.close();
        client.close();
        System.out.println("client disconnected");
    } catch (IOException e) {
        System.out.println("Client died: " + e.getMessage());
        e.printStackTrace();
        return;
    }
}

    private void handleNurseRequest(String clientMsg, PrintWriter out) {
        System.out.println("NurseTest");
}

    private void handlePatientRequest(String clientMsg,PrintWriter out) {
        System.out.println("PatientTest");
}

    private void handleDoctorRequest(String s, PrintWriter pw) {
        System.out.println("DoctorTest");
        
    }
}
