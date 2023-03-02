package Project_2.server.clientHandlers;

import Project_2.server.Log;
import Project_2.server.Record;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class PatientHandler extends Handler{
    public PatientHandler(SSLSocket client, String id, String division) {
        super(client, "patient", id, division);
    }

    @Override
    protected void handleRequests(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";

        while (!clientMsg.equals("quit")) {
            clientMsg = in.readLine();
            if (clientMsg.equals("yes")) {
                try {
                    Record record = Record.readRecord("records/" + id + ".record");
                    if(record != null){
                        Date now = new Date();
                        Log.generateLog(id, "IDnbr " + id + " has read this record at timestamp: "+ now);
                        out.println(record +";"+"Press (enter) to go back!");
                    }else{
                        out.println("You do not have a medical record. (enter)");
                    }
                } catch (Exception e) {
                    System.out.print("You have no medical record :'( ");
                }

            } else {
                out.println("Do you want to read your medical record? (yes / no)");
            }
        }
    }

    @Override
    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException {
        //Not used
    }

    @Override
    public void destroyRecord(PrintWriter out, BufferedReader in) throws IOException {
        //Not used
    }

    @Override
    protected boolean checkAccess(String idRecord, Record record) {
        return false;
    }
}
