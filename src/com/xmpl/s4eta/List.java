package com.xmpl.s4eta;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Электроник
 * Date: 19.04.13
 * Time: 1:33
 * To change this template use File | Settings | File Templates.
 */
public class List extends Activity {
    private String address;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        refresh();
    }
    public void refresh(){
        String user = getIntent().getExtras().getString("user");
        Button but= (Button) findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userr = getIntent().getExtras().getString("user");
                Intent intent1 = new Intent(List.this, AddCheck.class);
                intent1.putExtra("user", userr);
                startActivity(intent1);
                finish();
            }
        });
        ListView listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        ArrayList<String> numbers = new ArrayList<String>();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, numbers);
        SQLiteDatabase db = openOrCreateDatabase("s4eta", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS allchecks (number VARCHAR, description VARCHAR, login VARCHAR, cost VARCHAR);");
        Cursor c=db.rawQuery("SELECT * FROM allchecks WHERE login='"+user+"'", null);
        boolean b = c.moveToFirst();
        if(!b){
            return;
        }
        if (!c.isAfterLast()) {
            do {
                String number = c.getString(c.getColumnIndex("number"));
                String descr = c.getString(c.getColumnIndex("description"));
                String login = c.getString(c.getColumnIndex("login"));
                String cost = c.getString(c.getColumnIndex("cost"));
                numbers.add(new String(number+";"+descr+";"+login+";"+cost));
            } while (c.moveToNext());
        }
        listView.setAdapter(adapter1);
        c.close();
        db.close();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.main,menu);
        ListView listView = (ListView) findViewById(R.id.listView);
        if (v.getId() == listView.getId()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            address = listView.getItemAtPosition(info.position).toString();
        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item1){
            address=address.substring(0,address.indexOf(";"));
            SQLiteDatabase db = openOrCreateDatabase("s4eta", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS allchecks (number VARCHAR, description VARCHAR, login VARCHAR, cost VARCHAR);");
            db.execSQL("UPDATE allchecks SET cost=cost+1 WHERE number='"+address+"';");
            db.close();
            refresh();
        }
        if(item.getItemId()==R.id.item2){
            address=address.substring(0,address.indexOf(";"));
            SQLiteDatabase db = openOrCreateDatabase("s4eta", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS allchecks (number VARCHAR, description VARCHAR, login VARCHAR, cost VARCHAR);");
            db.execSQL("UPDATE allchecks SET cost=cost-1 WHERE number='"+address+"';");
            db.close();
            refresh();
        }
        return super.onContextItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }
}