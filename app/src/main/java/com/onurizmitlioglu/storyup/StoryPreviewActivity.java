package com.onurizmitlioglu.storyup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StoryPreviewActivity extends AppCompatActivity {
    private TextView authorName, storyTitle;
    private String author, story;
    private Bundle storyBundle;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_preview);
        storyBundle = getIntent().getExtras();
        authorName = (TextView) findViewById(R.id.tv_author_name);
        storyTitle = (TextView) findViewById(R.id.tv_story_title);

        if(storyBundle != null){
            author = storyBundle.getString("authorName" );
            story = storyBundle.getString("storyTitle" );
            authorName.setText(author);
            storyTitle.setText(story);
        }
        else{
            storyTitle.setText("Oops! Story Not Found!");
        }

    }


}
