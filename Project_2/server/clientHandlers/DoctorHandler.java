package Project_2.server.clientHandlers;

import javax.net.ssl.SSLSocket;

public class DoctorHandler extends ClientHandler {
    public DoctorHandler(SSLSocket client, String id, String division) {
        super(client, "doctor", id, division);
    }
}
