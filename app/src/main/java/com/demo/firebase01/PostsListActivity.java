package com.demo.firebase01;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*We will use hte following of Firebase
* 1. Storage - to store images
* 2. Realtime Database
* It will contain JSON of data to be retrieved Recycler View. The data contains title, image(url), description*/

public class PostsListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_lists);

        //ActionBar
        ActionBar actionBar=getSupportActionBar();
        //set title
        actionBar.setTitle("Posts List");

        //RecyclerView
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //set layout as Linear Layout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //send Query to FirebaseDatabase
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=mFirebaseDatabase.getReference("Data");
   }
   //load data into recycler view adapter
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                       com.demo.firebase01.Model.class,
                        R.layout.row,
                        ViewHolder.class,
                        mDatabaseRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder holder,Model model, int position){
                        holder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getImage());
                    }
                };

        //set adapter to recycleradapter
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
}
