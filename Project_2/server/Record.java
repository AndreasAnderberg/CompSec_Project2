package Project_2.server;

import java.io.*;

public class Record {
  private String patientName;
  private String doctor;
  private String nurse;
  private String division;
  private String note;

  public Record(String patientName, String doctor, String nurse, String division, String note) {
    this.patientName = patientName;
    this.doctor = doctor;
    this.nurse = nurse;
    this.division = division;
    this.note = note;
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
    writer.write(patientName + ";");
    writer.write(doctor + ";");
    writer.write(nurse + ";");
    writer.write(division + ";");
    writer.write(note);
    writer.close();
  }

  public static Record readRecord(String filename) throws IOException {
    try {
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String info = bufferedReader.readLine();
        String[] infos = info.split(";");
        String patientName = infos[0];
        String doctor = infos[1];
        String nurse = infos[2];
        String division = infos[3];
        String note = infos[4];
        Record record = new Record(patientName, doctor, nurse, division, note);
        fileReader.close();
        bufferedReader.close();
        return record;

    } catch (IOException e) {
      System.out.println(e);
        return null;
    }
  }
  
  @Override
  public String toString() {
    return patientName + ";" + doctor  + ";" + nurse + ";" + division + ";" + note + ";";
  }

}

