package com.example.spaceprovider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainFeatureAdapter extends FirebaseRecyclerAdapter<MainFeatureModel,MainFeatureAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainFeatureAdapter(@NonNull FirebaseRecyclerOptions<MainFeatureModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainFeatureModel model) {

        //Log.d("name",model.getName());

        //Log.d("seats",model.getSeats());
        holder.name.setText("Feature name: "+model.getName());
        holder.seats.setText("Total Seats :"+model.getSeats());
        holder.day_pass.setText("Fees for a day :"+model.getDay_pass());
        holder.monthly_pass.setText("Fees for a month :"+model.getMonthly_pass());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_feature_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,seats,day_pass,monthly_pass;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            seats = (TextView) itemView.findViewById(R.id.seats);
            day_pass = (TextView) itemView.findViewById(R.id.day_pass);
            monthly_pass = (TextView) itemView.findViewById(R.id.monthly_pass);
        }
    }
}
