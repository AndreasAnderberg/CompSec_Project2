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

        while(!idRecord.equals("back")) {
            try {
                out.println("Write idnumber for record you'd like to read: (idnumber | back)");
                idRecord = in.readLine();
                if(idRecord.equals("back")){
                    return;
                }
                Record record = Record.readRecord("records/" + idRecord + ".record");
                if (record != null) {

                    // Check access controll (patient id num)
                    checkAccess(idRecord, record);

                    Date now = new Date();
                    Log.generateLog(idRecord, "IDnbr " + id + " has read this record at timestamp: "+ now);
                    out.println(record +";"+"Press (enter) to go back!");
                    return;

                } else {
                    out.println("File does not exist!");
                }
            } catch (NullPointerException e) {
                out.println(e.getMessage());
            }
            idRecord = in.readLine();
        }
    }

    protected abstract void handleRequests(PrintWriter out, BufferedReader in) throws IOException;
    public abstract void saveRecord(PrintWriter out, BufferedReader in) throws IOException;
    public abstract void destroyRecord(PrintWriter out, BufferedReader in) throws IOException;
    protected abstract boolean checkAccess(String idRecord, Record record);
}
