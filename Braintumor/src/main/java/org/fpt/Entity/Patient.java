package org.fpt.Entity;

import java.util.UUID;

public class Patient {
    private UUID id;
    private String fullName;
    private String birthdate;
    private String gender;
    private String phone;
    private String healthInsuranceNumber;
    private String createDate;
    private String updateDate;
    private String status;

    public Patient() {
    }

    public Patient(UUID id, String fullName, String birthdate, String gender, String phone, String healthInsuranceNember, String createDate, String updateDate, String status) {
        this.id = id;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.healthInsuranceNumber = healthInsuranceNember;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHealthInsuranceNember() {
        return healthInsuranceNumber;
    }

    public void setHealthInsuranceNember(String healthInsuranceNember) {
        this.healthInsuranceNumber = healthInsuranceNember;
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
}
