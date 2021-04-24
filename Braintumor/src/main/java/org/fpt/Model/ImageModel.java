package org.fpt.Model;

import org.fpt.Connection.JdbcConnection;
import org.fpt.DTO.ImageDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

public class ImageModel {
    JdbcConnection c = new JdbcConnection();        //Initialize JdbcConnection object
    /*Load all image data from database*/
    public ArrayList<ImageDTO> loadAllImage(UUID tID){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        ImageDTO Image = new ImageDTO();
        ArrayList<ImageDTO> listImage = new ArrayList<ImageDTO>();
        int index = 0;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT * FROM image WHERE id_task='" + tID.toString() + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while  (rs.next()){
                String stringid = rs.getString("id");
                UUID id = UUID.fromString(stringid);
                String image_data = rs.getString("image_data");
                byte[] decodedBytes = Base64.getDecoder().decode(image_data);
                String type_of_tumor = rs.getString("type_of_tumor");
                String confirm_date = rs.getString("confirm_date");
                Image = new ImageDTO(index, id,"", decodedBytes, type_of_tumor, confirm_date);
                listImage.add(Image);
            }
            stmt.close();
            //connection.commit();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return listImage;
    }
    /*Store any predicted image to database*/
    public void saveTaskImage(ArrayList<ImageDTO> listImage, String taskID){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try{
            stmt = connection.createStatement();
            for (ImageDTO image:listImage){
                byte[] fileContent = image.getData();
                String encodedString = Base64.getEncoder().encodeToString(fileContent);     //Convert image data from byte[] to String
                String sql1 = "SET datestyle = ymd;";
                String sql2 = "INSERT INTO image(id, image_data, id_task, type_of_tumor, confirm_date, status)"+
                        "VALUES ('"+UUID.randomUUID()+"','"+encodedString+"','"+taskID+"','"+image.getType()+"','"+dtf.format(now)+"', 'Active');";
                PreparedStatement statement = connection.prepareStatement(sql2);
                stmt.execute(sql1);
                stmt.executeUpdate(sql2);
                connection.commit();
            }
            stmt.close();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    /*Update new predict information of image to database*/
    public void updateTaskImage(ArrayList<ImageDTO> listImage){
        Connection connection = c.JdbcConnection();
        Statement stmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try{
            stmt = connection.createStatement();
            for (ImageDTO image:listImage){
                String sql1 = "SET datestyle = ymd;";
                String sql2 = "UPDATE image set type_of_tumor='"+ image.getType() +"', confirm_date='"+dtf.format(now)+"' WHERE id='"+image.getImageId()+"';";
                PreparedStatement statement = connection.prepareStatement(sql2);
                stmt.execute(sql1);
                stmt.executeUpdate(sql2);
                connection.commit();
            }
            stmt.close();
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }




}
