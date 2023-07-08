package e.amil.e_amil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class penyaluran_zakat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyaluran_zakat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }


}