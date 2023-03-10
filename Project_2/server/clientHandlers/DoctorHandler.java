package Project_2.server.clientHandlers;

import Project_2.server.Log;
import Project_2.server.Record;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class DoctorHandler extends Handler {
    public DoctorHandler(SSLSocket client, String id, String division) {
        super(client, "doctor", id, division);
    }

    @Override
    protected String[] getAssociate(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Write nurse's id: ");
        String nurse = in.readLine();
        return new String[]{id, nurse};
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
    public void destroyRecord(PrintWriter out, BufferedReader in) throws IOException {
        //Not used
    }

    @Override
    protected boolean checkAccess(Record record) {
        if(record == null){
            return true;
        }
        return record.hasDoctor(id) || record.getDivision().equals(division);
    }
}
