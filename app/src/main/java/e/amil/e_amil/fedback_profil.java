package e.amil.e_amil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class fedback_profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fedback_profil);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}