package com.example.blackjack21;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button add = null, master = null , start = null;
    public SQLiteDatabase db;



    //ListView 要顯示的內容　改到全域變數
    public static String[] str = {"使用者1","使用者2","使用者3"};
    public int str_index = 0;

    //list使用的變數
    ArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * database preset
         */
        db = openOrCreateDatabase("testdb", Context.MODE_PRIVATE,null);
        String createTable = "CREATE TABLE IF NOT EXISTS " + "test" + "(item VARCHAR(32))";
        db.execSQL(createTable);


        //--------------------動作愈設
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //--------------------

        add = (Button)findViewById(R.id.add);

        start = (Button)findViewById(R.id.start);
        final ListView listview = (ListView) findViewById(R.id.listview);


        //建立add databasse 、 添加進去遊戲室
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //從database裡面檢查 要加入哪一個玩家
                refresh();
                //更新list
                listview.setAdapter(adapter);
            }
        });






        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                str);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"選取第 "+(position +1) +" 個 \n玩家成為莊家："+str[position], Toast.LENGTH_SHORT).show();
                //點集把選到的玩家變成master
                PlayPage.master_num = position;
            }
        });
    }

    public void refresh(){
        //畫面刷新，更新str的內容
        SQLiteDatabase db;
        db = openOrCreateDatabase("testdb", Context.MODE_PRIVATE,null);

        Cursor c =db.rawQuery("SELECT * FROM test" , null);
        if(c.moveToFirst()) {
            do {
                if (c.getString(0).equals("null") == false) {
                    Toast.makeText(MainActivity.this,"" + c.getString(0), Toast.LENGTH_SHORT).show();
                    //提取出來後，更改畫面中listview的內容
                    str[str_index] = c.getString(0);
                    str_index++;
                    str_index = str_index % 3;
                }
            } while (c.moveToNext());


        }
    }

    public void jump(View view){
        Intent iten = new Intent(this,PlayPage.class);
        startActivity(iten);
        finish();
    }

    public void addData(String item)
    {
        ContentValues cv = new ContentValues(1);
        cv.put("item" , item);
        db.insert("test" , null , cv);
    }

    public void reg_menber(View view){
        //這邊從下面的edittext裡面註冊一個使用者id
        EditText edit = (EditText)findViewById(R.id.reg_name);
        Toast.makeText(MainActivity.this,"註冊id：" + edit.getText(), Toast.LENGTH_SHORT).show();
        String reg = "" + edit.getText();
        addData(reg);
    }

}
