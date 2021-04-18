package Model;

import org.fpt.DTO.FullTaskDTO;
import org.fpt.Model.FullTaskModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FullTaskModelTest {
    FullTaskModel fullTaskModel;
    @Test
    @DisplayName("Should load information about the task and the patient")
    void testLoadFullTask() {
        String stringid = "019b85cc-233a-4250-adb3-32eecbcf903e";
        UUID id = UUID.fromString(stringid);
        String stringpid = "2932dbe3-0a66-4b48-bdae-72f6adf89da7";
        UUID pid = UUID.fromString(stringpid);
        String stringtid = "5515ea83-7893-4b21-9555-276b29e00d9e";
        UUID tid = UUID.fromString(stringtid);
        String stringdid = "84d755d5-b157-4670-a933-71f83deca852";
        UUID did = UUID.fromString(stringdid);
        String folderName = "NguyenVienNgoc-15-04-2021-10_39_23";
        String note = "Hung";
        FullTaskDTO fullTaskDTO = fullTaskModel.loadFullTask(id);
        assertEquals(id, fullTaskDTO.getId());
        assertEquals(pid, fullTaskDTO.getIdPatient());
        assertEquals(did, fullTaskDTO.getDoctorID());
        assertEquals(tid, fullTaskDTO.getIdTechnician());
        assertEquals(folderName, fullTaskDTO.getFolderName());
        assertEquals(note, fullTaskDTO.getNote());
        System.out.println("Success");
    }

    @BeforeEach
    void setUp() {
        fullTaskModel = new FullTaskModel();
    }

    @AfterEach
    void tearDown() {
    }
}