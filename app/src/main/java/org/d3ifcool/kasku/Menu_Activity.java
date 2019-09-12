package org.d3ifcool.kasku;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.d3ifcool.kasku.data.DownloadImageTask;



public class Menu_Activity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;

    private static long back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView = (TextView)findViewById(R.id.gagal);
        imageView = (ImageView)findViewById(R.id.background);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        new DownloadImageTask((ImageView)findViewById(R.id.background)).
                execute("https://image.ibb.co/c0t0mx/ba_menu.jpg");

    }

    private static String CALCULATOR_PACKAGE_NAME = "com.android.calculator2";
    private static String CALCULATOR_CLASS_NAME = "com.android.calculator2.Calculator";
    public void gotoCalculator(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName(CALCULATOR_PACKAGE_NAME, CALCULATOR_CLASS_NAME));
        try {

            startActivity(intent);
        } catch (Exception e) {
            CALCULATOR_PACKAGE_NAME = "com.sec.android.app.popupcalculator";
            CALCULATOR_CLASS_NAME = "com.sec.android.app.popupcalculator.Calculator";
            intent.setComponent(new ComponentName(CALCULATOR_PACKAGE_NAME, CALCULATOR_CLASS_NAME));
            try {
                startActivity(intent);
            } catch (Exception e2) {
                Toast.makeText(this, "Tidak tersedia untuk device ini", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if(back + 2000 > System.currentTimeMillis()){
            finishAffinity();
        }else {
            Toast.makeText(getBaseContext(),"Tekan Sekali Lagi Untuk Keluar", Toast.LENGTH_SHORT).show();
            back = System.currentTimeMillis();
        }

    }

    public void gotoKP(View view) {


    }

    public void gotoKK(View view) {
    }

    public void gotoBI(View view) {
    }
}
