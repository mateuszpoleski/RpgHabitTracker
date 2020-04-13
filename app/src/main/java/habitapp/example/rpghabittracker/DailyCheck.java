package habitapp.example.rpghabittracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class DailyCheck extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"onRecaive",Toast.LENGTH_LONG).show();
        Log.d("BRTag","onRecaive");

        if (intent.getAction().equals("DAILY_CHECK")) {
            Toast.makeText(context,"inside if",Toast.LENGTH_LONG).show();
            Log.d("BRTag","inside if");
            ArrayList<habitsRecviewItem> data;
            //getData
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences",MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("habits list",null);
            Type type = new TypeToken<ArrayList<habitsRecviewItem>>() {}.getType();
            data = gson.fromJson(json,type);
            if(data == null){
                data = new ArrayList<>();
            }

            //daily check
            Calendar calendar = Calendar.getInstance();
            int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
            dayofweek -=2;
            if(dayofweek < 0) dayofweek = 6;
            int prevDay = dayofweek-1;
            if(prevDay < 0) prevDay = 6;
            //
            for(int i = 0;i < data.size();i++){
                habitsRecviewItem currItem = data.get(i);

                //should be done
                if(currItem.getDays()[prevDay] == 1){
                    int habitsDone = Integer.parseInt(getDefaults("habitsDoneCtr",context));
                    int allHabits = Integer.parseInt(getDefaults("habitsOverallCtr",context));
                    boolean clicked = currItem.getIsClicked();
                    setDefaults("habitsOverallCtr",Integer.toString(allHabits+1),context);

                    //grey
                    if(clicked){
                        setDefaults("habitsDoneCtr",Integer.toString(habitsDone+1),context);
                    }
                    //green
                    else{
                        currItem.setStreak(0);
                    }
                }
                //
                if(currItem.getDays()[dayofweek] == 1){
                    currItem.setIsClicked(false);
                    currItem.setShouldBeDoneCtr(currItem.getShouldBeDoneCtr()+1);
                }
                else currItem.setIsClicked(true);
            }

            //save data
            SharedPreferences.Editor editor = sharedPreferences.edit();
            gson = new Gson();
            json = gson.toJson(data);
            editor.putString("habits list",json);
            editor.apply();
        }



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

