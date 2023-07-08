package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pilihzakat_admin extends AppCompatActivity {

    Button kembali_pilzakatadmin, fitrah_pilzakatadmin, mal_pilzakatadmin;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihzakat_admin);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        kembali_pilzakatadmin = findViewById(R.id.backpilih_zakatadmin);
        fitrah_pilzakatadmin = findViewById(R.id.datazakatfitrahadmin);
        mal_pilzakatadmin = findViewById(R.id.datazakatmaladmin);

        kembali_pilzakatadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onBackPressed();}
        });


        fitrah_pilzakatadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pilihzakat_admin.this, listdata_zakat.class));
            }
        });

        mal_pilzakatadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pilihzakat_admin.this,listdata_zakatmal.class));
            }
        });






    }
}