package Project_2.server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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
      FileWriter writer = new FileWriter("records/" + filename, true);
      String content = Files.readString(Path.of("records/" + filename));
      StringBuilder sb = new StringBuilder(content);

      sb.append(patientName).append(";");
      sb.append(doctor).append(";");
      sb.append(nurse).append(";");
      sb.append(division).append(";");
      sb.append(note);
      sb.append("\n");
      writer.write(sb.toString());

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

