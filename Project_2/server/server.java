package Project_2.server;
import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.auth.x500.X500Principal;

import java.security.KeyStore;
import java.security.cert.Certificate;
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
    try {
      SSLSocket socket=(SSLSocket)serverSocket.accept();
      newListener();
      SSLSession session = socket.getSession();
      X509Certificate cert = (X509Certificate) session.getPeerCertificates()[0];  

      //sparar role och id som strings, hämtar från certifikatet med cert.getSubjectX500Principal().getName().
      String role = cert.getSubjectX500Principal().getName(X500Principal.RFC1779, new Hashtable<>()).split(",\\s*")[0].substring(3);
      String id = cert.getSubjectX500Principal().getName(X500Principal.RFC1779, new Hashtable<>()).split(",\\s*")[0].substring(3);

      numConnectedClients++;
      System.out.println("client connected");
      System.out.println("client id (CN field): " + id);
      System.out.println("client role (O field): " + role);
      System.out.println(numConnectedClients + " concurrent connection(s)\n");

      PrintWriter out = null;
      BufferedReader in = null;
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      String clientMsg = null;
      while ((clientMsg = in.readLine()) != null) {
        if (clientMsg.equalsIgnoreCase("test")) {
          testAnswer(out);
      } else {
          String rev = new StringBuilder(clientMsg).reverse().toString();
          System.out.println("received '" + clientMsg + "' from client");
          System.out.print("sending '" + rev + "' to client...");
          out.println(rev);
          out.flush();
          System.out.println("done\n"); 
      }
      }
      in.close();
      out.close();
      socket.close();
      numConnectedClients--;
      System.out.println("client disconnected");
      System.out.println(numConnectedClients + " concurrent connection(s)\n");
    } catch (IOException e) {
      System.out.println("Client died: " + e.getMessage());
      e.printStackTrace();
      return;
    }
  }
  private void testAnswer(PrintWriter out) {
    out.println("testtest");
    out.flush();
}
  
  private void newListener() { (new Thread(this)).start(); } // calls run()
  public static void main(String args[]) {
    System.out.println("\nServer Started\n");
    int port = 9876;

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
        ks.load(new FileInputStream("serverkeystore"), password);  
        // truststore password (storepass)
        ts.load(new FileInputStream("servertruststore"), password); 
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
