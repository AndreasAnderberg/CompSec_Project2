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

        // Check permissions based on organization
        if (role.equals("doctor")) {
            handleDoctorRequest(out, in);
        } else if (role.equals("patient")) {
            handlePatientRequest(out, in);
        } else if (role.equals("nurse")) {
            handleNurseRequest(out, in);
        } else if (role.equals("Andreas Anderberg (an8521an-s)/Thilda Holmner (ti8080ho-s)/Adam Tegelberg Hagnefors (ad3444te-s)/Andre Roxhage (an8603ro-s)")) {
            handleNurseRequest(out, in);
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

    private void handleNurseRequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        while (clientMsg != "quit") {
            clientMsg = in.readLine();
            if (clientMsg.equals("save")) {
                saveRecord(out, in);
            } else if (clientMsg.equals("read")) {
                out.println("Vilket Record vill du läsa?");
                String namn = in.readLine(); 
                Record record = Record.readRecord("records/doctorRecord");
                out.println(record.toString());
            } else {
                out.println("Välj ett kommando...");
            }
            }
        
        }
        


    private void handlePatientRequest(PrintWriter out, BufferedReader in) {
        out.println("PatientTest");
    }

    private void handleDoctorRequest(PrintWriter out, BufferedReader in) {
        out.println("DoctorTest");
        
    }

    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Skapar ett nytt record...");
        out.println("Skriv namn på patient: ");
        String patient = in.readLine();
        out.println("Skriv namn på doctor: ");
        String doctor = in.readLine();
        out.println("Skriv namn på nurse: ");
        String nurse = in.readLine();
        out.println("Skriv namn på division: ");
        String division = in.readLine();
        Record record = new Record(patient, doctor, nurse, division);
        record.saveToFile(patient + "Record");
        out.println("Record sparat");
    }

    public void read(PrintWriter out, BufferedReader in) throws IOException {
        
    }
}