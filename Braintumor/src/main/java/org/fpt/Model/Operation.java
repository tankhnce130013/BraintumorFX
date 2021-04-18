package org.fpt.Model;

import org.fpt.Connection.JdbcConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Operation {
    JdbcConnection c = new JdbcConnection();    //Initialize JdbcConnection object
    /*Add patient and task simultaneously*/
    public void addPatientAndTask(String fullName, String dateOfBirth, String gender, String phone, String healthInsurance, String folderName, String note, UUID dID, UUID tID){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PatientModel patientModel = new PatientModel();
        TaskModel taskModel = new TaskModel();
        try {
            UUID pID = patientModel.insertPatient(fullName, dateOfBirth, gender, phone, healthInsurance);
            taskModel.insertTask(folderName, pID, tID, dID, note);
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return ;
        }
    }
    /*Update information of patient and task simultaneously*/
    public void updatePatientAndTask(String fullName, String dateOfBirth, String gender, String phone, String healthInsurance, String folderName, String note, UUID dID, UUID tID){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PatientModel patientModel = new PatientModel();
        TaskModel taskModel = new TaskModel();
        try{
            taskModel.updateTask(tID, folderName, dID, note);
            UUID pID=taskModel.getPatientID(tID);
            patientModel.updatePatient(pID, fullName, dateOfBirth, gender, phone, healthInsurance);
        }catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    /*Check if the account sign in is doctor or technician or nothing*/
    public int checkLogin(String email){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DoctorModel doctorModel = new DoctorModel();
        TechnicianModel technicianModel = new TechnicianModel();
        int check=0;
        boolean check2 = false;
        try{
            check2 = doctorModel.checkDoctor(email);
            if (check2!=true){
                check2=technicianModel.checkTechnician(email);
                if (check2!=true){
                    return check;
                }
                check = 2;
                return check;
            } else {
                check = 1;
            }
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        return check;
    }
}
