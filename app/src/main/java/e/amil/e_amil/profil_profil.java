package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class profil_profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_profil);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}