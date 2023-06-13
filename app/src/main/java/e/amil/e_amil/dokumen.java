package e.amil.e_amil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class dokumen extends Fragment implements View.OnClickListener {

    Button Btndatazakat_data,  Btndatainfaq_data, Btndatasodakoh_data;

    View view;

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
