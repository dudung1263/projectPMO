package e.amil.e_amil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import e.amil.e_amil.R;

public class fragment_prayer_times extends Fragment {

    private ListView listView;

    private String[] prayerTimes = {
            "Subuh - 04:30",
            "Dzuhur - 12:30",
            "Ashar - 15:30",
            "Maghrib - 18:30",
            "Isya - 20:30"
    };

    public fragment_prayer_times() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prayer_times, container, false);
        listView = rootView.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, prayerTimes);
        listView.setAdapter(adapter);
        return rootView;
    }
}
