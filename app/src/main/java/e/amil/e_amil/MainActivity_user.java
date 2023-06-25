package e.amil.e_amil;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity_user extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loadFragment(new dokumen());
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
            case R.id.menu_data:
                fragment = new dokumen_user();
                break;
            case R.id.menu_Tentang:
                fragment = new profile();
                break;
        }
        return loadFragment(fragment);
    }


}