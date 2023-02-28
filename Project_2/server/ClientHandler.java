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
        System.out.println("Client's role: " + role);

        // Check permissions based on organization
        if (role.equals("doctor")) {
            handleDoctorRequest(out, in);
        } else if (role.equals("patient")) {
            handlePatientRequest(out, in);
        } else if (role.equals("nurse")) {
            handleNurseRequest(out, in);
        } else if (role.equals("ga")) {
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
                read(out, in);
            } else {
                out.println("Välj ett kommando...");
            }
        }

        }



    private void handlePatientRequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        
        while (clientMsg != "quit") {
            out.println("Do you want to read your medical record? (yes / no)");
            clientMsg = in.readLine();            

            if(clientMsg == "yes") {
                read(out, in);
            }
        }
    }

    private void handleDoctorRequest(PrintWriter out, BufferedReader in) {
        out.println("DoctorTest");

    }

    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Skapar ett nytt record...");
        out.println("Skriv namn på patient: ");
        String patient = in.readLine();
        System.out.println("patientnamn_DEBUG:" + patient);
        out.println("Skriv namn på doctor: ");
        String doctor = in.readLine();
        out.println("Skriv namn på nurse: ");
        String nurse = in.readLine();
        out.println("Skriv namn på division: ");
        String division = in.readLine();
        out.println("Skriv en notering: ");
        String note = in.readLine();
        Record record = new Record(patient, doctor, nurse, division, note);
        record.saveToFile(patient + "Record");
        out.println("Record sparad");
    }

    public void read(PrintWriter out, BufferedReader in) throws IOException {
        try {
            out.println("Vilket Record (namn) vill du läsa?");
            String namn = in.readLine();
            Record record = Record.readRecord("records/" + namn);
            if (record != null) {
                out.println(record.toString());
            } else {
                out.println("Filen existerar inte");
                read(out, in);
            }
        } catch (NullPointerException e) {
            out.println(e.getMessage());
        }
    }
}