package e.amil.e_amil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

public class mosque extends Fragment {

    public mosque() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mosque, container, false);
        TextView textView = rootView.findViewById(R.id.textView);
        textView.setText("Daftar Masjid Terdekat");
        return rootView;
    }
}
