package habitapp.example.rpghabittracker;

import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class habitsRecviewItem {
    private String name;
    private String dif;
    private int streak;
    private int best;
    private int doneCtr;
    private int shouldBeDoneCtr;
    private int[] days;
    private boolean isClicked;

    public habitsRecviewItem(String _name,String _dif){
        name = _name;
        dif = _dif;
        streak = 0;
        best = 0;
        doneCtr = 0;
        shouldBeDoneCtr = 0;
        days = new int[]{0,0,0,0,0,0,0};
        isClicked = false;
    }
    public habitsRecviewItem(String _name,String _dif,int[] _days){
        name = _name;
        dif = _dif;
        streak = 0;
        best = 0;
        doneCtr = 0;
        shouldBeDoneCtr = 0;
        days = _days;
        isClicked = false;

        Calendar calendar = Calendar.getInstance();
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        dayofweek -=2;
        if(dayofweek < 0) dayofweek = 6;
        if(_days[dayofweek] == 0) isClicked = true;
        else shouldBeDoneCtr = 1;
    }
    public String getName(){
        return name;
    }
    public String getDif(){
        return dif;
    }
    public int getStreak(){
        return streak;
    }
    public int getBest(){
        return best;
    }
    public int getDoneCtr(){
        return doneCtr;
    }
    public int getShouldBeDoneCtr(){
        return shouldBeDoneCtr;
    }
    public int[] getDays(){
        return days;
    }
    public boolean getIsClicked(){
        return isClicked;
    }
    public void setName(String newName){
        name = newName;
    }
    public void setDif(String newDif){
        dif = newDif;
    }
    public void setStreak(int newStreak){
        streak = newStreak;
    }
    public void setBest(int newBest){
        best = newBest;
    }
    public void setDoneCtr(int newDoneCtr){
        doneCtr = newDoneCtr;
    }
    public void setShouldBeDoneCtr(int newShouldBeDoneCtr){
        shouldBeDoneCtr = newShouldBeDoneCtr;
    }
    public void setDays(int[] newDays){
        days = newDays;
    }
    public void setIsClicked(boolean _isClicked){
        isClicked = _isClicked;
    }
}
