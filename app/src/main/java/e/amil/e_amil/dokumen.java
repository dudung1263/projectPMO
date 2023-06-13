package e.amil.e_amil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    Button Btndatazakat_data,  Btndatainfaq_data, Btndatasodakoh_data;

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
        Btndatazakat_data.setOnClickListener(this);

        view = inflater.inflate(R.layout.fragment_dokumen, container, false);
        Btndatainfaq_data = view.findViewById(R.id.datainfaq_dokumen);
        Btndatainfaq_data.setOnClickListener(this);

        view = inflater.inflate(R.layout.fragment_dokumen, container, false);
        Btndatasodakoh_data = view.findViewById(R.id.datasodakoh_dokumen);
        Btndatasodakoh_data.setOnClickListener(this);

        return view;

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
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
