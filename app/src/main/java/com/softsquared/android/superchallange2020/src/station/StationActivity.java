package com.softsquared.android.superchallange2020.src.station;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import com.softsquared.android.superchallange2020.R;
        import com.softsquared.android.superchallange2020.src.BaseActivity;
        import com.softsquared.android.superchallange2020.src.curation.CurationActivity;
        import com.softsquared.android.superchallange2020.src.seat_choice.SeatChoiceActivity;

public class StationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.activity_station_tv_select:
                Intent intent = new Intent(getApplicationContext(), SeatChoiceActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
