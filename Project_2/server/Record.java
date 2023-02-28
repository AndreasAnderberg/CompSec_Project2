package Project_2.server;

import java.io.*;

public class Record {
  private String patientName;
  private String doctor;
  private String nurse;
  private String division;

  public Record(String patientName, String doctor, String nurse, String division) {
    this.patientName = patientName;
    this.doctor = doctor;
    this.nurse = nurse;
    this.division = division;
  }

  public String getPatientName() {
    return patientName;
  }

  public String getDoctor() {
    return doctor;
  }

  public String getNurse() {
    return nurse;
  }

  public void saveToFile(String filename) throws IOException {
    File file = new File("records/" + filename);
    FileWriter writer = new FileWriter(file);
    writer.write(patientName + "\n");
    writer.write(doctor + "\n");
    writer.write(nurse + "\n");
    writer.write(division + "\n");
    writer.close();
  }

  public static Record loadFromFile(String filename) throws IOException {
    File file = new File(filename);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String patientName = reader.readLine();
    String doctor = reader.readLine();
    String nurse = reader.readLine();
    String division = reader.readLine();
    reader.close();
    return new Record(patientName, doctor, nurse, division);
  }

  public static Record readRecord(String filename) throws IOException {
    try {
        System.out.println("test222");
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String patientName = bufferedReader.readLine();
        String doctor = bufferedReader.readLine();
        String nurse = bufferedReader.readLine();
        String division = bufferedReader.readLine();
        Record record = new Record(patientName, doctor, nurse, division);
        fileReader.close();
        bufferedReader.close();
        return record;

    } catch (IOException e) {
        return null;
    }
  }
  
  @Override
  public String toString() {
    return "patient: " + this.patientName + "doctor: " + this.doctor + "\n" + "nurse: " + this.nurse + "\n" + "division: " + this.division + "\n";
  }

}

