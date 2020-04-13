package habitapp.example.rpghabittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.example.rpghabittracker.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_habits_stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_stats);

        getSupportActionBar().setTitle("Stats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView twEff = findViewById(R.id.habits_stats_eff);
        Button btnBack = findViewById(R.id.habits_stats_button);
        int habitsDone = Integer.parseInt(getDefaults("habitsDoneCtr",activity_habits_stats.this));
        int habitsShouldBeDone = Integer.parseInt(getDefaults("habitsOverallCtr",activity_habits_stats.this));

        int percents = 0;
        if (habitsShouldBeDone != 0) percents = habitsDone*100/habitsShouldBeDone;
        String print = Integer.toString(percents)+"%";

        twEff.setText(print);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_habits_stats.this,activity_habits.class);
                startActivity(intent);
            }
        });

    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
