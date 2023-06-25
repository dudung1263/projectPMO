package e.amil.e_amil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class diagram extends AppCompatActivity {

    private EditText editText;
    private Button calculateButton;
    private TextView resultTextView;
    private PieChart chart;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);

        editText = findViewById(R.id.editText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);
        chart = findViewById(R.id.chart);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZakat();
            }
        });
    }

    private void calculateZakat() {
        double amount = Double.parseDouble(editText.getText().toString());

        double zakat = amount * 0.025; // Hitung zakat sebesar 2.5% dari jumlah harta

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedZakat = decimalFormat.format(zakat);

        resultTextView.setText("Jumlah zakat: " + formattedZakat);

        // Buat data diagram
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) zakat, "Zakat"));
        entries.add(new PieEntry((float) (amount - zakat), "Sisa Harta"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.animateY(1000);
        chart.invalidate();
    }
}
