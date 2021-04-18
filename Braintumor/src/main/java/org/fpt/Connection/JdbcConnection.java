package org.fpt.Connection;


import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {
    public Connection JdbcConnection(){
        Connection c = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://128.199.122.228:5432/BrainTumor",
                    "postgres", "docker");
            c.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Database connected successfully");
        return c;
    }
}
