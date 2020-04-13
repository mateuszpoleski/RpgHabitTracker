package habitapp.example.rpghabittracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
//import android.example.rpghabittracker.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class activity_shop_bought extends AppCompatActivity {

    private ArrayList<shopRecviewItem> boughtData;

    private RecyclerView mRecyclerView;
    private boughtRecAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_bought);

        getSupportActionBar().setTitle("Bought");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getBoughtData();
        buildRecyclerView();

        String bought = getDefaults("totalItemBought",activity_shop_bought.this);
        TextView boughtTw = findViewById(R.id.shop_bought_num);
        boughtTw.setText(bought);

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.shop_bought_recview);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new boughtRecAdapter(boughtData);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }


}
