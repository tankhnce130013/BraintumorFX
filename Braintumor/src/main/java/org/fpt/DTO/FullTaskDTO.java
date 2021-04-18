package org.fpt.DTO;

import java.util.UUID;

public class FullTaskDTO {
    private UUID id;
    private UUID idPatient;
    private UUID idTechnician;
    private UUID doctorID;
    private String folderName;
    private String note;

    public FullTaskDTO() {
    }

    public FullTaskDTO(UUID id, UUID idPatient, UUID idTechnician, UUID doctorID, String folderName, String note) {
        this.id = id;
        this.idPatient = idPatient;
        this.idTechnician = idTechnician;
        this.doctorID = doctorID;
        this.folderName = folderName;
        this.note = note;
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

    public void setIdTechnician(UUID idTechnician) {
        this.idTechnician = idTechnician;
    }

    public UUID getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(UUID doctorID) {
        this.doctorID = doctorID;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
