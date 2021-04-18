package org.fpt.Model;

import org.fpt.DTO.DoctorDTO;
import org.fpt.Entity.Doctor;
import org.fpt.Connection.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class DoctorModel {
    JdbcConnection c = new JdbcConnection();        //Initialize JdbcConnection object
    /*A function to convert the Entity to the DTO object*/
    public DoctorDTO convertDoctorEntityToDTO(Doctor doctor){
        //Get the element data from the Entity object
        UUID id = doctor.getId();
        String fullName = doctor.getFullName();
        String email = doctor.getEmail();
        String birthdate = doctor.getBirthdate();
        String specialist = doctor.getSpecialist();
        String gender = doctor.getGender();
        String ip = doctor.getIp();
        DoctorDTO d = new DoctorDTO(id,fullName,email,birthdate,specialist,gender,ip);      //Initialize DTO object
        return d;
    }

    public void updateDoctorIP(String mail, String ip){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try{
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "UPDATE doctor SET " +
                    "updated_at='" + dtf.format(now) +"', ip='"+ ip + "' " +
                    "WHERE mail='" + mail +"';";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
        System.out.println("Doctor updated successfully");
    }
    /*Load doctor information from database by using email*/
    public DoctorDTO loadDoctor(String mail){
        Connection connection = c.JdbcConnection();     //Initialize database connection
        Statement stmt = null;                          //Initialize statement
        Doctor doctor = new Doctor();                   //Initialize Doctor entity object
        try{
            stmt = connection.createStatement();        //Create statement for the connected database
            String sql = "SELECT * FROM doctor WHERE mail ='" + mail +"'";  //Create sql query
            ResultSet rs = stmt.executeQuery( sql);     //Create result set
            while  (rs.next()){
                //Get the data from the database
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String full_name = rs.getString("full_name");
                String email = rs.getString("mail");
                String specialist = rs.getString("specialist");
                String birthdate = rs.getString("birthdate");
                String gender = rs.getString("gender");
                String ip = rs.getString("ip");
                String createDate = rs.getString("created_at");
                String updateDate = rs.getString("updated_at");
                String status = rs.getString("status");
                doctor = new Doctor(id, full_name, email, birthdate, specialist, gender, ip, createDate, updateDate, status);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        DoctorDTO d = new DoctorDTO();
        d = convertDoctorEntityToDTO(doctor);       //Convert object from entity to DTO
        return d;
    }
    public Doctor loadEntityDoctor(String mail){
        Connection connection = c.JdbcConnection();     //Initialize database connection
        Statement stmt = null;                          //Initialize statement
        Doctor doctor = new Doctor();                   //Initialize Doctor entity object
        try{
            stmt = connection.createStatement();        //Create statement for the connected database
            String sql = "SELECT * FROM doctor WHERE mail ='" + mail +"'";  //Create sql query
            ResultSet rs = stmt.executeQuery( sql);     //Create result set
            while  (rs.next()){
                //Get the data from the database
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String full_name = rs.getString("full_name");
                String email = rs.getString("mail");
                String specialist = rs.getString("specialist");
                String birthdate = rs.getString("birthdate");
                String gender = rs.getString("gender");
                String ip = rs.getString("ip");
                String createDate = rs.getString("created_at");
                String updateDate = rs.getString("updated_at");
                String status = rs.getString("status");
                doctor = new Doctor(id, full_name, email, birthdate, specialist, gender, ip, createDate, updateDate, status);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return doctor;
    }
    /*Load all doctor those are online*/
    public ArrayList<DoctorDTO> loadDoctorOnline(){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        ArrayList<DoctorDTO> doctor = new ArrayList<>();    //Initialize an arraylist of DTO doctor
        try{
            stmt = connection.createStatement();
            String sql = "SELECT * FROM doctor WHERE status ='Online'";
            ResultSet rs = stmt.executeQuery( sql);
            while  (rs.next()){
                //Get data from database
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String full_name = rs.getString("full_name");
                String email = rs.getString("mail");
                String specialist = rs.getString("specialist");
                String birthdate = rs.getString("birthdate");
                String gender = rs.getString("gender");
                String ip = rs.getString("ip");
                doctor.add(new DoctorDTO(id, full_name, email, birthdate, specialist, gender, ip));
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return doctor;
    }

    /*Update status of doctor to online*/
    public void toggleDoctorOnline(String mail){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");     //Create datetime formatter
        LocalDateTime now = LocalDateTime.now();    //Load the present datetime
        try{
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "UPDATE doctor SET " +
                    "updated_at='" + dtf.format(now) +"', status='Online' " +
                    "WHERE mail='" + mail +"';";
            stmt.execute(sql1);     //Execute query 1
            stmt.executeUpdate(sql2);       //Execute query 2
            stmt.close();
            connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
        System.out.println("Doctor updated successfully");
    }
    /*Update status of doctor to offline*/
    public void toggleDoctorOffline(String mail){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try{
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "UPDATE doctor SET " +
                    "updated_at='" + dtf.format(now) +"', status='Offline' " +
                    "WHERE mail='" + mail +"';";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
        System.out.println("Doctor updated successfully");
    }
    /*Check sign in account doctor by using email*/
    public boolean checkDoctor(String email){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        String id="";
        boolean check;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM doctor WHERE mail='"+email+"';";
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                id = rs.getString("id");
            }
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        if (id!=""){
            return true;
        } else {
            return false;
        }
    }
    /*Update doctor profile*/
    public void updateDoctor(String full_name, String specialist, String birthdate, String gender, String mail){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try{
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "UPDATE doctor SET full_name='" + full_name + "', specialist='"+
                    specialist + "', birthdate='" + birthdate + "', gender='" + gender +
                    "', updated_at='" + dtf.format(now) +"'  " +
                    "WHERE mail='" + mail +"';";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
        System.out.println("Doctor updated successfully");
    }
}
