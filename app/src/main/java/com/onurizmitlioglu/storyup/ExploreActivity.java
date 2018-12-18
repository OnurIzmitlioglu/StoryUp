package com.onurizmitlioglu.storyup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView listViewStories;
    private List<Story> storyList = new ArrayList<>();
    private List<Story> resultList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    //Do nothing.
                    return true;
                case R.id.navigation_add_story:
                    startActivity(new Intent(ExploreActivity.this, WritingActivity.class));
                    return true;
                case R.id.navigation_profile:
                    startActivity(new Intent(ExploreActivity.this, ProfileActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        databaseReference = FirebaseDatabase.getInstance().getReference("stories");
        listViewStories = findViewById(R.id.lv_stories);
        listViewStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Story s = storyList.get(position);
                String storyId = s.getStoryId();
                Intent intent = new Intent(ExploreActivity.this, ReadingActivity.class);
                intent.putExtra("storyId", storyId);
                startActivity(intent);
            }

        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MenuItem exploreItem = navigation.getMenu().getItem(0);
        exploreItem.setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storyList.clear();
                for(DataSnapshot storySnapshot : dataSnapshot.getChildren()){
                    Story story = storySnapshot.getValue(Story.class);
                    storyList.add(story);
                }

                StoryList adapter = new StoryList(ExploreActivity.this,storyList);
                listViewStories.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                resultList.clear();
                for(Story story : storyList){
                    if(story.getStoryTitle().equals(text.toString())){
                        resultList.add(story);
                    }
                }
                StoryList adapter = new StoryList(ExploreActivity.this,resultList);
                listViewStories.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
