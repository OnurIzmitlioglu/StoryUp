package com.onurizmitlioglu.storyup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WritingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText storyTitle,storyText;
    private Spinner categories;
    private Button publishStory;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase databaseInstance;
    DatabaseReference databaseStories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        storyTitle = (EditText) findViewById(R.id.et_story_name_field);
        storyText = (EditText) findViewById(R.id.et_story_part_field);
        categories = (Spinner) findViewById(R.id.spn_category);
        publishStory = (Button) findViewById(R.id.btn_publish_story);

        databaseInstance = FirebaseDatabase.getInstance();
        databaseStories = databaseInstance.getReference("stories");

        publishStory.setOnClickListener(this);

    }

    private void saveStory() {
        String mAuthor = "Anonymous";
        String mTitle = storyTitle.getText().toString().trim();
        String mStory = storyText.getText().toString().trim();
        String mCategory = categories.getSelectedItem().toString();

        if(TextUtils.isEmpty(mTitle)){
            Toast.makeText(this,"You should specify the title of your story.", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(mStory)){
            Toast.makeText(this,"You should write your story.", Toast.LENGTH_SHORT).show();
        }
        else{

            String mId = databaseStories.push().getKey();
            Story story = new Story(mAuthor,mId,mTitle,mCategory,mStory);
            databaseStories.child(mId).setValue(story);

            Toast.makeText(this,"Your story has been published!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WritingActivity.this, ExploreActivity.class));
        }
    }


    @Override
    public void onClick(View view){
        if(view == publishStory){
            saveStory();
        }
    }

}
