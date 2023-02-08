package com.example.myapplication;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private Connection connection;

    // For Amazon Postgresql
     //private final String host = "cpanel20.mserwis.pl";

    // For Google Cloud Postgresql
    //private final String host = "35.44.16.169";

    // For Local PostgreSQL
    private final String host = "10.26.0.16";

    private final String database = "apkh_meeting_voicerecord";
    private final int port = 5432;
    private final String user = "apkh@localhost";
    private final String pass = "123456";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
}
