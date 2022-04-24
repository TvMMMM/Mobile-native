package vn.edu.greenwich.i_explore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button timeButton;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String btnCreateEventName = "Create event";
        Log.e("Activity Life Cycle", "--- onCreate ---");

        Button btnCreateEvent = findViewById(R.id.btnCreateEvent);
        btnCreateEvent.setText(btnCreateEventName);
        btnCreateEvent.setOnClickListener(btnCreateEvent_Click);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        timeButton = findViewById(R.id.timeButton);

    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

         int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        //default should never happen
        return "";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    private View.OnClickListener vi = new View.OnClickListener() {
        @Override
        public void onClick(View v) { setLocale("vi"); }
    };

    public void setLocale(String lang){
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private View.OnClickListener btnCreateEvent_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Boolean isValid = true;
            TextView eventTitle = findViewById(R.id.eventTitle);
            TextView eventLocation = findViewById(R.id.eventLocation);
            TextView eventReporter = findViewById(R.id.eventReporter);
            TextView eventDescribe = findViewById(R.id.eventDescribe);

            String title = eventTitle.getText().toString();
            String location = eventLocation.getText().toString();
            String reporter = eventReporter.getText().toString();
            String describe = eventDescribe.getText().toString();

//          alert
            String error_notification_title =
                    "Title is required";
            String error_notification_date =
                    "Date cannot be blank";
            String error_notification_reporter =
                    "Reporter is required";

            if(TextUtils.isEmpty(title)){
                isValid = false;
                Toast.makeText(v.getContext(), error_notification_title,
                        Toast.LENGTH_LONG).show();
            }
            if(TextUtils.isEmpty(reporter)){
                isValid = false;
                Toast.makeText(v.getContext(), error_notification_reporter,
                        Toast.LENGTH_LONG).show();
            }
            if (isValid){

                Log.i("Main Activity",title + " - " + location);
                Log.w("Main Activity","This is a warning log.");
                Log.i("Main Activity","This is an information log.");
                Log.d("Main Activity","This is a debug log.");
                Log.v("Main Activity","This is a verbose log.");

                //put data into many pages
                Bundle accountInfo = new Bundle();
                accountInfo.putString("Event Title", title);
                accountInfo.putString("Event Location", location);
                Date currentTime = Calendar.getInstance().getTime();
                accountInfo.putString("Event Date", currentTime.toString());
                accountInfo.putString("Event Reporter", reporter);
                accountInfo.putString("Event Describe", describe);
                Intent mainActivity = new Intent(v.getContext(), MainActivity.class);
                //put Extras for bundle
                mainActivity.putExtras(accountInfo);
                startActivity(mainActivity);
                //finish();
            }
            else {
                Log.e("Main Activity", "This is an error log.");
            }



        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Activity Life Cycle", "--- onStart ---");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Activity Life Cycle", "--- onRestart ---");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Activity Life Cycle", "--- onResume ---");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Activity Life Cycle", "--- onPause ---");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Activity Life Cycle", "--- onStop ---");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Activity Life Cycle", "--- onDestroy ---");
    }

}