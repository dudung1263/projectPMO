package e.amil.e_amil;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity_user extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    String username, email, ID;

    String dataNama, dataUsername, dataEmail, dataPhone, dataAlamat, dataFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        ID = FirebaseAuth.getInstance().getUid();
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");

        getUser();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loadFragment(new dokumen_user());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation_user);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout_user, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.menu_data_user:
                fragment = new dokumen_user();
                break;
            case R.id.menu_Tentang_user:
                fragment = new profile();
                break;
        }
        return loadFragment(fragment);
    }


    private void getUser(){
        FirebaseDatabase.getInstance().getReference("Pengguna").child("userAuth").child(ID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()){
                    DataSnapshot snapshot = task.getResult();
                    dataNama = String.valueOf(snapshot.child("dataNama").getValue());
                    dataAlamat = String.valueOf(snapshot.child("dataAlamat").getValue());
                    dataFoto = String.valueOf(snapshot.child("dataFoto").getValue());
                    dataEmail = String.valueOf(snapshot.child("dataEmail").getValue());
                    dataUsername = String.valueOf(snapshot.child("dataUsername").getValue());
                    dataPhone = String.valueOf(snapshot.child("dataPhone").getValue());
                }else {
                    DataUser user = new DataUser("", username, email, "","","");
                    FirebaseDatabase.getInstance().getReference("Pengguna").child("userAuth").child(ID).setValue(user);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUser();
    }
}