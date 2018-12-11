package com.onurizmitlioglu.storyup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView storyRecyclerView;
    private RecyclerView.Adapter storyRVAdapter;
    private RecyclerView.LayoutManager storyRVLayoutManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    //mTextMessage.setText(R.string.title_explore);
                    return true;
                case R.id.navigation_search:
                    //mTextMessage.setText(R.string.title_search);
                    return true;
                case R.id.navigation_add_story:
                    //mTextMessage.setText(R.string.title_add_story);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_profile:
                    //mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        storyRecyclerView = (RecyclerView) findViewById(R.id.rv_explore_stories);
        storyRecyclerView.setHasFixedSize(true);

        storyRVLayoutManager = new LinearLayoutManager(this);
        storyRecyclerView.setLayoutManager(storyRVLayoutManager);

        storyRVAdapter = new StoryPreviewAdapter(this.getBaseContext());
        storyRecyclerView.setAdapter(storyRVAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
