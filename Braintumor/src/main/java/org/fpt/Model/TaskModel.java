package org.fpt.Model;

import org.fpt.Connection.JdbcConnection;
import org.fpt.DTO.HistoryDTO;
import org.fpt.DTO.TaskDTO;
import org.fpt.Entity.Patient;
import org.fpt.Entity.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class TaskModel {
    JdbcConnection c = new JdbcConnection();

    /*Load list tasks completed for paging*/
    public ArrayList<HistoryDTO> loadPagingHistory(int page, String email){
        int pageFrom=(page-1);
        String limit = "10";
        ArrayList<HistoryDTO> listTask = new ArrayList<HistoryDTO>();
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        HistoryDTO History = new HistoryDTO();
        PatientModel patientModel = new PatientModel();
        Patient patient = new Patient();
        int idCount=0;
        try{
            stmt = connection.createStatement();
            System.out.println("his " +pageFrom);
            String sql = "SELECT t.id, p.full_name, p.gender, p.birthdate, t.upload_date, t.status FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Completed' and d.mail='"+email+"' and t.status='Active' Order by t.predict_date DESC offset " + String.valueOf(pageFrom) +" limit " +limit;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String pName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String birthdate = rs.getString("birthdate");
                String upload_date = rs.getString("upload_date");
                String status = rs.getString("status");
                History = new HistoryDTO(idCount, id, pName, gender, birthdate, upload_date, status);
                listTask.add(History);
                idCount++;
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return null;
        }
        return listTask;
    }
    /*Load the number tasks pending loaded*/
    public int loadTaskNumbers(String email){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        int count = 1;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and d.mail='"+email+"' and t.status='Active';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                count = rs.getInt("count");
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
    /*Load the number of task completed loaded*/
    public int loadHistoryNumbers(String email){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        int count = 0;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Completed' and d.mail='"+email+"' and t.status='Active';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                count = rs.getInt("count");
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return  count;
    }
    /*Load list tasks pending for paging*/
    public ArrayList<TaskDTO> loadPagingTask(int page, String email){
        int pageFrom=(page-1)*10;
        String limit = "10";
        ArrayList<TaskDTO> listTask = new ArrayList<TaskDTO>();
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        TaskDTO Task = new TaskDTO();
        PatientModel patientModel = new PatientModel();
        Patient patient = new Patient();
        try{
            stmt = connection.createStatement();
            String sql = "SELECT t.id, t.folder_name, p.full_name, p.gender, p.birthdate, t.upload_date, t.status FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and d.mail='"+email+"' and t.status='Active' Order by t.upload_date DESC offset " + String.valueOf(pageFrom) +" limit " +limit;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            int index = 0;
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String folder_name = rs.getString("folder_name");
                String pName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String birthdate = rs.getString("birthdate");
                String upload_date = rs.getString("upload_date");
                String status = rs.getString("status");
                int flag= 0;
                flag = folder_name.indexOf("_EDIT_");
                if(flag !=0){
                    pName+="    [EDITED]";
                }
                Task = new TaskDTO(index, id, pName, gender, birthdate, upload_date, status);
                listTask.add(Task);
                index ++;
            }
            stmt.close();
            //connection.commit();
            connection.close();
            System.out.println("Size:"+listTask.size());
        } catch(Exception e){
            e.printStackTrace();
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return null;
        }
        return listTask;
    }
    /*Load number of tasks matched*/
    public int searchTaskNumbers(String email, String patientName){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        int count = 1;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and d.mail='"+email+"' and p.full_name like '%"+patientName+ "%'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                count = rs.getInt("count");
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return  count;
    }

    public int searchHistoryNumbers(String email, String patientName){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        int count = 0;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Completed' and d.mail='"+email+"' and p.full_name like '%"+patientName+ "%'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                count = rs.getInt("count");
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return  count;
    }

    public ArrayList<TaskDTO> searchPagingTask(int page, String email, String patientName){
        System.out.println("Page ne: " + page);
        int pageFrom=(page-1)*10;
        String limit = "10";
        ArrayList<TaskDTO> listTask = new ArrayList<TaskDTO>();
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        TaskDTO Task = new TaskDTO();
        PatientModel patientModel = new PatientModel();
        Patient patient = new Patient();
        try{
            stmt = connection.createStatement();
            String sql = "SELECT t.id, p.full_name, p.gender, p.birthdate, t.upload_date, t.status FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and d.mail='"+email+"' and p.full_name like '%"+patientName+"%' and t.status='Active' Order by t.upload_date DESC offset " + String.valueOf(pageFrom) +" limit " +limit;
            ResultSet rs = stmt.executeQuery(sql);
            int index = 0;
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String pName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String birthdate = rs.getString("birthdate");
                String upload_date = rs.getString("upload_date");
                String status = rs.getString("status");
                Task = new TaskDTO(index, id, pName, gender, birthdate, upload_date, status);
                listTask.add(Task);
                index ++;
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return null;
        }
        return listTask;
    }
    public ArrayList<HistoryDTO> searchPagingHistory(int page, String email, String patientName){
        System.out.println("Page ne: " + page);
        int pageFrom=(page-1)*10;
        String limit = "10";
        ArrayList<HistoryDTO> listHistory = new ArrayList<HistoryDTO>();
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        HistoryDTO History = new HistoryDTO();
        PatientModel patientModel = new PatientModel();
        Patient patient = new Patient();
        try{
            stmt = connection.createStatement();
            String sql = "SELECT t.id, p.full_name, p.gender, p.birthdate, t.upload_date, t.status FROM task t JOIN patient p on p.id=t.id_patient join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Completed' and d.mail='"+email+"' and p.full_name like '%"+patientName+"%' and t.status='Active' Order by t.upload_date DESC offset " + String.valueOf(pageFrom) +" limit " +limit;
            ResultSet rs = stmt.executeQuery(sql);
            int index = 0;
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String pName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String birthdate = rs.getString("birthdate");
                String upload_date = rs.getString("upload_date");
                String status = rs.getString("status");
                History = new HistoryDTO(index, id, pName, gender, birthdate, upload_date, status);
                listHistory.add(History);
                index ++;
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return listHistory;
    }

    public UUID insertTask(String folderName, UUID pID, UUID tID, UUID dID, String note){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        UUID id = null;
        try{
            stmt = connection.createStatement();
            id = UUID.randomUUID();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "INSERT INTO Task (id, id_patient, id_technician, id_doctor, folder_name, upload_date, upload_status, predict_status, result, note, status)" +
                    "VALUES ('"+ id.toString() +"','"  + pID.toString() + "','" + tID.toString() + "','" +
                    dID.toString() + "','" + folderName + "','" + dtf.format(now) +"','" +
                    "Complete" + "','Pending',' ', '"+note+"','" + "Active"+"');";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return id;
        }
        System.out.println("Task created successfully");
        return id;
    }
    public void updateTask(UUID id,String folderName, UUID dID, String note){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "UPDATE task set folder_name='" + folderName+"_EDIT_" +"', id_doctor='" + dID.toString()+"', note='"+note+
                    "' WHERE id='"+id.toString()+"';";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
        System.out.println("Task updated successfully");
    }
    public ArrayList<Task>  getAllPendingTaskByDoctorUUID(UUID doctorUUID){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        //UUID id = UUID.randomUUID();
        try{
            ArrayList<Task> tasks = new ArrayList<>();
            stmt = connection.createStatement();
            String sql = "SELECT * FROM task WHERE id_doctor = '"+doctorUUID.toString()+"' and predict_status='Pending';";
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String stringpid = rs.getString("id_patient");
                UUID pid = UUID.fromString(stringpid);
                String stringtid = rs.getString("id_technician");
                UUID tid = UUID.fromString(stringtid);
                String stringdid = rs.getString("id_doctor");
                UUID did = UUID.fromString(stringdid);
                String folder_name = rs.getString("folder_name");
                String upload_date = rs.getString("upload_date");
                String predict_date = rs.getString("predict_date");
                String upload_status = rs.getString("upload_status");
                String predict_status = rs.getString("predict_status");
                String result = rs.getString("result");
                String note = rs.getString("note");
                String status = rs.getString("status");
                Task task = new Task(id, pid, tid, did, folder_name, upload_date, predict_date, upload_status, predict_status, result, note, status);
                tasks.add(task);
            }
            stmt.close();
            connection.close();
            //connection.commit();
            return tasks;
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            //System.exit(0);
        }
        return null;
    }
    public UUID getPatientID(UUID ID){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        UUID id = UUID.randomUUID();
        try{
            stmt = connection.createStatement();
            System.out.println("CMM:"+ID);
            String sql = "SELECT id_patient FROM task WHERE id = '"+ID.toString()+"' and status='Active';";
            ResultSet rs = stmt.executeQuery(sql);
            int index = 0;
            while  (rs.next()){
                String Stringid = rs.getString("id_patient");
                id = UUID.fromString(Stringid);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            //System.exit(0);
        }
        System.out.println("CMM:"+id);
        return id;
    }

    public void disableTask(String mail){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql = "UPDATE Task set status='Inactive'" +
                    "WHERE mail='" + mail.toString() +"';";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
            return;
        }
    }
    public void completeTask(UUID id){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try{
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "UPDATE task set predict_status='Completed', predict_date='"+ dtf.format(now)+
                    "' WHERE id='"+id.toString()+"';";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
        System.out.println("Task completed");
    }
    public Task loadTaskbyId(UUID taskId){
        Task task = new Task();
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT * FROM task t WHERE id='"+taskId.toString()+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String stringpid = rs.getString("id_patient");
                UUID pid = UUID.fromString(stringpid);
                String stringtid = rs.getString("id_technician");
                UUID tid = UUID.fromString(stringtid);
                String stringdid = rs.getString("id_doctor");
                UUID did = UUID.fromString(stringdid);
                String folder_name = rs.getString("folder_name");
                String upload_date = rs.getString("upload_date");
                String predict_date = rs.getString("predict_date");
                String upload_status = rs.getString("upload_status");
                String predict_status = rs.getString("predict_status");
                String result = rs.getString("result");
                String note = rs.getString("note");
                String status = rs.getString("status");
                task = new Task(id, pid, tid, did, folder_name, upload_date, predict_date, upload_status, predict_status, result, note, status);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return null;
        }
        return task;
    }
}
