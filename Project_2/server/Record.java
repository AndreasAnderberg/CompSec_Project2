package Project_2.server;

import java.io.*;

public class Record {
  private String username;
  private String password;
  private String role;

  public Record(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getRole() {
    return role;
  }

  public void saveToFile(String filename) throws IOException {
    File file = new File("records/" + filename);
    FileWriter writer = new FileWriter(file);
    writer.write(username + "\n");
    writer.write(password + "\n");
    writer.write(role + "\n");
    writer.close();
  }

  public static Record loadFromFile(String filename) throws IOException {
    File file = new File(filename);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String username = reader.readLine();
    String password = reader.readLine();
    String organization = reader.readLine();
    reader.close();
    return new Record(username, password, organization);
  }
}

