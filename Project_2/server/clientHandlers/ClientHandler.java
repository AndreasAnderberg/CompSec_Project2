package Project_2.server.clientHandlers;
import Project_2.server.Log;
import Project_2.server.Record;

import java.io.*;
import java.util.Date;
import javax.net.ssl.*;



public class ClientHandler implements Runnable {
  
    private final SSLSocket client;
    private final String role;
    private final String id;
    private final String division;

    public ClientHandler(SSLSocket client, String role, String id, String division) {
        this.client = client;
        this.role = role;
        this.id = id;
        this.division = division;
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

            if (clientMsg.equals("save")) {
                saveRecord(out, in);
            } else if (clientMsg.equals("read")) {
                read(out, in);
            } else {
                out.println("Choose a command: (read | save | quit)");
            }
        }
    }

    private void handlePatientRequest(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        
        while (!clientMsg.equals("quit")) {
            clientMsg = in.readLine();
            if (clientMsg.equals("yes")) {
                try {
                    Record record = Record.readRecord("records/" + id + ".record");
                    Date now = new Date();
                    Log.generateLog(id, "IDnbr " + id + " has read this record at timestamp: "+ now);
                    out.println(record +";"+"Press (enter) to go back!");
                } catch (Exception e) {
                    System.out.print("You have no medical record :'( ");
                }
                
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

            if (clientMsg.equals("read")) {
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

        if(role.equals("doctor")){
            writeRecord(patient, out, in);
        } else if (role.equals("nurse") && Record.fileExists("records/" + patient + ".record")){
            writeRecord(patient, out, in);
        } else {
        out.println("Access denied or unknown patient");
        }
    }
    private void writeRecord(String patient, PrintWriter out, BufferedReader in) throws IOException {
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
        record.saveToFile(patient + ".record");
        out.println("Record saved");
    }

    public void read(PrintWriter out, BufferedReader in) throws IOException {
        while(!in.readLine().equals("back")) {
            try {
                out.println("Write idnumber for record you'd like to read: (idnumber | back)");
                String idRecord = in.readLine();
                Record record = Record.readRecord("records/" + idRecord + ".record");
                if (record != null) {
    
                    // Check access controll (patient id num)
                    checkAccess(idRecord, record);
    
                    Date now = new Date();
                    Log.generateLog(idRecord, "IDnbr " + id + " has read this record at timestamp: "+ now);
                    out.println(record +";"+"Press (enter) to go back!");
    
                } else {
                    out.println("File does not exist!");
                }
            } catch (NullPointerException e) {
                out.println(e.getMessage());
            }
        }
        
    }

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
    
    private boolean checkAccess(String idRecord, Record record) {
        // Patient: same id as 
        String[] words = record.toString().split(";");


        // Nurse: only for same division


        // Doctor: only for same division

        boolean acCtrl = false;




        return acCtrl;
    }
}