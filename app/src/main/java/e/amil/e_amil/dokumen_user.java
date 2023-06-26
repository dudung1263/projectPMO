package e.amil.e_amil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dokumen_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dokumen_user extends Fragment {

    private ImageView imageView;
    private int[] imageResources = {R.drawable.img_14, R.drawable.img_16, R.drawable.img_12};
    private final long DELAY_MS =8; // Delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 3000; // Time interval in milliseconds between successive task executions.

    private BarChart barChart;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    private int currentPage = 0;
    private Timer timer;



    View view;


    public dokumen_user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dokumen_user.
     */
    // TODO: Rename and change types and number of parameters
    public static dokumen_user newInstance(String param1, String param2) {
        dokumen_user fragment = new dokumen_user();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dokumen_user, container, false);

        //Barchart
        barChart = view.findViewById(R.id.barChart);

        setupBarChart();
        loadDataFromFirebase();

        // Auto start image slider

        imageView = view.findViewById(R.id.image_dokumenuser);
        autoStartImageSlider();
        return view;


    }

    private void setupBarChart() {
        // Mengatur deskripsi
        Description description = new Description();
        description.setText("Contoh Grafik Batang");
        barChart.setDescription(description);

        // Mengatur animasi
        barChart.animateY(1000);

        // Mengatur sumbu X
        // ...

        // Mengatur sumbu Y
        // ...
    }


    private void loadDataFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child("Zakat").child("Infak")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<BarEntry> entries = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Ambil nilai dari Firebase
                            int value = snapshot.getValue(Integer.class);

                            // Tambahkan ke daftar entri untuk grafik batang
                            entries.add(new BarEntry(entries.size() + 1, value));
                        }

                        // Buat data set untuk grafik batang
                        BarDataSet dataSet = new BarDataSet(entries, "Data Set");
                        dataSet.setColor(Color.GREEN);

                        // Buat objek data untuk grafik batang
                        BarData barData = new BarData(dataSet);

                        // Atur data ke grafik batang
                        barChart.setData(barData);
                        barChart.invalidate(); // Perbarui tampilan grafik batang
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Penanganan kesalahan saat memuat data dari Firebase
                    }
                });
    }

    private void autoStartImageSlider() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == imageResources.length) {
                    currentPage = 0;
                }
                imageView.setImageResource(imageResources[currentPage++]);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
}