package Project_2.server;
import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.auth.x500.X500Principal;


import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

public class server implements Runnable {
  private ServerSocket serverSocket = null;
  private static int numConnectedClients = 0;
  
  public server(ServerSocket ss) throws IOException {
    serverSocket = ss;
    newListener();
  }

  public void run() {
    while (true) {
      try {
        SSLSocket socket=(SSLSocket)serverSocket.accept();

        SSLSession session = socket.getSession();
        X509Certificate cert = (X509Certificate) session.getPeerCertificates()[0];

        System.out.println(socket.getSession().getCipherSuite());

        //sparar role och id som strings, hämtar från certifikatet med cert.getSubjectX500Principal().getName().
        String subject = cert.getSubjectX500Principal().getName(X500Principal.RFC1779, new Hashtable<>());
        String[] subjectFields = subject.split(",\\s*");
        String id = subjectFields[0].substring(3);
        String role = subjectFields[1].substring(2);

        numConnectedClients++;
        System.out.println("client connected");
        System.out.println("client id (CN field): " + id);
        System.out.println("client role (O field): " + role);
        System.out.println(numConnectedClients + " concurrent connection(s)\n");

        System.out.println("creating clientHandler");
        ClientHandler clientHandler = new ClientHandler(socket, role);
        new Thread(clientHandler).start();
      } catch (IOException e) {
        System.err.println("Error in connection attempt.");
      }
    }
}


  private void newListener() {
      (new Thread(this)).start();
  } // calls run()


  public static void main(String args[]) {
    System.out.println("\nServer Started\n");
    int port = 9876;
    if (args.length >= 1) {
      port = Integer.parseInt(args[0]);
    }
    String type = "TLSv1.2";
    try {
      ServerSocketFactory ssf = getServerSocketFactory(type);
      ServerSocket ss = ssf.createServerSocket(port);
      ((SSLServerSocket)ss).setNeedClientAuth(true); // enables client authentication
      new server(ss);
    } catch (IOException e) {
      System.out.println("Unable to start Server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static ServerSocketFactory getServerSocketFactory(String type) {
    if (type.equals("TLSv1.2")) {
      SSLServerSocketFactory ssf = null;
      try { // set up key manager to perform server authentication
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        char[] password = "password".toCharArray();
        // keystore password (storepass)
        ks.load(new FileInputStream("Project_2/server/serverkeystore"), password);
        // truststore password (storepass)
        ts.load(new FileInputStream("Project_2/server/servertruststore"), password); 
        kmf.init(ks, password); // certificate password (keypass)
        tmf.init(ts);  // possible to use keystore as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ssf = ctx.getServerSocketFactory();
        return ssf;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      return ServerSocketFactory.getDefault();
    }
    return null;
  }
}


