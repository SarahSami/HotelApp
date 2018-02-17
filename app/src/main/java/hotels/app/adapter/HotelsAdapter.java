package hotels.app.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hotels.app.R;
import hotels.app.model.Hotel;
import hotels.app.util.ImageLoader;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ViewHolder> {

    private List<Hotel> items;
    private Context mContext;
    private ImageLoader imageLoader;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hotelNameTv;
        public CardView cardView;
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            hotelNameTv = itemView.findViewById(R.id.hotelNameTv);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    public HotelsAdapter(Context context, List<Hotel> data) {
        items = data;
        mContext = context;
        imageLoader = new ImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_hotel, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Hotel item = items.get(position);
        holder.hotelNameTv.setText("" + item.getTitle());
        imageLoader.loadDrawable(item.getMedia(), holder.imageView, holder.imageView.getDrawable());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}