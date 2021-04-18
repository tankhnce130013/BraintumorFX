package org.fpt.DTO;

import java.util.UUID;

public class HistoryDTO {
    private int id;
    private UUID eID;
    private String patientName;
    private String gender;
    private String birthDate;
    private String date;
    private String status;

    public HistoryDTO() {
    }

    public HistoryDTO(int id, UUID eID, String patientName, String gender, String birthDate, String date, String status) {
        this.id = id;
        this.eID = eID;
        this.patientName = patientName;
        this.gender = gender;
        this.birthDate = birthDate;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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
