package Project_2.server.clientHandlers;

import Project_2.server.Log;
import Project_2.server.Record;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class NurseHandler extends Handler{
    public NurseHandler(SSLSocket client, String id, String division) {
        super(client, "nurse", id, division);
    }

    @Override
    protected void handleRequests(PrintWriter out, BufferedReader in) throws IOException {
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

    @Override
    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Creating new record...;Write patient's name: ");
        String patient = in.readLine();

        if(Record.fileExists("records/" + patient + ".record")){
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
            out.println("Access denied or unknown patient");
        }
    }

    @Override
    public void read(PrintWriter out, BufferedReader in) throws IOException {
        String idRecord = "init";

        while(!idRecord.equals("back")) {
            try {
                out.println("Write idnumber for record you'd like to read: (idnumber | back)");
                idRecord = in.readLine();
                Record record = Record.readRecord("records/" + idRecord + ".record");
                if (record != null) {

                    // Check access controll (patient id num)
                    checkAccess(idRecord, record);

                    Date now = new Date();
                    Log.generateLog(idRecord, "IDnbr " + id + " has read this record at timestamp: "+ now);
                    out.println(record +";"+"Press (enter) to go back!");
                    return;

                } else {
                    if(idRecord.equals("back")){
                        return;
                    }
                    out.println("File does not exist!");
                }
            } catch (NullPointerException e) {
                out.println(e.getMessage());
            }
            idRecord = in.readLine();
        }
    }

    @Override
    public void destroyRecord(PrintWriter out, BufferedReader in) throws IOException {
        //Not used
    }

    @Override
    protected boolean checkAccess(String idRecord, Record record) {
        return true;
    }
}
