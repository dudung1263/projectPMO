package e.amil.e_amil;

import static e.amil.e_amil.login.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ikonamil)
                .setTitle(R.string.app_name)
                .setMessage("Apakah Anda Yakin Keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void logout(View view) {
        FirebaseUser user = auth.getCurrentUser();
        Intent intent = new Intent(MainActivity.this, login.class);
        auth.signOut();
        startActivity(intent);
    }
}