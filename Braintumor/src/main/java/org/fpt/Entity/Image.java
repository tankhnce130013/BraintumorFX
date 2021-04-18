package org.fpt.Entity;

import java.util.Base64;
import java.util.UUID;

public class Image {
    private UUID id;
    private byte[] imageData;
    private UUID idPatient;
    private UUID idDoctor;
    private UUID idTask;
    private String typeOfTumor;
    private String confirmDate;
    private String status;

    public Image() {
    }

    public Image(UUID id, byte[] imageData, UUID idPatient, UUID idDoctor, UUID idTask, String typeOfTumor, String confirmDate, String status) {
        this.id = id;
        this.imageData = imageData;
        this.idPatient = idPatient;
        this.idDoctor = idDoctor;
        this.idTask = idTask;
        this.typeOfTumor = typeOfTumor;
        this.confirmDate = confirmDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public UUID getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(UUID idPatient) {
        this.idPatient = idPatient;
    }

    public UUID getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(UUID idDoctor) {
        this.idDoctor = idDoctor;
    }

    public UUID getIdTask() {
        return idTask;
    }

    public void setIdTask(UUID idTask) {
        this.idTask = idTask;
    }

    public String getTypeOfTumor() {
        return typeOfTumor;
    }

    public void setTypeOfTumor(String typeOfTumor) {
        this.typeOfTumor = typeOfTumor;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
