package Connection;

import org.fpt.Model.DoctorModel;
import org.fpt.DTO.DoctorDTO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcConnectionTest {
    @Test
    @DisplayName("Should display connection success message")
    @RepeatedTest(50)
    public void testConnection() {
        DoctorModel doctorModel = new DoctorModel();
        DoctorDTO doctor = doctorModel.loadDoctor("hungndce130309@fpt.edu.vn");
        assertEquals("hungndce130309@fpt.edu.vn", doctor.getEmail());
    }
    @BeforeEach
    void setUp() {
    }
}