package e.amil.e_amil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dokumen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dokumen extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int[] imageResources = {R.drawable.img_14, R.drawable.img_16, R.drawable.img_12};

    Button Btndatazakat_data,  Btndatainfaq_data, Btndatasodakoh_data, Btnsholat ;

    private ImageView imageView;
    private int currentPage = 0;
    private Timer timer;

    private final long DELAY_MS =8; // Delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 3000; // Time interval in milliseconds between successive task executions.



    View view;

    public dokumen() {
        // Required empty public constructor
    }


    public static dokumen newInstance(String param1, String param2) {
        dokumen fragment = new dokumen();
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
        view = inflater.inflate(R.layout.fragment_dokumen, container, false);
        Btndatazakat_data = view.findViewById(R.id.datazakat_dokumen);
        Btndatainfaq_data = view.findViewById(R.id.datainfaq_dokumen);
        Btndatasodakoh_data = view.findViewById(R.id.datasodakoh_dokumen);
        Btnsholat = view.findViewById(R.id.sholat);



        Btndatazakat_data.setOnClickListener(this);
        Btndatasodakoh_data.setOnClickListener(this);
        Btndatainfaq_data.setOnClickListener(this);
        Btnsholat.setOnClickListener(this);


        imageView = view.findViewById(R.id.imageView_dokumen);

        // Auto start image slider
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datazakat_dokumen:
                getActivity().startActivity(new Intent(getActivity(), listdata_zakat.class));
                break;

            case R.id.datainfaq_dokumen:
                getActivity().startActivity(new Intent(getActivity(), listdata_infaq.class));
                break;

            case R.id.datasodakoh_dokumen:
                getActivity().startActivity(new Intent(getActivity(), lisdata_sodakoh.class));
                break;

            case R.id.sholat:
                getActivity().startActivity(new Intent(getActivity(), jadwalsholat.class));
                break;




        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
