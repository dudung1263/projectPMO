package e.amil.e_amil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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