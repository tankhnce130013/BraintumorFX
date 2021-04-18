package Model;

import org.fpt.DTO.FullTaskDTO;
import org.fpt.DTO.HistoryDTO;
import org.fpt.DTO.TaskDTO;
import org.fpt.Entity.Task;
import org.fpt.Model.FullTaskModel;
import org.fpt.Model.TaskModel;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskModelTest {
    TaskModel taskModel;
    @Test
    @DisplayName("Should load list of tasks assigned to the doctor")
    @RepeatedTest(40)
    void testLoadPagingTask() {
        ArrayList<TaskDTO> listTask;
        String email = "hungndce130309@fpt.edu  .vn";
        int page = 1;
        boolean checkCorrect = true;
        boolean checkSize = false;
        listTask = taskModel.loadPagingTask(page, email);
        if (listTask.size()>0 & listTask.size()<11){
            checkSize = true;
        }
        assertEquals(checkCorrect, checkSize);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load number of tasks assigned to the doctor")
    @RepeatedTest(40)
    void testLoadTaskNumbers(){
        String email = "hungndce130309@fpt.edu.vn";
        int numberTask = taskModel.loadTaskNumbers(email);
        boolean checkCorrect = true;
        boolean checkNumber = false;
        if (numberTask > 0){
            checkNumber = true;
        }
        assertEquals(checkCorrect, checkNumber);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load list of tasks completed by the doctor")
    @RepeatedTest(40)
    void testLoadPagingHistory() {
        ArrayList<HistoryDTO> listTask;
        String email = "hungndce130309@fpt.edu.vn";
        int page = 1;
        boolean checkCorrect = true;
        boolean checkSize = false;
        listTask = taskModel.loadPagingHistory(1, email);
        if (listTask.size()>0 & listTask.size()<11){
            checkSize = true;
        }
        assertEquals(checkCorrect, checkSize);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load number of tasks completed by the doctor")
    @RepeatedTest(40)
    void testLoadHistoryNumbers(){
        String email = "hungndce130309@fpt.edu.vn";
        int numberTask = taskModel.loadHistoryNumbers(email);
        boolean checkCorrect = true;
        boolean checkNumber = false;
        if (numberTask > 0){
            checkNumber = true;
        }
        assertEquals(checkCorrect, checkNumber);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load list of tasks completed based on the search")
    @RepeatedTest(40)
    void testSearchPagingHistory(){
        ArrayList<HistoryDTO> listTask;
        int page = 1;
        String email = "hungndce130309@fpt.edu.vn";
        String patientName = "NGUYEN VIEN NGOC";
        boolean checkCorrect = true;
        boolean checkSize = false;
        listTask = taskModel.searchPagingHistory(page, email, patientName);
        if (listTask.size()>0 & listTask.size()<11){
            checkSize = true;
        }
        assertEquals(checkCorrect, checkSize);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load number of tasks based on the search")
    @RepeatedTest(40)
    void testSearchTaskNumbers(){
        String email = "hungndce130309@fpt.edu.vn";
        String patientName = "Phan Minh Tam";
        int numberHistory = taskModel.searchTaskNumbers(email, patientName);
        boolean checkCorrect = true;
        boolean checkNumber = false;
        if (numberHistory > 0){
            checkNumber = true;
        }
        assertEquals(checkCorrect, checkNumber);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load list of tasks based on the search")
    @RepeatedTest(40)
    void testSearchPagingTask(){
        ArrayList<TaskDTO> listTask;
        int page = 1;
        String email = "hungndce130309@fpt.edu.vn";
        String patientName = "Phan Minh Tam";
        boolean checkCorrect = true;
        boolean checkSize = false;
        listTask = taskModel.searchPagingTask(page, email, patientName);
        if (listTask.size()>0 & listTask.size()<11){
            checkSize = true;
        }
        assertEquals(checkCorrect, checkSize);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load number of tasks completed based on the search")
    @RepeatedTest(40)
    void testSearchHistoryNumbers(){
        String email = "hungndce130309@fpt.edu.vn";
        String patientName = "NGUYEN VIEN NGOC";
        int numberHistory = taskModel.searchHistoryNumbers(email, patientName);
        boolean checkCorrect = true;
        boolean checkNumber = false;
        if (numberHistory > 0){
            checkNumber = true;
        }
        assertEquals(checkCorrect, checkNumber);
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should insert task to the database")
    @RepeatedTest(65)
    void testInsertTask(){
        String folderName = "NguyenVienNgoc-15-04-2021-10_39_23";
        String stringpid = "95c5e2ec-3371-4aa0-91c9-724f74fc4738";
        UUID pid = UUID.fromString(stringpid);
        String stringtid = "5515ea83-7893-4b21-9555-276b29e00d9e";
        UUID tid = UUID.fromString(stringtid);
        String stringdid = "f007fdf7-55ca-4f53-8478-41c10c1077b4";
        UUID did = UUID.fromString(stringdid);
        String note = "TestNote";
        UUID id = taskModel.insertTask(folderName, pid, tid, did, note);
        FullTaskModel fullTaskModel = new FullTaskModel();
        FullTaskDTO task = fullTaskModel.loadFullTask(id);
        assertEquals(id, task.getId());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should update task with correct information in the database")
    @RepeatedTest(60)
    void testUpdateTask(){
        String stringid = "11d99bf3-80a8-4ced-b91e-c4763720c3f8";
        UUID id = UUID.fromString(stringid);
        String folderName = "NguyenVienThuat-15-04-2021-10_39_23";
        String stringdid = "f007fdf7-55ca-4f53-8478-41c10c1077b4";
        UUID did = UUID.fromString(stringdid);
        String note = "TestNoteUpdate";
        taskModel.updateTask(id, folderName, did, note);
        FullTaskModel fullTaskModel = new FullTaskModel();
        FullTaskDTO task = fullTaskModel.loadFullTask(id);
        assertEquals(folderName, task.getFolderName());
        assertEquals(did, task.getDoctorID());
        assertEquals(note, task.getNote());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should get correct patient id")
    @RepeatedTest(45)
    void testGetPatientId() {
        String stringid = "11d99bf3-80a8-4ced-b91e-c4763720c3f8";
        UUID id = UUID.fromString(stringid);
        String stringpid = "e050d907-7d37-42fc-b2dd-7822b76499a8";
        UUID pid = UUID.fromString(stringpid);
        assertEquals(pid, taskModel.getPatientID(id));
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should load correct task by using id")
    @RepeatedTest(35)
    void testLoadTaskbyId(){
        String stringid = "b41f6004-a9ae-4aec-8021-86ca347ad43f";
        UUID id = UUID.fromString(stringid);
        Task task = taskModel.loadTaskbyId(id);
        assertEquals(id, task.getId());
        System.out.println("Success");
    }
    @Test
    @DisplayName("Should change predict status of task to completed")
    @RepeatedTest(65)
    void testCompleteTask(){
        String stringid = "b41f6004-a9ae-4aec-8021-86ca347ad43f";
        UUID id = UUID.fromString(stringid);
        String predictStatus = "Completed";
        taskModel.completeTask(id);
        Task task = taskModel.loadTaskbyId(id);
        assertEquals(predictStatus, task.getPredictStatus());
        System.out.println("Success");
    }
    @BeforeEach
    void setUp() {
        taskModel = new TaskModel();
    }
}