package habitapp.example.rpghabittracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.example.rpghabittracker.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class activity_shop extends AppCompatActivity {

    private ArrayList<shopRecviewItem> data;
    private ArrayList<shopRecviewItem> boughtData;

    private RecyclerView mRecyclerView;
    private shopRecAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        getSupportActionBar().setTitle("Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createData();
        buildRecyclerView();

    }

    protected void onPause() {
        super.onPause();
        saveData();
    }
    public void insertItem(int position,String name,int cost){
        data.add(position,new shopRecviewItem(name,cost));
        mAdapter.notifyItemInserted(position);
    }
    public void delItem(int position){
        data.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public void createData() {
        getData();
        getBoughtData();
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.shop_rec_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new shopRecAdapter(data);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new shopRecAdapter.OnItemClickListener() {
            @Override
            public void onButtonBuyClick(int position) {
                shopRecviewItem curr = data.get(position);
                String name = curr.getName();
                int cost = curr.getCost();
                int gold = Integer.parseInt(getDefaults("Gold",activity_shop.this));
                int itemsBought = Integer.parseInt(getDefaults("totalItemBought",activity_shop.this));

                if(gold - cost >= 0){
                    Calendar cal = Calendar.getInstance();
                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());

                    setDefaults("Gold",Integer.toString(gold-cost),activity_shop.this);
                    setDefaults("totalItemBought",Integer.toString(itemsBought+1),activity_shop.this);

                    Toast.makeText(activity_shop.this,"You have bought: "+"\n"
                            +" "+curr.getName() + "\n" +
                            "-"+ Integer.toString(cost)+" gold",Toast.LENGTH_LONG).show();

                    //save to bought
                    boughtData.add(new shopRecviewItem(name,cost,date));
                    saveBoughtData();
                }
                else{
                    Toast.makeText(activity_shop.this,"You don't have enought gold.",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onButtonDelClick(int position) {
                delItem(position);
            }
        });
    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("shop list",json);
        editor.apply();
    }
    public void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("shop list",null);
        Type type = new TypeToken<ArrayList<shopRecviewItem>>() {}.getType();
        data = gson.fromJson(json,type);
        if(data == null){
            data = new ArrayList<>();
        }

    }
    public void saveBoughtData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(boughtData);
        editor.putString("bought list",json);
        editor.apply();
    }

    public void getBoughtData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bought list",null);
        Type type = new TypeToken<ArrayList<shopRecviewItem>>() {}.getType();
        boughtData = gson.fromJson(json,type);
        if(boughtData == null){
            boughtData = new ArrayList<>();
        }

    }

    public void onClickShop(View view) {
        switch(view.getId())
        {
            case R.id.shop_btn_new:
                newItemDialog();
                break;
            case R.id.shop_btn_bought:
                Intent intent = new Intent(activity_shop.this,activity_shop_bought.class);
                startActivity(intent);
                break;
        }
    }

    private void newItemDialog(){

        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity_shop.this);
        final View mView = getLayoutInflater().inflate(R.layout.shop_dialog_new,null);
        final EditText newEt = mView.findViewById(R.id.dialog_newtobuy_name);
        final EditText costEt = mView.findViewById(R.id.dialog_newtobuy_cost);
        Button createBtn = mView.findViewById(R.id.dialog_newtobuy_btn_create);
        Button cancleBtn = mView.findViewById(R.id.dialog_newtobuy_btn_cancle);
        a_builder.setView(mView);
        final AlertDialog dialog = a_builder.create();
        dialog.show();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = data.size();
                String name = newEt.getText().toString();
                String c = costEt.getText().toString();
                int cost = 0;
                if(c.equals("")== false){
                    cost = Integer.parseInt(c);
                }
                if(cost > 0){
                    insertItem(pos,name,cost);
                    dialog.cancel();
                }
                else{
                    Toast.makeText(activity_shop.this,"Cost must be positive.",Toast.LENGTH_LONG).show();
                }

            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    //
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
