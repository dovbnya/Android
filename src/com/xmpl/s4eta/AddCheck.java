package com.xmpl.s4eta;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Электроник
 * Date: 22.04.13
 * Time: 2:25
 * To change this template use File | Settings | File Templates.
 */
public class AddCheck extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_check);
        final EditText edit = (EditText) findViewById(R.id.editText);
        final EditText edit1 = (EditText) findViewById(R.id.editText1);
        final TextView validate = (TextView) findViewById(R.id.textView);
        final TextView validateAddCheck= (TextView) findViewById(R.id.textView1);
        Button but1 = (Button) findViewById(R.id.button);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ME","click button");
                String number= edit.getText().toString();
                String descr= edit1.getText().toString();
                String user = getIntent().getExtras().getString("user");
                if(number.length()==0 || descr.length()==0){
                    validate.setVisibility(View.VISIBLE);
                    return;
                }
                SQLiteDatabase db = openOrCreateDatabase("s4eta", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS  allchecks (number VARCHAR, description VARCHAR, login VARCHAR, cost VARCHAR);");
                Cursor c=db.rawQuery("SELECT * FROM allchecks WHERE number='"+number+"'", null);
                boolean b = c.moveToFirst();
                if(b){
                    validateAddCheck.setVisibility(View.VISIBLE);
                    return;
                }
                db.execSQL("CREATE TABLE IF NOT EXISTS  allchecks (number VARCHAR, description VARCHAR, login VARCHAR, cost VARCHAR);");
                db.execSQL("INSERT INTO allchecks ('number', 'description', 'login', 'cost') VALUES ('"+number+"', '"+descr+"', '"+user+"', '0');");
                c.close();
                db.close();
                Intent intent1 = new Intent(AddCheck.this, List.class);
                intent1.putExtra("user", user);
                startActivity(intent1);
                finish();
            }
        });
    }
}
