package org.fpt.Model;

import org.fpt.Connection.JdbcConnection;
import org.fpt.DTO.FullTaskDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class FullTaskModel {
    JdbcConnection c = new JdbcConnection();        //Initialize JdbcConnection object
    /*Load all data needed for a doctor task*/
    public FullTaskDTO loadFullTask(UUID uuid){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        FullTaskDTO FullTaskDTO = new FullTaskDTO();
        try{
            stmt = connection.createStatement();
            String sql = "SELECT * FROM Task WHERE id='" + uuid.toString() + "';";  //SQL query
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                //Get data from database
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String stringPid = rs.getString("id_patient");
                UUID Pid = UUID.fromString(stringPid);
                String stringTid = rs.getString("id_technician");
                UUID Tid = UUID.fromString(stringTid);
                String stringDid = rs.getString("id_doctor");
                UUID Did = UUID.fromString(stringDid);
                String folder_name = rs.getString("folder_name");
                String note = rs.getString("note");
                FullTaskDTO = new FullTaskDTO(id, Pid, Tid, Did, folder_name, note);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return FullTaskDTO;
    }
}
