package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;


public class MainActivity extends AppCompatActivity {


    private static final String url = "jdbc:mysql://185.221.108.136:2083/apkh_meeting_voicerecord?max_allowed_packet=5526600";
    private static final String user = "apkh";
    private static final String pass = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread() {
            @Override
            public void run() {
                testDB();
            }
        };
        thread.start();
    }

    public void testDB() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //riverManager.setLoginTimeout(3600);
            Connection con = DriverManager.getConnection(url, user, pass);
            Log.e("qwerty", "Database connection success");
            //String result = "Database connection success\n";
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("select * from table_name");
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//            while(rs.next()) {
//                result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
//                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
//                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
//            }
//            tv.setText(result);
//        } catch (ReplicationErrored er) {
//            er.printStackTrace();
//            Log.e("qwerty", er);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("qwerty", e.toString());
        }

    }
}