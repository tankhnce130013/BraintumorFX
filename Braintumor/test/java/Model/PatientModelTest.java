package Model;

import org.fpt.DTO.PatientDTO;
import org.fpt.Model.PatientModel;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientModelTest {
    PatientModel patientModel;
    @Test
    @DisplayName("Should load information of patient correctly")
    @RepeatedTest(45)
    void testLoadPatient(){
        String stringid = "01bde757-05e5-4267-b8dd-779e1aebd4d7";
        UUID id = UUID.fromString(stringid);
        PatientDTO patient = patientModel.loadPatient(id);
        assertEquals(id, patient.getId());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should insert the information of patient successfully")
    @RepeatedTest(55)
    void testInsertPatient(){
        String fullName = "Nguyen Anh Huy";
        String dateOfBirth = "1989-12-03";
        String gender = "Female";
        String phone = "0979775412";
        String health = "CA76859376571823";
        UUID id = patientModel.insertPatient(fullName, dateOfBirth, gender, phone, health);
        PatientDTO patient = patientModel.loadPatient(id);
        assertEquals(id, patient.getId());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should update information of patient correctly")
    @RepeatedTest(60)
    void testUpdatePatient(){
        String stringid = "01bde757-05e5-4267-b8dd-779e1aebd4d7";
        UUID id = UUID.fromString(stringid);
        String fullName = "Nguyen Thi Phung";
        String dateOfBirth = "1989-12-04";
        String gender = "Female";
        String phone = "0979775412";
        String health = "CA76859376571823";
        patientModel.updatePatient(id, fullName, dateOfBirth, gender, phone, health);
        PatientDTO patient = patientModel.loadPatient(id);
        assertEquals(id, patient.getId());
        assertEquals(fullName, patient.getFullname());
        assertEquals(dateOfBirth, patient.getDateOfBirth());
        assertEquals(gender, patient.getGender());
        assertEquals(phone, patient.getPhone());
        assertEquals(health, patient.getHealthInsurance());
        System.out.println("Success");
    }
    @BeforeEach
    void setUp() {
        patientModel = new PatientModel();
    }
}