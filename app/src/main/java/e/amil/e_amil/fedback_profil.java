package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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