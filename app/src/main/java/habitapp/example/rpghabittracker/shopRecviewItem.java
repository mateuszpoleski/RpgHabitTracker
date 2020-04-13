package habitapp.example.rpghabittracker;

import android.widget.Toast;

public class shopRecviewItem {
    private String name;
    private String date;
    private int cost;

    public shopRecviewItem(String name,int cost){
        this.name = name;
        this.cost = cost;
        this.date = "";
    }
    public shopRecviewItem(String name,int cost,String date){
        this.name = name;
        this.cost = cost;
        this.date = date;
    }
    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public int getCost(){
        return cost;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
