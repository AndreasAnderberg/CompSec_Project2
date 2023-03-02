package Project_2.server.clientHandlers;

import Project_2.server.Log;
import Project_2.server.Record;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

public abstract class Handler implements Runnable{
    protected final SSLSocket client;
    protected final String role;
    protected final String id;
    protected final String division;

    public Handler  (SSLSocket client, String role, String id, String division) {
        this.client = client;
        this.role = role;
        this.id = id;
        this.division = division;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            System.out.println("Client's role: " + role);

            handleRequests(out, in);

            in.close();
            out.close();
            client.close();
            System.out.println("client disconnected");
        } catch (IOException e) {
            System.out.println("Client died: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void read(PrintWriter out, BufferedReader in) throws IOException{
        String idRecord = "init";

        while(true) {
            try {
                out.println("Write idnumber for record you'd like to read: (idnumber | back)");
                idRecord = in.readLine();
                if(idRecord.equals("back")){
                    out.println("Going back");
                    return;
                }
                Record record = Record.readRecord("records/" + idRecord + ".record");
                if (record != null) { // om recorden finns
                    if(checkAccess(record)){
                        Date now = new Date();
                        Log.generateLog(idRecord, "IDnbr " + id + " has read this record at timestamp: "+ now);
                        out.println(record +";"+"Press (enter) to go back!");
                        return;
                    }
                }
                out.println("File does not exist! (enter)");
            } catch (NullPointerException e) {
                out.println(e.getMessage());
            }
            idRecord = in.readLine();
        }
    }

    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException{
        out.println("Creating new record...;Write patient's name: ");
        String patient = in.readLine();

        if(checkAccess(Record.readRecord("records/" + patient + ".record"))){
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
        } else{
            out.println("No such record found");
        }
    }


    protected abstract void handleRequests(PrintWriter out, BufferedReader in) throws IOException;
    public abstract void destroyRecord(PrintWriter out, BufferedReader in) throws IOException;
    protected abstract boolean checkAccess(Record record);
}
