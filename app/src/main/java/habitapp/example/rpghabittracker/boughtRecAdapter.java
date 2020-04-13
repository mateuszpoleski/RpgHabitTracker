package habitapp.example.rpghabittracker;

//import android.example.rpghabittracker.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class boughtRecAdapter extends RecyclerView.Adapter<boughtRecAdapter.BoughtRecViewHolder> {
    private ArrayList<shopRecviewItem> boughtList;

    public class BoughtRecViewHolder extends RecyclerView.ViewHolder{
        public TextView twName;
        public TextView twCost;
        public TextView twDate;

        public BoughtRecViewHolder(@NonNull final View itemView) {
            super(itemView);
            twName = itemView.findViewById(R.id.recview_bought_name);
            twCost = itemView.findViewById(R.id.recview_bought_cost);
            twDate = itemView.findViewById(R.id.recview_bought_date);

        }
    }
    public boughtRecAdapter(ArrayList<shopRecviewItem> _boughtList){
        boughtList = _boughtList;
    }

    @NonNull
    @Override
    public boughtRecAdapter.BoughtRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_bought_layout,parent,false);
        boughtRecAdapter.BoughtRecViewHolder vh = new boughtRecAdapter.BoughtRecViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull boughtRecAdapter.BoughtRecViewHolder holder, final int position) {
       shopRecviewItem currItem = boughtList.get(position);
        holder.twName.setText(currItem.getName());
        holder.twCost.setText(Integer.toString(currItem.getCost()));
        holder.twDate.setText(currItem.getDate());
    }

    @Override
    public int getItemCount() {
        return boughtList.size();
    }
}
