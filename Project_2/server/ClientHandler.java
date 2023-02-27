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
        System.out.println("Client's organization: " + role);
        String clientMsg = in.readLine();
        while(!clientMsg.equals("quit")) {

            // Check permissions based on organization
            if (role.equals("doctor")) {
                handleDoctorRequest(clientMsg, out, in);
            } else if (role.equals("patient")) {
                handlePatientRequest(clientMsg, out, in);
            } else if (role.equals("nurse")) {
                handleNurseRequest(clientMsg, out, in);
            } else if (role.equals("Andreas Anderberg (an8521an-s)/Thilda Holmner (ti8080ho-s)/Adam Tegelberg Hagnefors (ad3444te-s)/Andre Roxhage (an8603ro-s)")) {
                handleNurseRequest(clientMsg, out, in);
            } else {
                out.println("Error: unknown user");
            }

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

    private void handleNurseRequest(String clientMsg, PrintWriter out, BufferedReader in) throws IOException {
        out.println("skriv något: ");
        String input = in.readLine();
        out.println(input);
}

    private void handlePatientRequest(String clientMsg,PrintWriter out, BufferedReader in) {
        out.println("PatientTest");
}

    private void handleDoctorRequest(String s, PrintWriter out, BufferedReader in) {
        out.println("DoctorTest");
        
    }
}