package e.amil.e_amil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Lupa_sandi extends AppCompatActivity {


    private EditText txtemail;
    private Button btnlupasandi;
    private String email;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_sandi);


        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        auth = FirebaseAuth.getInstance();


        txtemail = findViewById(R.id.lupasandi);
        btnlupasandi = findViewById(R.id.btnlupasandi);


        btnlupasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }


    private void validateData() {
        email = txtemail.getText().toString();
        if (email.isEmpty()){
            txtemail.setError("Required");
        }else {
            btnlupasandi();
        }
    }

    private void btnlupasandi() {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Lupa_sandi.this, "check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Lupa_sandi.this, login.class));
                            finish();
                        }else {
                            Toast.makeText(Lupa_sandi.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


