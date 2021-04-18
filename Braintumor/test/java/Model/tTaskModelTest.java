package Model;

import org.fpt.DTO.tTaskDTO;
import org.fpt.Model.tTaskModel;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class tTaskModelTest {
    tTaskModel tTaskModel;
    @Test
    @DisplayName("Should load list of tasks along with patient information created by the technician")
    @RepeatedTest(40)
    void testLoadPagingHistory(){
        ArrayList<tTaskDTO> listTask;
        int page = 1;
        String email = "ducpcce130109@fpt.edu.vn";
        boolean checkCorrect = true;
        boolean checkSize = false;
        listTask = tTaskModel.loadPagingHistory(page, email);
        if (listTask.size()>0 & listTask.size()<11){
            checkSize = true;
        }
        assertEquals(checkCorrect, checkSize);
    }
    @Test
    @DisplayName("Should load number of tasks along with patient information created by the technician")
    @RepeatedTest(35)
    void testLoadHistoryNumbers(){
        String email = "ducpcce130109@fpt.edu.vn";
        int numberHistory = tTaskModel.loadHistoryNumbers(email);
        boolean checkCorrect = true;
        boolean checkNumber = false;
        if (numberHistory > 0){
            checkNumber = true;
        }
        assertEquals(checkCorrect, checkNumber);
    }
    @Test
    @DisplayName("Should load list of tasks along with patient information created by the technician based on the search")
    @RepeatedTest(40)
    void testSearchPagingHistory(){
        ArrayList<tTaskDTO> listTask;
        int page = 1;
        String email = "ducpcce130109@fpt.edu.vn";
        String patientName = "Phan Minh Tam";
        String doctorName = "Nguyen Dong Hung";
        boolean checkCorrect = true;
        boolean checkSize = false;
        listTask = tTaskModel.searchPagingHistory(page, email, patientName, doctorName);
        if (listTask.size()>0 & listTask.size()<11){
            checkSize = true;
        }
        assertEquals(checkCorrect, checkSize);
    }
    @Test
    @DisplayName("Should load number of tasks along with patient information created by the technician based on the search")
    @RepeatedTest(40)
    void testSearchHistoryNumbers(){
        String email = "ducpcce130109@fpt.edu.vn";
        String patientName = "Phan Minh Tam";
        String doctorName = "Nguyen Dong Hung";
        int numberHistory = tTaskModel.searchHistoryNumbers(email, patientName, doctorName);
        boolean checkCorrect = true;
        boolean checkNumber = false;
        if (numberHistory > 0){
            checkNumber = true;
        }
        assertEquals(checkCorrect, checkNumber);
    }
    @BeforeEach
    void setUp() {
        tTaskModel = new tTaskModel();
    }
}