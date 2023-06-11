package e.amil.e_amil;

import static e.amil.e_amil.login.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    private TextView profil, help, about, fedback, keluar;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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


        view = inflater.inflate(R.layout.fragment_profile, container, false);
        profil = view.findViewById(R.id.profil);
        help = view.findViewById(R.id.help);
        about = view.findViewById(R.id.about);
        fedback = view.findViewById(R.id.fedback);
        keluar = view.findViewById(R.id.logout);

        profil.setOnClickListener(this);
        help.setOnClickListener(this);
        about.setOnClickListener(this);
        fedback.setOnClickListener(this);
        keluar.setOnClickListener(this);
        return view;
    }



    FirebaseUser user = auth.getCurrentUser();
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profil:
                getActivity().startActivity(new Intent(getActivity(), profil_profil.class));
                break;

            case R.id.help:
                getActivity().startActivity(new Intent(getActivity(), help_profil.class));
                break;

            case R.id.fedback:
                getActivity().startActivity(new Intent(getActivity(), fedback_profil.class));
                break;

            case R.id.about:
                getActivity().startActivity(new Intent(getActivity(), about_profil.class));
                break;
            case R.id.logout:
                getActivity().startActivity(new Intent(getActivity(), login.class));
                auth.signOut();
                break;

        }

    }
}



