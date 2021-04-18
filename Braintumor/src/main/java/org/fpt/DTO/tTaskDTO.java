package org.fpt.DTO;

import java.util.UUID;

public class tTaskDTO {
    private int id;
    private UUID eID;
    private String patientName;
    private String doctorName;
    private String date;
    private String status;

    public tTaskDTO() {
    }

    public tTaskDTO(int id, UUID eID, String patientName, String doctorName, String date, String status) {
        this.id = id;
        this.eID = eID;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID geteID() {
        return eID;
    }

    public void seteID(UUID eID) {
        this.eID = eID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
