package habitapp.example.rpghabittracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.example.rpghabittracker.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView lvlTw,nameTw,expTw,goldTw;
    ProgressBar progressBar;
    ImageView userIv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onImageClick();

        String userName = getDefaults("userName",MainActivity.this);
        if(userName == null){
            firstStart();
        }
        loadMainData();
        //startDailyCheck();
    }

    private void loadMainData() {
        String lvl = "Lvl. "+ getDefaults("Lvl",MainActivity.this);
        int progAct = Integer.parseInt(getDefaults("Exp",MainActivity.this));
        int progMax = 50 + (Integer.parseInt(getDefaults("Lvl",MainActivity.this))-1)*5;
        String exp = "Exp. "+Integer.toString(progAct)+"/"+Integer.toString(progMax);
        String gold = getDefaults("Gold",MainActivity.this);
        String name = getDefaults("userName",MainActivity.this);
        lvlTw = findViewById(R.id.main_tw_lvl);
        nameTw = findViewById(R.id.main_tw_name);
        expTw = findViewById(R.id.main_tw_exp);
        goldTw = findViewById(R.id.main_tw_gold);
        progressBar = findViewById(R.id.main_prog_bar);
        lvlTw.setText(lvl);
        nameTw.setText(name);
        expTw.setText(exp);
        goldTw.setText(gold);
        progressBar.setProgress(progAct);
        progressBar.setMax(progMax);

        String tag = getDefaults("picTag",MainActivity.this);
        if (tag != null){
            if(tag.equals("female")) {
                userIv.setImageResource(R.mipmap.ic_usericon_female_layer);
            }
            else{
                userIv.setImageResource(R.mipmap.ic_usericon_male_layer);
            }
        }

    }

    private void firstStart() {
        startDialog();
        setStartingVar();
        //startDailyCheck();
    }

    private void setStartingVar() {
        setDefaults("Lvl",Integer.toString(1),MainActivity.this);
        setDefaults("Exp",Integer.toString(0),MainActivity.this);
        setDefaults("Gold",Integer.toString(0),MainActivity.this);
        setDefaults("skillPts",Integer.toString(0),MainActivity.this);
        setDefaults("bonusExp",Integer.toString(0),MainActivity.this);
        setDefaults("bonusGold",Integer.toString(0),MainActivity.this);
        setDefaults("bonusMemePoints",Integer.toString(0),MainActivity.this);
        setDefaults("memePoints",Integer.toString(0),MainActivity.this);
        setDefaults("bonusExpLvl",Integer.toString(0),MainActivity.this);
        setDefaults("BonusGoldLvl",Integer.toString(0),MainActivity.this);
        setDefaults("habitsDoneCtr",Integer.toString(0),MainActivity.this);
        setDefaults("habitsOverallCtr",Integer.toString(0),MainActivity.this);
        setDefaults("ToDosDoneCtr",Integer.toString(0),MainActivity.this);
        setDefaults("totalGoldEarned",Integer.toString(0),MainActivity.this);
        setDefaults("totalExpEarned",Integer.toString(0),MainActivity.this);
        setDefaults("totalItemBought",Integer.toString(0),MainActivity.this);
    }

    private void startDialog(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.main_dialog_start,null);
        final EditText userName = mView.findViewById(R.id.main_dialog_start_entr);
        Button btnContinue = mView.findViewById(R.id.main_dialog_start_btn);
        a_builder.setView(mView);
        final AlertDialog dialog = a_builder.create();
        dialog.show();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUserName = userName.getText().toString();
                setDefaults("userName",getUserName,MainActivity.this);
                TextView nameTw = findViewById(R.id.main_tw_name);
                nameTw.setText(getUserName);
                dialog.cancel();
                Toast.makeText(MainActivity.this,"Hello "+ getUserName+"!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startDailyCheck(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,3);
        calendar.set(Calendar.MINUTE,1);
        calendar.set(Calendar.SECOND,1);


        Intent intent = new Intent(getApplicationContext(),DailyCheck.class);
        intent.setAction("DAILY_CHECK");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY,AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    public void onClick(View view) {
        Intent intent;
        switch(view.getId())
        {
            case R.id.main_button_habits:
                intent = new Intent(MainActivity.this,activity_habits.class);
                startActivity(intent);
                break;
            case R.id.main_button_todo:
                intent = new Intent(MainActivity.this,activity_todo.class);
                startActivity(intent);
                break;
            case R.id.main_button_profile:
                intent = new Intent(MainActivity.this,activity_profile.class);
                startActivity(intent);
                break;
            case R.id.main_button_shop:
                intent = new Intent(MainActivity.this,activity_shop.class);
                startActivity(intent);
                break;
        }
    }

    public void onImageClick(){
        userIv = findViewById(R.id.main_iw_user);
        userIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = userIv.getTag().toString();
                if(tag.equals("male")) {
                    userIv.setImageResource(R.mipmap.ic_usericon_female_layer);
                    userIv.setTag("female");
                    setDefaults("picTag","female",MainActivity.this);
                }
                else{
                    userIv.setImageResource(R.mipmap.ic_usericon_male_layer);
                    userIv.setTag("male");
                    setDefaults("picTag","male",MainActivity.this);

                }
            }
        });
    }


    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
