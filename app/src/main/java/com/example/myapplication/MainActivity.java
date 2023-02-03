package com.example.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private static String host = "localhost";
    private static String port = "3306";
    private static String Classes = "com.mysql.jdbc.Driver";
    private static String database = "apkh_meeting_voicerecord";
    private static String uname = "apkh@localhost";
    private static String pass = "UDu#3u6KrB!ChM9fdn$T*4EVC";
    private static String ip = "83.19.110.113";
    //private static String url = "jdbc:jtds:sqlserver://"+host+":"+port+"/"+database;
    private static String URL = "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+uname+";password="+pass+";";

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startConn(View view) {
//        ActivityCompat.requestPermissions(this,new String[]{INTERNET}, PackageManager.PERMISSION_GRANTED);
//
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(URL, uname, pass);
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Connected no", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Connected fail", Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        }

//        URL url = null;
//        try {
//            url = new URL(URL);
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            http.setRequestMethod("GET");
//            http.setDoOutput(true);
//            http.setRequestProperty("Content-Type", "application/json");
//            int responseCode = http.getResponseCode();
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
//                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Connected no", Toast.LENGTH_SHORT).show();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        }
    }
}