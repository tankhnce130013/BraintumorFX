package Model;

import org.fpt.DTO.TechnicianDTO;
import org.fpt.Entity.Technician;
import org.fpt.Model.TechnicianModel;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TechncianModelTest {
    TechnicianModel technicianModel;
    @Test
    @DisplayName("Should convert an Entity technician to a DTO technician")
    @RepeatedTest(30)
    void testConvertTechnician() {
        UUID id = UUID.randomUUID();
        Technician technician01 = new Technician(id, "Nguyen Van Hung", "hung@fpt.edu.vn", "19990913", "Brain MRI Analysis", "Male", "192168011", "19990913", "19990913", "Active");
        TechnicianDTO technician02 = new TechnicianDTO(id, "Nguyen Van Hung", "hung@fpt.edu.vn", "19990913", "Brain MRI Analysis", "Male", "192168011");
        TechnicianDTO technician03 = technicianModel.convertTechnicianEntityToDTO(technician01);
        assertEquals(technician02.getId(), technician03.getId());
        assertEquals(technician02.getFullName(), technician03.getFullName());
        assertEquals(technician02.getEmail(), technician03.getEmail());
        assertEquals(technician02.getBirthdate(), technician03.getBirthdate());
        assertEquals(technician02.getSpecialist(), technician03.getSpecialist());
        assertEquals(technician02.getGender(), technician03.getGender());
        assertEquals(technician02.getIp(), technician03.getIp());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load information of technician from database to DTO technician")
    @RepeatedTest(30)
    void testLoadTechnician() {
        String email01 = "ducpcce130109@fpt.edu.vn";
        TechnicianDTO technician = technicianModel.loadTechnician(email01);
        assertEquals(email01, technician.getEmail());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should verify the input technician email")
    @RepeatedTest(30)
    void testCheckTechnician(){
        boolean checkTrue = true;
        boolean checkFalse = false;
        String email01 = "hungndce130309@fpt.edu.vn";
        String email02 = "ducpcce130109@fpt.edu.vn";
        assertEquals(checkTrue, technicianModel.checkTechnician(email02));
        assertEquals(checkFalse, technicianModel.checkTechnician(email01));
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should update correct information of technician to the database")
    @RepeatedTest(65)
    void testUpdateTechnician(){
        String full_name = "Pham Minh Duc";
        String specialist = "Brain Tumor Analysis";
        String birthdate = "1999-02-03";
        String gender = "Other";
        String email = "ducpcce130109@fpt.edu.vn";
        technicianModel.updateTechnician(full_name, specialist, birthdate, gender, email);
        TechnicianDTO technician = technicianModel.loadTechnician(email);
        assertEquals(full_name, technician.getFullName());
        assertEquals(specialist, technician.getSpecialist());
        assertEquals(birthdate, technician.getBirthdate());
        assertEquals(gender, technician.getGender());
        System.out.println("Success");
    }
    @BeforeEach
    void setUp() {
        technicianModel = new TechnicianModel();
    }
}