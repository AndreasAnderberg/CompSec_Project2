package Project_2.server.clientHandlers;

import javax.net.ssl.SSLSocket;

public class PatientHandler extends ClientHandler{
    public PatientHandler(SSLSocket client, String id, String division) {
        super(client, "patient", id, division);
    }
}
