package habitapp.example.rpghabittracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class activity_habits extends AppCompatActivity {

    private ArrayList<habitsRecviewItem> data;

    private RecyclerView mRecyclerView;
    private habitsRecAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        getSupportActionBar().setTitle("Habits");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String info = getDefaults("habitsInfoShown",activity_habits.this);
        if(info == null){
            setDefaults("habitsInfoShown","true",activity_habits.this);
            startDialog();
        }

        createData();
        buildRecyclerView();

    }


    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }
    public void insertItem(int position,String name,String diff,int[] days){
        data.add(position,new habitsRecviewItem(name,diff,days));
        mAdapter.notifyItemInserted(position);
    }
    public void delItem(int position){
        data.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public void createData() {
        getData();
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.habits_rec_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new habitsRecAdapter(data);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new habitsRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeHabitDialog(position,mAdapter);
            }

            @Override
            public void onButtonClick(int position) {
                data.get(position).setIsClicked(true);
                String diff = data.get(position).getDif();
                int exp,gold;
                if(diff.equals("Easy")){
                    exp = 7;
                    gold = 10;
                }
                else if(diff.equals("Medium")){
                    exp = 9;
                    gold = 15;
                }
                else{
                    exp = 12;
                    gold = 20;
                }
                exp = getExp(exp);
                gold = getGold(gold);
                Toast.makeText(activity_habits.this,"+ "+Integer.toString(exp)+" Experience"+ "\n" +
                                                                    "+ "+Integer.toString(gold)+" Gold",Toast.LENGTH_SHORT).show();
                expUpdate(exp);
                goldUpdate(gold);
                //
                int streak = data.get(position).getStreak();
                int best = data.get(position).getBest();
                int done = data.get(position).getDoneCtr();
                data.get(position).setStreak(streak+1);
                data.get(position).setBest(Math.max(streak+1,best));
                data.get(position).setDoneCtr(done+1);

                mAdapter.notifyItemChanged(position);
            }
        });
    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("habits list",json);
        editor.apply();
    }
    public void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("habits list",null);
        Type type = new TypeToken<ArrayList<habitsRecviewItem>>() {}.getType();
        data = gson.fromJson(json,type);
        if(data == null){
            data = new ArrayList<>();
        }

    }

    public void onClickHabits(View view) {
        switch(view.getId())
        {
            case R.id.habits_btn_new:
                newHabitDialog();
                saveData();
                break;
            case R.id.habits_btn_stats:
                Intent intent = new Intent(activity_habits.this,activity_habits_stats.class);
                startActivity(intent);
                break;
            case R.id.habits_btn_reload:
                reload();
                saveData();
                break;
            case R.id.habits_btn_summarize:
                summarize();
                saveData();
                break;
        }
    }

    private void newHabitDialog(){

        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity_habits.this);
        final View mView = getLayoutInflater().inflate(R.layout.habit_dialog_newhab,null);
        final EditText habitName = mView.findViewById(R.id.dialog_newhab_habname);
        final RadioGroup diffGroup = mView.findViewById(R.id.dialog_newhab_radio_group);
        final CheckBox habitDay1 = mView.findViewById(R.id.dialog_newhab_box1);
        final CheckBox habitDay2 = mView.findViewById(R.id.dialog_newhab_box2);
        final CheckBox habitDay3 = mView.findViewById(R.id.dialog_newhab_box3);
        final CheckBox habitDay4 = mView.findViewById(R.id.dialog_newhab_box4);
        final CheckBox habitDay5 = mView.findViewById(R.id.dialog_newhab_box5);
        final CheckBox habitDay6 = mView.findViewById(R.id.dialog_newhab_box6);
        final CheckBox habitDay7 = mView.findViewById(R.id.dialog_newhab_box7);
        Button habitCreate = mView.findViewById(R.id.dialog_newhab_btn_create);
        Button habitCancel = mView.findViewById(R.id.dialog_newhab_btn_cancle);
        a_builder.setView(mView);
        final AlertDialog dialog = a_builder.create();
        dialog.show();
        habitCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] days = new int[7];
                if(habitDay1.isChecked()) days[0] = 1; else days[0] = 0;
                if(habitDay2.isChecked()) days[1] = 1; else days[1] = 0;
                if(habitDay3.isChecked()) days[2] = 1; else days[2] = 0;
                if(habitDay4.isChecked()) days[3] = 1; else days[3] = 0;
                if(habitDay5.isChecked()) days[4] = 1; else days[4] = 0;
                if(habitDay6.isChecked()) days[5] = 1; else days[5] = 0;
                if(habitDay7.isChecked()) days[6] = 1; else days[6] = 0;
                //
                int pos = data.size();
                String name = habitName.getText().toString();
                int diffID = diffGroup.getCheckedRadioButtonId();
                RadioButton diffBtn = mView.findViewById(diffID);
                String diff = diffBtn.getText().toString();
                //
                insertItem(pos,name,diff,days);
                dialog.cancel();
            }
        });
        habitCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }
    private void changeHabitDialog(final int pos, final habitsRecAdapter adapter){
        // Setting up vars
        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity_habits.this);
        final View mView = getLayoutInflater().inflate(R.layout.habit_dialog_change,null);
        final EditText habitName = mView.findViewById(R.id.dialog_changehab_habname);
        final EditText newPos = mView.findViewById(R.id.dialog_changehab_pos);
        final RadioGroup diffGroup = mView.findViewById(R.id.dialog_changehab_radio_group);
        final CheckBox habitDay1 = mView.findViewById(R.id.dialog_changehab_box1);
        final CheckBox habitDay2 = mView.findViewById(R.id.dialog_changehab_box2);
        final CheckBox habitDay3 = mView.findViewById(R.id.dialog_changehab_box3);
        final CheckBox habitDay4 = mView.findViewById(R.id.dialog_changehab_box4);
        final CheckBox habitDay5 = mView.findViewById(R.id.dialog_changehab_box5);
        final CheckBox habitDay6 = mView.findViewById(R.id.dialog_changehab_box6);
        final CheckBox habitDay7 = mView.findViewById(R.id.dialog_changehab_box7);
        Button habitChange = mView.findViewById(R.id.dialog_changehab_btn_change);
        Button habitDelate = mView.findViewById(R.id.dialog_changehab_btn_delate);
        Button habitCancel = mView.findViewById(R.id.dialog_changehab_btn_cancle);
        //
        a_builder.setView(mView);
        final AlertDialog dialog = a_builder.create();
        dialog.show();
        // Setting prev data
        habitName.setText(data.get(pos).getName());
        String myDiff = data.get(pos).getDif();
        if(myDiff.equals("Easy")) diffGroup.check(R.id.dialog_changehab_radio_dif_1);
        else if(myDiff.equals("Medium")) diffGroup.check(R.id.dialog_changehab_radio_dif_2);
        else diffGroup.check(R.id.dialog_changehab_radio_dif_3);
        int[] myDays = data.get(pos).getDays();
        if(myDays[0] == 0) habitDay1.setChecked(false);
        if(myDays[1] == 0) habitDay2.setChecked(false);
        if(myDays[2] == 0) habitDay3.setChecked(false);
        if(myDays[3] == 0) habitDay4.setChecked(false);
        if(myDays[4] == 0) habitDay5.setChecked(false);
        if(myDays[5] == 0) habitDay6.setChecked(false);
        if(myDays[6] == 0) habitDay7.setChecked(false);
        newPos.setText(Integer.toString(pos+1));
        // Updating data
        habitChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] days = new int[7];
                if(habitDay1.isChecked()) days[0] = 1; else days[0] = 0;
                if(habitDay2.isChecked()) days[1] = 1; else days[1] = 0;
                if(habitDay3.isChecked()) days[2] = 1; else days[2] = 0;
                if(habitDay4.isChecked()) days[3] = 1; else days[3] = 0;
                if(habitDay5.isChecked()) days[4] = 1; else days[4] = 0;
                if(habitDay6.isChecked()) days[5] = 1; else days[5] = 0;
                if(habitDay7.isChecked()) days[6] = 1; else days[6] = 0;
                //
                int posToUpdate = Integer.parseInt(newPos.getText().toString())-1;
                String name = habitName.getText().toString();
                int diffID = diffGroup.getCheckedRadioButtonId();
                RadioButton diffBtn = mView.findViewById(diffID);
                String diff = diffBtn.getText().toString();
                // Updating position
                if(posToUpdate != pos && posToUpdate >=0 && posToUpdate <= data.size()-1) {
                    int streak = data.get(pos).getStreak();
                    int best = data.get(pos).getBest();
                    int done = data.get(pos).getDoneCtr();
                    int shouldBeDone = data.get(pos).getShouldBeDoneCtr();
                    boolean clicked = data.get(pos).getIsClicked();
                    delItem(pos);
                    insertItem(posToUpdate,name,diff,days);
                    data.get(posToUpdate).setBest(best);
                    data.get(posToUpdate).setStreak(streak);
                    data.get(posToUpdate).setDoneCtr(done);
                    data.get(posToUpdate).setShouldBeDoneCtr(shouldBeDone);
                    data.get(posToUpdate).setIsClicked(clicked);
                    dialog.cancel();
                    for(int i = 0;i <= data.size()-1;i++){
                        adapter.notifyItemChanged(i);
                    }
                }
                else{
                    data.get(pos).setName(name);
                    data.get(pos).setDif(diff);
                    data.get(pos).setDays(days);
                    dialog.cancel();
                    if(posToUpdate != pos) Toast.makeText(activity_habits.this,"That is not a valid position.",Toast.LENGTH_LONG).show();
                    adapter.notifyItemChanged(pos);
                }
            }
        });
        habitCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                adapter.notifyItemChanged(pos);

            }
        });
        habitDelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delItem(pos);
                dialog.cancel();
                adapter.notifyItemChanged(pos);

            }
        });

    }
    private void summarize(){
        //daily check
        Calendar calendar = Calendar.getInstance();
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        dayofweek -=2;
        if(dayofweek < 0) dayofweek = 6;
        int nextDay = (dayofweek+1)%7;
        //
        for(int i = 0;i < data.size();i++){
            habitsRecviewItem currItem = data.get(i);

            //should be done
            if(currItem.getDays()[dayofweek] == 1){
                int habitsDone = Integer.parseInt(getDefaults("habitsDoneCtr",activity_habits.this));
                int allHabits = Integer.parseInt(getDefaults("habitsOverallCtr",activity_habits.this));
                boolean clicked = currItem.getIsClicked();
                setDefaults("habitsOverallCtr",Integer.toString(allHabits+1),activity_habits.this);

                //grey
                if(clicked){
                    setDefaults("habitsDoneCtr",Integer.toString(habitsDone+1),activity_habits.this);
                }
                //green
                else{
                    currItem.setStreak(0);
                }
            }
            //
            if(hour >=0 && hour <= 4){
                if(currItem.getDays()[dayofweek] == 1){
                    currItem.setIsClicked(false);
                    currItem.setShouldBeDoneCtr(currItem.getShouldBeDoneCtr()+1);
                }
                else currItem.setIsClicked(true);
            }
            else{
                if(currItem.getDays()[nextDay] == 1){
                    currItem.setIsClicked(false);
                    currItem.setShouldBeDoneCtr(currItem.getShouldBeDoneCtr()+1);
                }
                else currItem.setIsClicked(true);
            }
            mAdapter.notifyItemChanged(i);
        }
    }
    private void reload(){
        //daily check
        Calendar calendar = Calendar.getInstance();
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        dayofweek -=2;
        if(dayofweek < 0) dayofweek = 6;
        //
        for(int i = 0;i < data.size();i++){
            habitsRecviewItem currItem = data.get(i);

            //
            if(currItem.getDays()[dayofweek] == 1){
                currItem.setIsClicked(false);
                currItem.setShouldBeDoneCtr(currItem.getShouldBeDoneCtr()+1);
            }
            else currItem.setIsClicked(true);
            mAdapter.notifyItemChanged(i);
        }
    }
    //
    private void expUpdate(int exp){
        int myExp = Integer.parseInt(getDefaults("Exp",activity_habits.this));
        int myTotalExp = Integer.parseInt(getDefaults("totalExpEarned",activity_habits.this));
        int lvl = Integer.parseInt(getDefaults("Lvl",activity_habits.this));
        int expToNextLvl = 50 + (lvl-1)*5;
        int skillPts = Integer.parseInt(getDefaults("skillPts",activity_habits.this));
        //
        setDefaults("totalExpEarned",Integer.toString(myTotalExp + exp),activity_habits.this);
        if(myExp + exp < expToNextLvl){
            //Toast.makeText(activity_habits.this,"1 "+myExp + " , "+ exp + " , "+ expToNextLvl,Toast.LENGTH_LONG).show();
            setDefaults("Exp",Integer.toString(myExp + exp),activity_habits.this);
        }
        else{
            //Toast.makeText(activity_habits.this,"2 "+myExp + " , "+ exp + " , "+ expToNextLvl,Toast.LENGTH_LONG).show();
            setDefaults("Lvl",Integer.toString(lvl+1),activity_habits.this);
            setDefaults("Exp",Integer.toString(myExp + exp - expToNextLvl),activity_habits.this);
            setDefaults("skillPts",Integer.toString(skillPts+1),activity_habits.this);
            //
            dialogLvlUp(lvl+1);
        }
    }
    private void goldUpdate(int gold){
        int myGold = Integer.parseInt(getDefaults("Gold",activity_habits.this));
        int myTotalGold = Integer.parseInt(getDefaults("totalGoldEarned",activity_habits.this));
        //
        setDefaults("totalGoldEarned",Integer.toString(myTotalGold + gold),activity_habits.this);
        setDefaults("Gold",Integer.toString(myGold + gold),activity_habits.this);
    }
    private int getExp(int exp){
       int skillBonus = Integer.parseInt(getDefaults("bonusExp",activity_habits.this));
       int lvlBonus = Integer.parseInt(getDefaults("bonusExpLvl",activity_habits.this));
       return exp + skillBonus + lvlBonus;
    }
    private int getGold(int gold){
        int skillBonus = 3 * Integer.parseInt(getDefaults("bonusGold",activity_habits.this));
        int lvlBonus = Integer.parseInt(getDefaults("BonusGoldLvl",activity_habits.this));
        return gold + skillBonus + lvlBonus;
    }
    private void dialogLvlUp(int lvl) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity_habits.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_lvlup_layout,null);
        final TextView whatLvl = mView.findViewById(R.id.dialog_lvlup_lvl);
        final TextView comment = mView.findViewById(R.id.dialog_lvlup_com);
        Button btnContinue = mView.findViewById(R.id.dialog_lvlup_btn);
        a_builder.setView(mView);
        final AlertDialog dialog = a_builder.create();
        dialog.show();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        whatLvl.setText(Integer.toString(lvl));
        if(lvl % 5 == 0){
            comment.setText("From now on you will receive slightly more gold for completing tasks.");
            int bonusGold = Integer.parseInt(getDefaults("BonusGoldLvl",activity_habits.this));
            setDefaults("BonusGoldLvl",Integer.toString(bonusGold+2),activity_habits.this);
        }

    }
    private void startDialog() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity_habits.this);
        View mView = getLayoutInflater().inflate(R.layout.habit_dialog_start,null);
        Button btnContinue = mView.findViewById(R.id.habit_dialog_start_btn);
        a_builder.setView(mView);
        final AlertDialog dialog = a_builder.create();
        dialog.show();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
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
