package e.amil.e_amil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listdata_sodakoh extends AppCompatActivity {


    private RecyclerView recyclerview;
    RecyclerViewSodakoh adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<data_sodakoh> dataSodakoh;

    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata_sodakoh);

        recyclerview= findViewById(R.id.datalist);

        GetData("");

        searchView = findViewById(R.id.etSearch);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    GetData(s.toString());
                }else {
                    adapter.getFilter().filter(s);
                }

            }
        });





        MyRecycleView();

    }
    private void GetData(String data){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSodakoh = new ArrayList<>();
                        dataSodakoh.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            data_sodakoh mahasiswa = snapshot.getValue(data_sodakoh.class);

                            mahasiswa.setKey(snapshot.getKey());
                            dataSodakoh.add(mahasiswa);
                        }
                        adapter = new RecyclerViewSodakoh(dataSodakoh, listdata_sodakoh.this);
                        adapter.notifyDataSetChanged();

                        recyclerview.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getApplicationContext(), "Data Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        Log.e("Crud", error.getDetails() + " " + error.getMessage());

                    }

                });
    }
    private void MyRecycleView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);

        DividerItemDecoration ItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerview.addItemDecoration(ItemDecoration);
    }

}
