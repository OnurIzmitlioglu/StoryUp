package com.onurizmitlioglu.storyup;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StoryList extends ArrayAdapter<Story> {
    private Activity context;
    private List<Story> myStoryList;

    public StoryList(Activity context, List<Story> myStoryList){
        super(context,R.layout.story_list_layout,myStoryList);
        this.context = context;
        this.myStoryList = myStoryList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.story_list_layout,null, true);

        TextView textViewStoryName = (TextView) listViewItem.findViewById(R.id.tv_story_header);
        TextView textViewAuthorName = (TextView) listViewItem.findViewById(R.id.tv_author_name);
        TextView textViewStoryCategory = (TextView) listViewItem.findViewById(R.id.tv_category);

        Story story = myStoryList.get(position);

        textViewStoryName.setText(story.getStoryTitle());
        textViewAuthorName.setText(story.getAuthorMail());
        textViewStoryCategory.setText(story.getStoryCategory());

        return listViewItem;
    }
}
