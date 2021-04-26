package org.fpt.Model;

import org.fpt.Connection.JdbcConnection;
import org.fpt.DTO.PatientDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PatientModel {
    JdbcConnection c = new JdbcConnection();
    /*Insert patient information to database*/
    public UUID insertPatient(String fullName, String dateOfBirth, String gender, String phone, String healthInsurance){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        UUID id;
        try{
            id = UUID.randomUUID();
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "INSERT INTO patient (id, full_name, birthdate, gender, phone, health_insurance_number, created_at, updated_at, status)" +
                    "VALUES ('" + id + "','" + fullName + "','" + dateOfBirth + "','" +
                    gender + "','" + phone + "','" +
                    healthInsurance + "','" + dtf.format(now) + "', NULL, 'Active');";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            connection.commit();
            stmt.close();
            connection.commit();
            connection.close();
        }
        catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return UUID.randomUUID();
        }
        return id;
    }
    /*Load patient data from database by using patient id*/
    public PatientDTO loadPatient(UUID Pid){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        PatientDTO patient =new PatientDTO();
        try{
            stmt = connection.createStatement();
            String sql = "SELECT * FROM patient WHERE id ='" + Pid.toString() +"'";
            ResultSet rs = stmt.executeQuery( sql);
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String full_name = rs.getString("full_name");
                String birthdate = rs.getString("birthdate");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String hiNumber = rs.getString("health_insurance_number");
                patient = new PatientDTO(id, full_name, birthdate, gender, phone, hiNumber);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        return patient;
    }
    /*Update patient information to database*/
    public UUID updatePatient(UUID id, String fullName, String dateOfBirth, String gender, String phone, String healthInsurance) {
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "Update patient set full_name='"+fullName+"', birthdate='"+dateOfBirth+"', gender='"+gender+"', phone='"+phone+"', health_insurance_number='"+healthInsurance+"', updated_at='"+dtf.format(now)+"' " +
                    "WHERE id='"+id.toString()+"';";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}

