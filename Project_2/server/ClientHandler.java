package Project_2.server;
import java.io.*;
import java.util.Date;
import javax.net.ssl.*;



public class ClientHandler implements Runnable {
  
    private final SSLSocket client;
    private final String role;
    private final String id;

    public ClientHandler(SSLSocket client, String role, String id) {
        this.client = client;
        this.role = role;
        this.id = id;
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
            handleGARequest(out, in);
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
    }
  }

    private void handleNurseRequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        while (!clientMsg.equals("quit")) {
            clientMsg = in.readLine();

            if (clientMsg.equals("read")) {
                read(out, in);
            } else {
                out.println("Choose a command: (read | quit)");
            }
        }
    }

    private void handlePatientRequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        
        while (!clientMsg.equals("quit")) {
            clientMsg = in.readLine();
            if (clientMsg.equals("yes")) {
                read(out, in);
            } else {
                out.println("Do you want to read your medical record? (yes / no)");
            }
        }
    }

    private void handleDoctorRequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        while (!clientMsg.equals("quit")) {
            clientMsg = in.readLine();
            if (clientMsg.equals("save")) {
                saveRecord(out, in);
            } else if (clientMsg.equals("read")) {
                read(out, in);
            } else {
                out.println("Choose a command: (read | save | quit)");
            }
        }
    }

    private void handleGARequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        while (!clientMsg.equals("quit")) {
            clientMsg = in.readLine();
            if (clientMsg.equals("save")) {
                saveRecord(out, in);
            } else if (clientMsg.equals("read")) {
                read(out, in);
            } else if(clientMsg.equals("destroy")){
                destroyRecord(out, in);
            }else {
                out.println("Choose a command: (read | save | destroy | quit)");
            }
        }
    }


    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Creating new record...;Write patient's name: ");
        String patient = in.readLine();
        out.println("Write doctor's name: ");
        String doctor = in.readLine();
        out.println("Write nurse's name: ");
        String nurse = in.readLine();
        out.println("Write division's name: ");
        String division = in.readLine();
        out.println("Write a note: ");
        String note = in.readLine();
        Record record = new Record(patient, doctor, nurse, division, note);
        Date now = new Date();
        Log.generateLog(patient, "IDnbr " + id + " has added an entry to this record at timestamp: "+ now);
        record.saveToFile(patient + "Record");
        out.println("Record saved");
    }

    public void read(PrintWriter out, BufferedReader in) throws IOException {
        try {
            out.println("Who's record do you want to read?");
            String patient = in.readLine();
            Record record = Record.readRecord("records/" + patient + "Record");
            if (record != null) {
                Date now = new Date();
                Log.generateLog(patient, "IDnbr " + id + " has read this record at timestamp: "+ now);
                out.println(record +";"+"Press (enter) to go back!");

            } else {
                out.println("File does not exist!");
                read(out, in);
            }
        } catch (NullPointerException e) {
            out.println(e.getMessage());
        }
    }

    public void destroyRecord(PrintWriter out, BufferedReader in) throws IOException {
        // Date now = new Date();
        //Log.generateLog(patient, "IDnbr " + id + "has destroyed this record at timestamp: "+ now);
        //TODO
    }
}