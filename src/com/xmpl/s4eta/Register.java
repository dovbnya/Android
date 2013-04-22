package com.xmpl.s4eta;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Электроник
 * Date: 17.04.13
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class Register extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final EditText edit = (EditText) findViewById(R.id.editText);
        final EditText edit1 = (EditText) findViewById(R.id.editText1);
        final TextView validate = (TextView) findViewById(R.id.textView);
        final TextView validateLogin= (TextView) findViewById(R.id.textView1);
        Button but1 = (Button) findViewById(R.id.button);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login= edit.getText().toString();
                String password= edit1.getText().toString();
                if(login.length()==0 || password.length()==0){
                    validate.setVisibility(View.VISIBLE);
                    return;
                }
                SQLiteDatabase db = openOrCreateDatabase("s4eta", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS users (login VARCHAR, password VARCHAR);");
                Cursor c=db.rawQuery("SELECT * FROM users WHERE login='"+login+"'", null);
                boolean b = c.moveToFirst();
                if(b){
                    validateLogin.setVisibility(View.VISIBLE);
                    return;
                }
                db.execSQL("CREATE TABLE IF NOT EXISTS users (login VARCHAR, password VARCHAR);");
                db.execSQL("INSERT INTO users ('login', 'password') VALUES ('"+login+"', '"+password+"');");
                c.close();
                db.close();
                Intent intent1 = new Intent(Register.this, List.class);
                intent1.putExtra("user", login);
                startActivity(intent1);
                finish();
            }
        });
    }
}
