package Project_2.server;

public class Entry {
        private String patientName;
        private String doctor;
        private String nurse;
        private String division;
        private String note;

        public Entry  (String patientName, String doctor, String nurse, String division, String note){
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
    public String getDivision() {
        return division;
    }
    public String getNote() {
        return note;
    }
}