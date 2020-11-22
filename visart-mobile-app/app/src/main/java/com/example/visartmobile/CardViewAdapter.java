package com.example.visartmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.visartmobile.util.ArtListing;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<ArtListing> artListings;

    public CardViewAdapter(List<ArtListing> artListings){
        this.artListings = artListings;
    }

    /***** Creating OnItemClickListener *****/

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView cardTitle;
        public ImageView postImage;
        public TextView listingUsername;
        public TextView listingDescription;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            cardTitle = (TextView) itemView.findViewById(R.id.card_name);
            postImage = (ImageView) itemView.findViewById(R.id.post_image);
            listingUsername = (TextView) itemView.findViewById(R.id.listingUsername);
            listingDescription = (TextView) itemView.findViewById(R.id.listingDescription);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recyclerview_card_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CardViewAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        ArtListing listing = artListings.get(position);

        // Set item views based on the views and data model
        TextView titleTextView = holder.cardTitle;
        TextView postDescription = holder.listingDescription;
        TextView postUsername = holder.listingUsername;
        ImageView postImage = holder.postImage;


        titleTextView.setText(listing.getTitle()+", $"+listing.getPrice());
        postDescription.setText(listing.getDescription());
        postUsername.setText((listing.getArtistDisplayname()));
        Picasso.with(holder.postImage.getContext()).load(listing.getPostImages()[0]).fit().into(postImage);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return artListings.size();
    }
}
