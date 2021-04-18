package org.fpt.Entity;

import java.util.UUID;

public class Task {
    private UUID id;
    private UUID idPatient;
    private UUID idTechnician;
    private UUID idDoctor;
    private String folderName;
    private String uploadDate;
    private String predictDate;
    private String uploadStatus;
    private String predictStatus;
    private String result;
    private String note;
    private String status;
    public Task() {
    }

    public Task(UUID id, UUID idPatient, UUID idTechnician, UUID idDoctor, String folderName, String uploadDate, String predictDate, String uploadStatus, String predictStatus, String result, String note, String status) {
        this.id = id;
        this.idPatient = idPatient;
        this.idTechnician = idTechnician;
        this.idDoctor = idDoctor;
        this.folderName = folderName;
        this.uploadDate = uploadDate;
        this.predictDate = predictDate;
        this.uploadStatus = uploadStatus;
        this.predictStatus = predictStatus;
        this.result = result;
        this.note = note;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(UUID idPatient) {
        this.idPatient = idPatient;
    }

    public UUID getIdTechnician() {
        return idTechnician;
    }

    public void setIdTechnician(UUID idTechniciant) {
        this.idTechnician = idTechniciant;
    }

    public UUID getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(UUID idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPredictDate() {
        return predictDate;
    }

    public void setPredictDate(String predictDate) {
        this.predictDate = predictDate;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getPredictStatus() {
        return predictStatus;
    }

    public void setPredictStatus(String predictStatus) {
        this.predictStatus = predictStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
