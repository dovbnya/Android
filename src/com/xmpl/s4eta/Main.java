package com.xmpl.s4eta;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.util.ArrayList;

public class Main extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button but1 = (Button) findViewById(R.id.button);
        Button but2 = (Button) findViewById(R.id.button1);
        Button but3 = (Button) findViewById(R.id.button2);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.main,menu);
        super.onCreateContextMenu(menu, v, menuInfo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item1){
        }
        return super.onContextItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button){
            Animation anim1 = AnimationUtils.loadAnimation(Main.this, R.anim.anim);
            Button but1 = (Button) findViewById(R.id.button);
            but1.startAnimation(anim1);
            final EditText e = (EditText) findViewById(R.id.editText);
            final String login=e.getText().toString();
            final EditText e1 = (EditText) findViewById(R.id.editText1);
            final String password=e1.getText().toString();
            final TextView validate= (TextView) findViewById(R.id.textView);
            ArrayList<String> users = new ArrayList<String>();
            SQLiteDatabase db = openOrCreateDatabase("s4eta", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS users (login VARCHAR, password VARCHAR);");
            Cursor c=db.rawQuery("SELECT * FROM users WHERE login='"+login+"' and password='"+password+"'", null);
            boolean b = c.moveToFirst();
            if(!b){
                validate.setVisibility(View.VISIBLE);
                return;
            }
            String user = c.getString(c.getColumnIndex("login"));
            c.close();
            db.close();
            Intent intent1 = new Intent(Main.this, List.class);
            intent1.putExtra("user", user);
            startActivity(intent1);
            finish();
        }
        if(view.getId()==R.id.button1){
            Animation anim1 = AnimationUtils.loadAnimation(Main.this, R.anim.anim);
            Button but2 = (Button) findViewById(R.id.button1);
            but2.startAnimation(anim1);
            Intent intent1 = new Intent(Main.this, Register.class);
            startActivity(intent1);
        }
        if(view.getId()==R.id.button2){
            Animation anim1 = AnimationUtils.loadAnimation(Main.this, R.anim.anim);
            Button but3 = (Button) findViewById(R.id.button2);
            but3.startAnimation(anim1);
            finish();
        }
    }
}
