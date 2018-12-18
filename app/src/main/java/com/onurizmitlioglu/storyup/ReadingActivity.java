package com.onurizmitlioglu.storyup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadingActivity extends AppCompatActivity {
    private TextView title, author, category, story;
    private FirebaseDatabase databaseInstance;
    private DatabaseReference databaseStories;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        title = (TextView) findViewById(R.id.tv_story_name);
//        author = (TextView) findViewById(R.id.tv_author_name);
        category = (TextView) findViewById(R.id.tv_category);
        story = (TextView) findViewById(R.id.tv_story);

        databaseInstance = FirebaseDatabase.getInstance();
        databaseStories = databaseInstance.getReference("stories");

        Bundle storyBundle = getIntent().getExtras();
        String storyId = storyBundle.getString("storyId");


        databaseStories.child(storyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title.setText(dataSnapshot.getValue(Story.class).getStoryTitle());
//                author.setText(dataSnapshot.getValue(Story.class).getAuthorMail());
                category.setText(dataSnapshot.getValue(Story.class).getStoryCategory());
                story.setText(dataSnapshot.getValue(Story.class).getStoryText());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
