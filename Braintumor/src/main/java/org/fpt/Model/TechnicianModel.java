package org.fpt.Model;

import org.fpt.Connection.JdbcConnection;
import org.fpt.DTO.TechnicianDTO;
import org.fpt.Entity.Technician;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TechnicianModel {
    JdbcConnection c = new JdbcConnection();

    public TechnicianDTO convertTechnicianEntityToDTO(Technician techDTO){
        UUID id = techDTO.getId();
        String fullName = techDTO.getFullName();
        String email = techDTO.getEmail();
        String birthdate = techDTO.getBirthdate();
        String specialist = techDTO.getSpecialist();
        String gender = techDTO.getGender();
        String ip = techDTO.getIp();
        TechnicianDTO t = new TechnicianDTO(id,fullName,email,birthdate,specialist,gender,ip);
        return t;
    }

    public TechnicianDTO loadTechnician(String mail) {
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        Technician technician = new Technician();
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM technician WHERE mail ='" + mail + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
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
                technician = new Technician(id, full_name, email, birthdate, specialist, gender, ip, createDate, updateDate, status);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        TechnicianDTO t = new TechnicianDTO();
        t = convertTechnicianEntityToDTO(technician);
        return t;
    }
    public boolean checkTechnician(String email){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        String id="";
        boolean check;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM technician WHERE mail='"+email+"';";
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

    public void updateTechnician(String full_name, String specialist, String birthdate, String gender, String mail){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try{
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            String sql1 = "SET datestyle = ymd;";
            String sql2 = "UPDATE technician SET full_name='" + full_name + "', specialist='"+
                    specialist + "', birthdate='" + birthdate + "', gender='" + gender +
                    "', updated_at='" + dtf.format(now) +"'  " +
                    "WHERE mail='" + mail +"';";
            stmt.execute(sql1);
            stmt.executeUpdate(sql2);
            connection.commit();
            stmt.close();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
    }

    public void disableTechnician(String mail) {
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "UPDATE technician set status='Inactive'" +
                    "WHERE mail='" + mail.toString() + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return;
        }
    }
}
