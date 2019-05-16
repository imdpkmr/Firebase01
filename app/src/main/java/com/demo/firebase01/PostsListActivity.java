package com.demo.firebase01;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*We will use hte following of Firebase
* 1. Storage - to store images
* 2. Realtime Database
* It will contain JSON of data to be retrieved Recycler View. The data contains title, image(url), description*/

public class PostsListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_posts_lists);
        setContentView(R.layout.activity_drawer);

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

        //navigation drawer layout
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //calling method to set onclicklistener for navigation drawer
        setNavigationViewListener();
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
        inflater.inflate(R.menu.main_menu,menu);

        //Associate searchable configuration with the SearchView
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId()==R.id.settings){
            Toast.makeText(this,"You clicked SETTINGS, settings is on the way please wait",Toast.LENGTH_LONG).show();
        }else if(item.getItemId()==R.id.help){
            Toast.makeText(this,"Help is on the way, please wait.",Toast.LENGTH_SHORT).show();
        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //handle navigation item clicks here
        switch ((item.getItemId())){
            case R.id.dashboardItem:
                Toast.makeText(this,"Opening Dahsboard, please wait",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mapItem:
                //call maps activity
                Toast.makeText(this,"Opening Maps, please wait.",Toast.LENGTH_SHORT).show();
                launchMapActivity();
                break;
            case R.id.settingsItem:
                Toast.makeText(this,"Opening Settings, please wait.",Toast.LENGTH_LONG).show();
                break;
            case R.id.logoutItem:
                Toast.makeText(this,"Logging Out, please wait.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exitItem:
                Intent homeIntent=new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //method to set listener for navigation drawer
    private void setNavigationViewListener(){
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationDrawer);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void launchMapActivity(){
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
}
