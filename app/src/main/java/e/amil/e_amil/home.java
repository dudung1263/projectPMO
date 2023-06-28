package e.amil.e_amil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;

import java.util.Timer;
import java.util.TimerTask;

public class home extends Fragment implements View.OnClickListener {

    Button btnzakat, btninfaq, btnsodakoh, btnpenyaluranzakat, btnpenyaluraninfaq;
    View view ;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ImageSlider imageslider;

        private ImageView imageView;
        private int[] imageResources = {R.drawable.slidezakat, R.drawable.slideinfaq, R.drawable.slidesodakoh};
        private int currentPage = 0;
        private Timer timer;
        private final long DELAY_MS = 5; // Delay in milliseconds before task is to be executed
        private final long PERIOD_MS = 3000; // Time interval in milliseconds between successive task executions.


    public home() {


    }


    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home, container, false);
        btnzakat = view.findViewById(R.id.datazakat_home);
        btninfaq = view.findViewById(R.id.datainfaq_home);
        btnpenyaluranzakat = view.findViewById(R.id.penyalurandatazakat_home);
        btnpenyaluraninfaq = view.findViewById(R.id.penyalurandatainfaq_home);

        btnzakat.setOnClickListener(this);
        btninfaq.setOnClickListener(this);
        btnpenyaluranzakat.setOnClickListener(this);
        btnpenyaluraninfaq.setOnClickListener(this);


        imageView = view.findViewById(R.id.imageView);

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
        switch (v.getId()){
            case R.id.datazakat_home:
                getActivity().startActivity(new Intent(getActivity(), data_zakat.class));
                break;

            case R.id.datainfaq_home:
                getActivity().startActivity(new Intent(getActivity(), data_infaqq.class));
                break;

            case R.id.penyalurandatazakat_home:
                getActivity().startActivity(new Intent(getActivity(), penyaluran_zakat.class));
                break;

            case R.id.penyalurandatainfaq_home:
            getActivity().startActivity(new Intent(getActivity(), penyaluran_infaq.class));
                break;

        }

    }

    @Override
    public void onDestroyView(){super.onDestroyView();}






}