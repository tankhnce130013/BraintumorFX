package org.fpt.Model;

import org.fpt.Connection.JdbcConnection;
import org.fpt.DTO.tTaskDTO;
import org.fpt.Entity.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class tTaskModel {
    private JdbcConnection c = new JdbcConnection();
    public ArrayList<tTaskDTO> loadPagingHistory(int page, String email){
        int pageFrom=(page-1);
        String limit = "10";
        ArrayList<tTaskDTO> listtTask = new ArrayList<tTaskDTO>();
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        tTaskDTO tTask = new tTaskDTO();
        int idCount=0;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT t.id, p.full_name, d.full_name as doctor_name, p.birthdate, t.upload_date, t.status FROM task t JOIN patient p on p.id=t.id_patient join technician t2 on t2.id=t.id_technician join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and t2.mail='"+email+"' and t.status='Active' offset " + String.valueOf(pageFrom) +" limit " +limit;
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String pName = rs.getString("full_name");
                String dName = rs.getString("doctor_name");
                String upload_date = rs.getString("upload_date");
                String status = rs.getString("status");
                tTask = new tTaskDTO(idCount, id, pName, dName, upload_date, status);
                listtTask.add(tTask);
                idCount++;
            }
            stmt.close();
//            connection.commit();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return listtTask;
    }

    public int loadHistoryNumbers(String email){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        int count = 0;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM task t JOIN patient p on p.id=t.id_patient join technician t2 on t2.id=t.id_technician join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and t2.mail='\"+email+\"' and t.status='Active'";
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
    public int searchHistoryNumbers(String email, String patientName, String doctorName){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        int count = 0;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM task t JOIN patient p on p.id=t.id_patient join technician t2 on t2.id=t.id_technician join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and t2.mail='\"+email+\"' and t.status='Active' and (p.full_name like '%"+patientName+ "%' or d.full_name='%"+doctorName+"%' );";
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
        return  count;
    }
    public ArrayList<tTaskDTO> searchPagingHistory(int page, String email, String patientName, String doctorName){
        int pageFrom=(page-1);
        String limit = "1";
        ArrayList<tTaskDTO> listtTask = new ArrayList<tTaskDTO>();
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        tTaskDTO tTask = new tTaskDTO();
        PatientModel patientModel = new PatientModel();
        Patient patient = new Patient();
        int idCount=0;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT t.id, p.full_name, d.full_name, p.birthdate, t.upload_date, t.status FROM task t JOIN patient p on p.id=t.id_patient join technician t2 on t2.id=t.id_technician join doctor d on d.id=t.id_doctor WHERE t.predict_status = 'Pending' and t2.mail='"+email+"' and (p.full_name like '%" +patientName+ "%' or d.full_name like '%"+doctorName+"%') and t.status='Active' Order by t.upload_date DESC offset " + String.valueOf(pageFrom) +" limit " +limit;
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String pName = rs.getString("p.full_name");
                String dName = rs.getString("d.full_name");
                String gender = rs.getString("gender");
                String birthdate = rs.getString("birthdate");
                String upload_date = rs.getString("upload_date");
                String status = rs.getString("status");
                tTask = new tTaskDTO(idCount, id, pName, dName, upload_date, status);
                listtTask.add(tTask);
                idCount++;
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return listtTask;
    }


}
