package com.example.sampath.reminder.Birthday;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampath.reminder.LoginActivity;
import com.example.sampath.reminder.MainActivity;
import com.example.sampath.reminder.R;
import com.example.sampath.reminder.RegisterActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class to_do_layout extends AppCompatActivity {

    EditText username,birthday;
    DbHelperBirthday myDb;
    Button saveButton,showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_layout);
        myDb = new DbHelperBirthday(this);

        username = (EditText)findViewById(R.id.etBdName);
        birthday = (EditText)findViewById(R.id.etBdDate);
        saveButton = (Button)findViewById(R.id.btnSave);
        showButton = (Button)findViewById(R.id.btnShow);

        AddData();
        showData();

        //calender set
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        final EditText txtDate = (EditText)findViewById(R.id.etBdDate);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepickter = new DatePickerDialog(to_do_layout.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int day) {
                        txtDate.setText(year + "-" + (monthOfYear+1) + "-" + day);
                    }
                },year,month,day
                );
                datepickter.setTitle("select date");
                datepickter.show();
            }
        });
    }

    public void AddData(){

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = username.getText().toString();
                String ubirth = birthday.getText().toString();

                if((TextUtils.isEmpty(uname)) || (TextUtils.isEmpty(ubirth))){
                    Toast.makeText(to_do_layout.this, "Some field is Empty!", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    boolean insertData = myDb.addUser(uname, ubirth);
                    if (insertData == true) {
                        Toast.makeText(to_do_layout.this, "successful entered data!", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(to_do_layout.this,BirthdayActivity.class));

                    } else {
                        Toast.makeText(to_do_layout.this, "something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                CheckedDate();
            }
        });

    }

    public void showData(){
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(to_do_layout.this,BirthdayActivity.class);
                startActivity(intent);
            }
        });
    }

    ////////
    private void CheckedDate(){

        android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
        final String currentDate = (cal.get(android.icu.util.Calendar.YEAR)+"-"+(cal.get(android.icu.util.Calendar.MONTH)+1)+"-"+cal.get(android.icu.util.Calendar.DAY_OF_MONTH)).trim();
        Cursor cursor = myDb.getAllData();
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
