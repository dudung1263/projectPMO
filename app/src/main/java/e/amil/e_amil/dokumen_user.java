package e.amil.e_amil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dokumen_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dokumen_user extends Fragment {

    private ImageView imageView;
    private int[] imageResources = {R.drawable.img_14, R.drawable.img_16, R.drawable.img_12};
    private final long DELAY_MS = 8; // Delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 3000; // Time interval in milliseconds between successive task executions.



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    private int currentPage = 0;
    private Timer timer;

    private CardView gambarzakat, gambarinfak, jam;


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

        gambarzakat = view.findViewById(R.id.datazakat_user);
        jam = view.findViewById(R.id.jadwalsholat_dokumen);


        gambarzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), keterangan_zakat.class);
                startActivity(intent);
            }
        });

        jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), jadwalsholat.class);
                startActivity(intent);
            }
        });





        // Auto start image slider

        imageView = view.findViewById(R.id.image_dokumenuser);
        autoStartImageSlider();
        return view;
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