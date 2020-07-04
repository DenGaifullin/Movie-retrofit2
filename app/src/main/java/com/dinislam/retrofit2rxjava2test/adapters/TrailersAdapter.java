package com.dinislam.retrofit2rxjava2test.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinislam.retrofit2rxjava2test.R;
import com.dinislam.retrofit2rxjava2test.pojo.Trailer;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder> {
    private List<Trailer> trailers;
    private static TrailerClickListener trailerClickListener;

    public interface TrailerClickListener{
        void onClick(String key);
    }

    public void setTrailerClickListener(TrailerClickListener trailerClickListener) {
        TrailersAdapter.trailerClickListener = trailerClickListener;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrailerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.textView.setText(trailer.getName());
        holder.key = trailer.getKey();
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    static class TrailerHolder extends RecyclerView.ViewHolder {
        String name;
        String type;
        String key;
        TextView textView;
        TrailerHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.trailer_name_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TrailersAdapter .trailerClickListener.onClick(key);
                }
            });
        }
    }

    public TrailersAdapter(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }
}
