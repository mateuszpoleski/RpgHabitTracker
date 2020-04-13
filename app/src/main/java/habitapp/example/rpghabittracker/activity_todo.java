package habitapp.example.rpghabittracker;

import androidx.appcompat.app.AppCompatActivity;

//import android.example.rpghabittracker.R;
import android.os.Bundle;

public class activity_todo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        getSupportActionBar().setTitle("ToDo's");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
