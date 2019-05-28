package com.example.blackjack21;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

public class PlayPage extends AppCompatActivity {

    public static int master_num;
    int[] score = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);
        //--------------------動作愈設
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //--------------------

        Random ran = new Random();
        long seed = System.currentTimeMillis();
        ran.setSeed(seed);

        TextView ans_show = (TextView)findViewById(R.id.show);
        String reg = "";
        for (int i = 0 ;  i < 3 ; i++)
        {
            int x = (int) (ran.nextInt(21));
            if (x < 2)
            {
                x = 2;
            }
            score[i] = x;
            reg += MainActivity.str[i] + " = " + score[i] + "點\n";
        }

        //自動幫莊家加1點 避免重複的數字
        score[master_num]++;

        //找出最大的點數是誰
        int max = 0;
        for (int i = 0 ; i < 3 ; i++)
        {
            if (max < score[i])
            {
                max = score[i];
            }
        }
        if (max == score[0])
        {
            reg += "\n\n\n 獲勝的是：" + MainActivity.str[0] + "\n\n";
        }else if (max == score[1]){
            reg += "\n\n\n 獲勝的是：" + MainActivity.str[1] + "\n\n";
        }else if (max == score[2]){
            reg += "\n\n\n 獲勝的是：" + MainActivity.str[2] + "\n\n";
        }




        ans_show.setText("" + reg);
    }
}
