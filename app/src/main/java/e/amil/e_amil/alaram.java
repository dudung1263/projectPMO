package e.amil.e_amil;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class alaram extends AppCompatActivity {

    private int jam,menit;
    private TimePicker timePicker;
    private Button btnsimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaram);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        timePicker = findViewById(R.id.timePicker);
        btnsimpan = findViewById(R.id.btnalaram);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                jam = hourOfDay;
                menit = minute;
            }
        });

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(alaram.this, "Set Alaram " + jam + " : "+ menit, Toast. LENGTH_SHORT).show();
                setTimer();
                notification();
            }
        });


    }

    private void notification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Alaram Rimender";
            String description = "Hey Ayo Sholat";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("Notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setTimer() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar_now = Calendar.getInstance();

        calendar.setTime(date);
        calendar_now.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY,  jam);
        calendar.set(calendar.MINUTE, menit);
        calendar.set(Calendar.SECOND, 0);

        if(calendar.before(calendar_now)){
            calendar.add(Calendar.DATE,1);
        }
        Intent i = new Intent(alaram.this, MyBraodcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alaram.this, 0, i ,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
    }
}
