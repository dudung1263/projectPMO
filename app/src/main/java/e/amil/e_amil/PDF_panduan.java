package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.joanzapata.pdfview.PDFView;

public class PDF_panduan extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_panduan);

        pdfView = findViewById(R.id.pdfPanduan);

        pdfView.fromAsset("panduan_amil.pdf")
                .swipeVertical(true)
                .load();
    }
}