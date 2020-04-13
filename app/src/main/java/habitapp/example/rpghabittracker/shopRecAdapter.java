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

public class shopRecAdapter extends RecyclerView.Adapter<shopRecAdapter.ShopRecViewHolder> {
    private ArrayList<shopRecviewItem> shopList;
    private shopRecAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onButtonBuyClick(int position);
        void onButtonDelClick(int position);
    }

    public void setOnItemClickListener(shopRecAdapter.OnItemClickListener listener){
        mListener = listener;
    }


    public static class ShopRecViewHolder extends RecyclerView.ViewHolder {
        public TextView twName;
        public TextView twCost;
        public Button btnBuy;
        public Button btnDel;

        public ShopRecViewHolder(@NonNull final View itemView, final shopRecAdapter.OnItemClickListener listener) {
            super(itemView);
            twName = itemView.findViewById(R.id.recview_shop_name);
            twCost = itemView.findViewById(R.id.recview_shop_cost);
            btnBuy = itemView.findViewById(R.id.recview_shop_btn_buy);
            btnDel = itemView.findViewById(R.id.recview_shop_btn_del);

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onButtonBuyClick(position);
                        }
                    }
                }
            });
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onButtonDelClick(position);
                        }
                    }
                }
            });

        }
    }

    public shopRecAdapter(ArrayList<shopRecviewItem> _shopList){
        shopList = _shopList;
    }

    @NonNull
    @Override
    public shopRecAdapter.ShopRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_shop_layout,parent,false);
        shopRecAdapter.ShopRecViewHolder vh = new shopRecAdapter.ShopRecViewHolder(v,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull shopRecAdapter.ShopRecViewHolder holder, final int position) {
        shopRecviewItem currItem = shopList.get(position);

        holder.twName.setText(currItem.getName());
        holder.twCost.setText(Integer.toString(currItem.getCost()));
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }
    
}
