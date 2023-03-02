package Project_2.server;

import java.io.*;
import java.util.ArrayList;

public class Record {
  private ArrayList<Entry> entries;

  public Record(String patientName, String doctor, String nurse, String division, String note) {
    this.entries = new ArrayList<>();
    entries.add(new Entry(patientName, doctor, nurse, division, note));
  }

  public Record(ArrayList<Entry> entries){
      this.entries = entries;
  }

  public void saveToFile(String filename) throws IOException {
      FileWriter writer = new FileWriter("records/" + filename, true);
      //String content = Files.readString(Path.of("records/" + filename));
      StringBuilder sb = new StringBuilder();

      for(Entry e : entries){
          sb.append(e.getPatientName()).append(";");
          sb.append(e.getDoctor()).append(";");
          sb.append(e.getNurse()).append(";");
          sb.append(e.getDivision()).append(";");
          sb.append(e.getNote()).append("\n");
          writer.write(sb.toString());
      }

      writer.close();
  }

  public static boolean fileExists(String filename){
      File f = new File(filename);
      return f.exists();
  }


  public static Record readRecord(String filename) throws IOException {
    try {
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("file with path " + filename + " existance: " + file.exists());
            return null;
        }

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<Entry> entries = new ArrayList<>();

        String line = bufferedReader.readLine();
        while(line != null){
            System.out.println(line);
            String[] info = line.split(";");
            String patientName = info[0];
            String doctor = info[1];
            String nurse = info[2];
            String division = info[3];
            String note = info[4];
            Entry e = new Entry(patientName, doctor, nurse, division, note);
            entries.add(e);
            line = bufferedReader.readLine();
        }

        Record record = new Record(entries);
        fileReader.close();
        bufferedReader.close();
        return record;

    } catch (IOException e) {
      System.out.println(e);
        return null;
    }
  }

  public boolean hasDoctor(String doctorID){
      for (Entry entry : entries){
          if(entry.getDoctor().equals(doctorID)){
              return true;
          }
      }
      return false;
  }

  public boolean hasNurse(String nurseID){
      for (Entry entry : entries){
         if(entry.getNurse().equals(nurseID)){
             return true;
         }
      }
      return false;
  }

  public String getDivision(){
      for(Entry entry : entries){
          return entry.getDivision();
      }
      return "-1";
  }

  @Override
  public String toString() {
      StringBuilder sb = new StringBuilder();
      int i = 1;
      for (Entry e : entries){
          sb.append("----Entry nbr " + i + "----").append(";");
          sb.append(e.getPatientName() + ";" + e.getDoctor()  + ";" + e.getNurse() + ";" + e.getDivision() + ";" + e.getNote() + ";");
          i++;
      }
    return sb.toString();
  }
}

