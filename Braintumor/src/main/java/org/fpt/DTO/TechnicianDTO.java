package org.fpt.DTO;

import java.util.UUID;

public class TechnicianDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String birthdate;
    private String specialist;
    private String gender;
    private String ip;

    public TechnicianDTO() {
    }

    public TechnicianDTO(UUID id, String fullName, String email, String birthdate, String specialist, String gender, String ip) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.birthdate = birthdate;
        this.specialist = specialist;
        this.gender = gender;
        this.ip = ip;
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
}
