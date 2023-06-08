package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class data_infaqq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_infaqq);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}