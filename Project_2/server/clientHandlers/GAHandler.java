package Project_2.server.clientHandlers;

import Project_2.server.Log;
import Project_2.server.Record;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class GAHandler extends Handler{
    public GAHandler(SSLSocket client, String id, String division) {
        super(client, "ga", id, division);
    }

    @Override
    protected void handleRequests(PrintWriter out, BufferedReader in) throws IOException {
        String clientMsg = "";
        while (!clientMsg.equals("quit")) {
            clientMsg = in.readLine();

            if (clientMsg.equals("read")) {
                read(out, in);
            } else if(clientMsg.equals("destroy")){
                destroyRecord(out, in);
            }else {
                out.println("Choose a command: (read | destroy | quit)");
            }
        }
    }

    @Override
    public void saveRecord(PrintWriter out, BufferedReader in) throws IOException {
        //Not used
    }

    @Override
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

    @Override
    protected boolean checkAccess(String idRecord, Record record) {
        return true;
    }
}
