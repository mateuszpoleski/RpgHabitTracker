package habitapp.example.rpghabittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
//import android.example.rpghabittracker.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class activity_profile extends AppCompatActivity {

    TextView lvlTw,nameTw,expTw,goldTw;
    TextView skillTw,bExpTw,bGoldTw,bMemeTw,habitsDoneTw,ToDosTw,goldEarnedTw,expEarnedTw;
    ImageView bonusExpBtn,bonusGoldBtn,bonusMemeBtn;
    ProgressBar progressBar;
    ImageView userIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onImageClick();
        loadMainData();
    }

    private void loadMainData() {
        String lvl = "Lvl. "+ getDefaults("Lvl",activity_profile.this);
        int progAct = Integer.parseInt(getDefaults("Exp",activity_profile.this));
        int progMax = 50 + (Integer.parseInt(getDefaults("Lvl",activity_profile.this))-1)*5;
        String exp = "Exp. "+Integer.toString(progAct)+"/"+Integer.toString(progMax);
        String gold = getDefaults("Gold",activity_profile.this);
        String name = getDefaults("userName",activity_profile.this);
        //
        String skillpts = "Available skill points: "+ getDefaults("skillPts",activity_profile.this);
        String bExp = "Bonus Exp. - Lvl."+ getDefaults("bonusExp",activity_profile.this);
        String bGold = "Bonus Gold - Lvl."+ getDefaults("bonusGold",activity_profile.this);
        String bMeme = "memePts - Lvl."+ getDefaults("bonusMemePoints",activity_profile.this);
        String habitsDone = "Habits done: "+ getDefaults("habitsDoneCtr",activity_profile.this);
        String ToDosDone = "ToDo's: "+ getDefaults("ToDosDoneCtr",activity_profile.this);
        String goldEarned = "Gold earned: "+ getDefaults("totalGoldEarned",activity_profile.this);
        String expGained = "Exp gained: "+ getDefaults("totalExpEarned",activity_profile.this);

        skillTw = findViewById(R.id.profile_skillpoints);
        bExpTw = findViewById(R.id.profile_bonusexp);
        bGoldTw = findViewById(R.id.profile_bonusgold);
        bMemeTw = findViewById(R.id.profile_bonusmeme);
        habitsDoneTw = findViewById(R.id.profile_habitsdone);
        ToDosTw = findViewById(R.id.profile_todosdone);
        goldEarnedTw = findViewById(R.id.profile_goldearned);
        expEarnedTw = findViewById(R.id.profile_expearned);
        bonusExpBtn = findViewById(R.id.profile_btn_exp);
        bonusGoldBtn = findViewById(R.id.profile_btn_gold);
        bonusMemeBtn = findViewById(R.id.profile_btn_meme);

        skillTw.setText(skillpts);
        bExpTw.setText(bExp);
        bGoldTw.setText(bGold);
        bMemeTw.setText(bMeme);
        habitsDoneTw.setText(habitsDone);
        ToDosTw.setText(ToDosDone);
        goldEarnedTw.setText(goldEarned);
        expEarnedTw.setText(expGained);

        bonusExpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int skillsCtr = Integer.parseInt(getDefaults("skillPts",activity_profile.this));
                int bonus = Integer.parseInt(getDefaults("bonusExp",activity_profile.this));
                if(skillsCtr == 0){
                    Toast.makeText(activity_profile.this,"You don't have enought skill points.",Toast.LENGTH_LONG).show();
                }
                else{
                    setDefaults("bonusExp",Integer.toString(bonus+1),activity_profile.this);
                    setDefaults("skillPts",Integer.toString(skillsCtr-1),activity_profile.this);
                    bExpTw.setText("Bonus Exp. - Lvl."+Integer.toString(bonus+1));
                    skillTw.setText("Available skill points: "+Integer.toString(skillsCtr-1));
                }
            }
        });
        bonusGoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int skillsCtr = Integer.parseInt(getDefaults("skillPts",activity_profile.this));
                int bonus = Integer.parseInt(getDefaults("bonusGold",activity_profile.this));
                if(skillsCtr == 0){
                    Toast.makeText(activity_profile.this,"You don't have enought skill points.",Toast.LENGTH_LONG).show();
                }
                else{
                    setDefaults("bonusGold",Integer.toString(bonus+1),activity_profile.this);
                    setDefaults("skillPts",Integer.toString(skillsCtr-1),activity_profile.this);
                    bGoldTw.setText("Bonus Gold - Lvl."+Integer.toString(bonus+1));
                    skillTw.setText("Available skill points: "+Integer.toString(skillsCtr-1));
                }
            }
        });
        bonusMemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int skillsCtr = Integer.parseInt(getDefaults("skillPts",activity_profile.this));
                int bonus = Integer.parseInt(getDefaults("bonusMemePoints",activity_profile.this));
                if(skillsCtr == 0){
                    Toast.makeText(activity_profile.this,"You don't have enought skill points.",Toast.LENGTH_LONG).show();
                }
                else{
                    setDefaults("bonusMemePoints",Integer.toString(bonus+1),activity_profile.this);
                    setDefaults("skillPts",Integer.toString(skillsCtr-1),activity_profile.this);
                    bMemeTw.setText("memePts - Lvl."+Integer.toString(bonus+1));
                    skillTw.setText("Available skill points: "+Integer.toString(skillsCtr-1));
                }
            }
        });

        //
        lvlTw = findViewById(R.id.profile_tw_lvl);
        nameTw = findViewById(R.id.profile_tw_name);
        expTw = findViewById(R.id.profile_tw_exp);
        goldTw = findViewById(R.id.profile_tw_gold);
        progressBar = findViewById(R.id.profile_prog_bar);
        lvlTw.setText(lvl);
        nameTw.setText(name);
        expTw.setText(exp);
        goldTw.setText(gold);
        progressBar.setProgress(progAct);
        progressBar.setMax(progMax);

        String tag = getDefaults("picTag",activity_profile.this);
        if (tag != null){
            if(tag.equals("female")) {
                userIv.setImageResource(R.mipmap.ic_usericon_female_layer);
            }
            else{
                userIv.setImageResource(R.mipmap.ic_usericon_male_layer);
            }
        }

    }
    public void onImageClick(){
        userIv = findViewById(R.id.profile_iw_user);
        userIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = userIv.getTag().toString();
                if(tag.equals("male")) {
                    userIv.setImageResource(R.mipmap.ic_usericon_female_layer);
                    userIv.setTag("female");
                    setDefaults("picTag","female",activity_profile.this);
                }
                else{
                    userIv.setImageResource(R.mipmap.ic_usericon_male_layer);
                    userIv.setTag("male");
                    setDefaults("picTag","male",activity_profile.this);

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
