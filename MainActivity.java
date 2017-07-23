package com.example.sampath.reminder;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sampath.reminder.Birthday.BirthdayActivity;
import com.example.sampath.reminder.Birthday.DbHelperBirthday;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout,btnBirthday,btnTodo;
    private Session session;
    DbHelperBirthday mydb;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBirthday = (Button)findViewById(R.id.btnBirthday);
        btnBirthday.setOnClickListener(this);

        mydb = new DbHelperBirthday(this);

        /*final Thread thread = new Thread(){
            public void run(){
               try{
                  while(true){
                      CheckedDate();
                      sleep(100);
                  }
               }catch (Exception e){

               }
            }
        };
        Runnable run = new Runnable() {
            @Override
            public void run() {
                CheckedDate();
                System.out.println("****jHYGJHDYGH");
            }
        };*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                CheckedDate();
                System.out.println("************");
            }
        }).start();

        //Logout code
        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
              // alert box
                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("Do you want to Close this App !!!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert !!!");
                alert.show();

            }
        });

    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBirthday:
                startActivity(new Intent(MainActivity.this, BirthdayActivity.class));
                break;
        }
    }

    private void CheckedDate(){

        Calendar cal = Calendar.getInstance();
        final String currentDate = (cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)).trim();
        Cursor cursor = mydb.getAllData();
        String allNames = "";
        cursor.moveToFirst();
        do{
            String saveDate = cursor.getString(2).trim();
            if(saveDate.equals(currentDate)){
               allNames = cursor.getString(1)+" ";
                    ClickMe(allNames);

            }else{

            }
        }while (cursor.moveToNext());
    }


    private void ClickMe(String name){
            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Birthday Notification")
                    .setContentText(name + "Birth today");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(name.length(), mBuilder.build());
    }
}
