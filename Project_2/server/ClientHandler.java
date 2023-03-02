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
                out.println("Choose a command: (read save quit)");
            }
        }

        }



    private void handlePatientRequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        
        while (clientMsg != "quit") {
            clientMsg = in.readLine();
            if (clientMsg.equals("yes")) {
                read(out, in);
            } else {
                out.println("Do you want to read your medical record? (yes / no)");
            }
        }
    }

    private void handleDoctorRequest(PrintWriter out, BufferedReader in) {
        out.println("DoctorTest");

    }

    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Skapar ett nytt record...;Skriv namn på patient: ");
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
<<<<<<< HEAD
        record.saveToFile(patient + "Record");
        out.println("Record sparad");
=======
        Date now = new Date();
        Log.generateLog(patient, "IDnbr " + id + " has added an entry to this record at timestamp: "+ now);
        record.saveToFile(patient + ".record");
        out.println("Record saved");
>>>>>>> 2ca07bc608638374de38b6f804a0caed0db72a00
    }

    public void read(PrintWriter out, BufferedReader in) throws IOException {
        try {
            out.println("Who's record do you want to read?");
<<<<<<< HEAD
            String namn = in.readLine();
            Record record = Record.readRecord("records/" + namn + "Record");
=======
            String patient = in.readLine();
            Record record = Record.readRecord("records/" + patient + ".record");
>>>>>>> 2ca07bc608638374de38b6f804a0caed0db72a00
            if (record != null) {
                out.println(record.toString() +";"+"Press (enter) to go back!");

            } else {
                out.println("File does not exist!");
                read(out, in);
            }
        } catch (NullPointerException e) {
            out.println(e.getMessage());
        }
    }
<<<<<<< HEAD
=======

    public void destroyRecord(PrintWriter out, BufferedReader in) throws IOException {
        try{
            out.println("Who's record do you want to destroy?");
            String patient = in.readLine();

            File file = new File("Records/" + patient + ".record");
            if(file.delete()){
                System.out.println("Records of " + patient + " was deleted successfully");
                out.println("Record deleted;Press (enter) to go back!");
                Date now = new Date();
                Log.generateLog(patient, "IDnbr " + id + "has destroyed this record at timestamp: "+ now);
            } else{
                System.out.println("Failure in deletion of records of " + patient);
                out.println("Failure in deletion;Press (enter) to go back!");
                Date now = new Date();
                Log.generateLog(patient, "IDnbr " + id + "tried to destroy this record at timestamp: "+ now);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
>>>>>>> 2ca07bc608638374de38b6f804a0caed0db72a00
}