package Model;

import org.fpt.Model.DoctorModel;
import org.fpt.Entity.Doctor;
import org.fpt.DTO.DoctorDTO;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoctorModelTest {
    DoctorModel doctorModel;
    @Test
    @DisplayName("Should convert an Entity Doctor to a DTO Doctor")
    @RepeatedTest(40)
    void testConvertDoctor() {
        UUID id = UUID.randomUUID();
        Doctor doctor01 = new Doctor(id, "Nguyen Van Hung", "hung@fpt.edu.vn", "19990913", "Brain MRI Analysis", "Male", "192168011", "19990913", "19990913", "Active");
        DoctorDTO doctor02 = new DoctorDTO(id, "Nguyen Van Hung", "hung@fpt.edu.vn", "19990913", "Brain MRI Analysis", "Male", "192168011");
        DoctorDTO doctor03 = doctorModel.convertDoctorEntityToDTO(doctor01);
        assertEquals(doctor02.getId(), doctor03.getId());
        assertEquals(doctor02.getFullName(), doctor03.getFullName());
        assertEquals(doctor02.getEmail(), doctor03.getEmail());
        assertEquals(doctor02.getBirthdate(), doctor03.getBirthdate());
        assertEquals(doctor02.getSpecialist(), doctor03.getSpecialist());
        assertEquals(doctor02.getGender(), doctor03.getGender());
        assertEquals(doctor02.getIp(), doctor03.getIp());
        System.out.println("Success");
    }

    @Test
    @DisplayName("Should load information of doctor from database to DTO Doctor")
    @RepeatedTest(40)
    void testLoadDoctor() {
        String email01 = "hungndce130309@fpt.edu.vn";
        DoctorDTO doctor = doctorModel.loadDoctor(email01);
        assertEquals(email01, doctor.getEmail());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load information of doctor from database to Entity Doctor")
    @RepeatedTest(35)
    void testLoadEntityDoctor() {
        String email01 = "hungndce130309@fpt.edu.vn";
        Doctor doctor = doctorModel.loadEntityDoctor(email01);
        assertEquals(email01, doctor.getEmail());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load a list of doctors those are online")
    @RepeatedTest(50)
    void testLoadDoctorOnline(){
        String email01 = "tuan@fpt.edu.vn";
        ArrayList<DoctorDTO> listDoctor = doctorModel.loadDoctorOnline();
        Doctor doctor = doctorModel.loadEntityDoctor(listDoctor.get(0).getEmail());
        assertEquals("Online", doctor.getStatus());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should change the status of doctor in database to online")
    @RepeatedTest(55)
    void testToggleDoctorOnline(){
        String email01 = "hungndce130309@fpt.edu.vn";
        doctorModel.toggleDoctorOnline(email01);
        Doctor doctor = doctorModel.loadEntityDoctor(email01);
        assertEquals("Online", doctor.getStatus());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should change the status of doctor in database to offline")
    @RepeatedTest(55)
    void testToggleDoctorOffline(){
        String email01 = "hungndce130309@fpt.edu.vn";
        doctorModel.toggleDoctorOffline(email01);
        Doctor doctor = doctorModel.loadEntityDoctor(email01);
        assertEquals("Offline", doctor.getStatus());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should verify the input doctor email")
    @RepeatedTest(40)
    void testCheckDoctor(){
        boolean checkTrue = true;
        boolean checkFalse = false;
        String email01 = "hungndce130309@fpt.edu.vn";
        String email02 = "ducpcce130109@fpt.edu.vn";
        assertEquals(checkTrue, doctorModel.checkDoctor(email01));
        assertEquals(checkFalse, doctorModel.checkDoctor(email02));
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should update correct information of doctor to the database")
    @RepeatedTest(65)
    void testUpdateDoctor(){
        String full_name = "Nguyen Tay Hung";
        String specialist = "Brain Tumor Analysis";
        String birthdate = "1999-02-03";
        String gender = "Other";
        String email = "hungndce130309@fpt.edu.vn";
        doctorModel.updateDoctor(full_name, specialist, birthdate, gender, email);
        DoctorDTO doctor = doctorModel.loadDoctor(email);
        assertEquals(full_name, doctor.getFullName());
        assertEquals(specialist, doctor.getSpecialist());
        assertEquals(birthdate, doctor.getBirthdate());
        assertEquals(gender, doctor.getGender());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should update ip of doctor computer to the database")
    @RepeatedTest(55)
    void testUpdateDoctorIp() {
        String email = "hungndce130309@fpt.edu.vn";
        String ip = "1921681100";
        doctorModel.updateDoctorIP(email, ip);
        DoctorDTO doctor = doctorModel.loadDoctor(email);
        assertEquals(ip, doctor.getIp());
        System.out.println("Success");
    }
    @BeforeEach
    void setUp() {
        doctorModel = new DoctorModel();
    }
}