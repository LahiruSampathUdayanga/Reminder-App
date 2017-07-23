package com.example.sampath.reminder.Birthday;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sampath.reminder.R;

import java.util.ArrayList;
import java.util.Calendar;

public class BirthdayActivity extends AppCompatActivity {

    DbHelperBirthday myDb;
    ArrayList theList;
    ArrayAdapter listAdapter;
    Button delete;
    ListView listView;
    ///
    ArrayList list;
    int map[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        listView = (ListView) findViewById(R.id.listView);
        myDb = new DbHelperBirthday(this);


        ///
        list = new ArrayList<>();
        delete = (Button) findViewById(R.id.btnDelete);
        ////

        theList = new ArrayList<>();
        final Cursor data = myDb.getAllData();

        map = new int[data.getCount()];


        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, theList);

        int count = 0;
        while (data.moveToNext()) {
            theList.add(data.getString(1) + " \t\t\t " + data.getString(2));
            listView.setAdapter(listAdapter);
            map[count] = data.getInt(0);
            count++;
        }
        
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray checkedItemPosition = listView.getCheckedItemPositions();

                int itemCount = listView.getCount();

                for(int i = itemCount-1; i>=0; i--){
                    if(checkedItemPosition.get(i)){
                        myDb.deleteData(map[i]+"");
                        listAdapter.remove(theList.get(i));
                    }
                }
                listAdapter.notifyDataSetChanged();
            }
        };
        delete.setOnClickListener(listener);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
                startActivity(new Intent(BirthdayActivity.this, to_do_layout.class));
                finish();
                //dateDifferent();
                break;

        }
    }

   /* public void dateDifferent(){
        Calendar cal = Calendar.getInstance();
        String currentDate = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
        long dif = cal.getTimeInMillis() - System.currentTimeMillis();
        System.out.println(dif);
    }*/

}
