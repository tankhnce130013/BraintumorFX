package org.fpt.Entity;

import java.util.UUID;

public class Doctor {
    private UUID id;
    private String fullName;
    private String email;
    private String birthdate;
    private String specialist;
    private String gender;
    private String ip;
    private String createDate;
    private String updateDate;
    private String status;

    public Doctor() {
    }

    public Doctor(UUID id, String fullName, String email, String birthdate, String specialist, String gender, String ip, String createDate, String updateDate, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.birthdate = birthdate;
        this.specialist = specialist;
        this.gender = gender;
        this.ip = ip;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", specialist='" + specialist + '\'' +
                ", gender='" + gender + '\'' +
                ", ip='" + ip + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
