package habitapp.example.rpghabittracker;

//import android.example.rpghabittracker.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class habitsRecAdapter extends RecyclerView.Adapter<habitsRecAdapter.HabitsRecViewHolder> {

    private ArrayList<habitsRecviewItem> habitsList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public static class HabitsRecViewHolder extends RecyclerView.ViewHolder {
        public TextView twName;
        public TextView twDif;
        public TextView twStreak;
        public TextView twBest;
        public TextView twAll;
        public Button btn;

        public HabitsRecViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            twName = itemView.findViewById(R.id.habits_list_name);
            twDif = itemView.findViewById(R.id.habits_list_diff);
            twStreak = itemView.findViewById(R.id.habits_list_streak);
            twBest = itemView.findViewById(R.id.habits_list_best);
            twAll = itemView.findViewById(R.id.habits_list_all);
            btn = itemView.findViewById(R.id.habits_list_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onButtonClick(position);
                        }
                    }
                }
            });

        }
    }

    public habitsRecAdapter(ArrayList<habitsRecviewItem> _habitsList){
        habitsList = _habitsList;
    }

    @NonNull
    @Override
    public HabitsRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_habits_layout,parent,false);
        HabitsRecViewHolder vh = new HabitsRecViewHolder(v,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitsRecViewHolder holder, final int position) {
        habitsRecviewItem currItem = habitsList.get(position);

        holder.twName.setText(currItem.getName());
        holder.twDif.setText(currItem.getDif());
        holder.twStreak.setText("Streak: "+currItem.getStreak());
        holder.twBest.setText("Best: "+currItem.getBest());
        holder.twAll.setText("All: "+currItem.getDoneCtr()+"/"+currItem.getShouldBeDoneCtr());
        if(currItem.getIsClicked()){
            holder.btn.setBackgroundResource(R.drawable.ic_btn_act_done_clicked);
            holder.btn.setClickable(false);
        }
        else{
            holder.btn.setBackgroundResource(R.drawable.ic_btn_act_done);
            holder.btn.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }



}
