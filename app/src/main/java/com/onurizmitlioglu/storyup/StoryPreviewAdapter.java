package com.onurizmitlioglu.storyup;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

class StoryPreviewAdapter extends RecyclerView.Adapter<StoryPreviewAdapter.ViewHolder> {
    private Context con;
    private boolean userLoggedIn = true; // Initially false.

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout mStory;
        public ViewHolder(Context context, ConstraintLayout constraintLayout){
            super(constraintLayout);
            mStory = constraintLayout;
            con = context;
            mStory.setOnClickListener(new View.OnClickListener() {
                TextView storyTitleText = (TextView) mStory.getChildAt(0);
                TextView authorNameText = (TextView) mStory.getChildAt(1);
                @Override
                public void onClick(View view) {
                    if(userLoggedIn){
                        Intent storyPreviewIntent = new Intent(con, StoryPreviewActivity.class);
                        storyPreviewIntent.putExtra("storyTitle", storyTitleText.getText());
                        storyPreviewIntent.putExtra("authorName", authorNameText.getText());
                        storyPreviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        con.startActivity(storyPreviewIntent);
                    }
                    else{
                        //Intent loginIntent = new Intent(con, com.onurizmitlioglu.lerative_android.OnBoardActivity.class);
                        //loginIntent.putExtra("storyLastVisited", storyTitleText.getText());
                        //loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //con.startActivity(loginIntent);
                    }
                }
            });
        }
    }

    public StoryPreviewAdapter(Context context/*, List<Story> myDataSet */){
        //mStoriesDataSet = myDataSet;
        con = context;
    }

    public StoryPreviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parentView, int ViewType){
        ConstraintLayout textView = (ConstraintLayout) LayoutInflater.from(parentView.getContext()).inflate(R.layout.explore_single_story_rv,parentView,false);
        ViewHolder viewHolder = new ViewHolder(con,textView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.mStory.setText(mStoriesDataSet[position]);
        LinearLayout ll = (LinearLayout) holder.mStory.getChildAt(1);
        TextView tv = (TextView) ll.getChildAt(0);
        /*if(mStoriesDataSet[position].length()>=20){
            tv.setText(mStoriesDataSet[position].substring(0,19) + "...");
        }
        else{ // We should create intent of story preview from the DB, not the TextView.
            tv.setText(mStoriesDataSet[position]);
        }
        */

        //tv.setText(mStoriesDataSet.get(position).getStoryName());
    }

    @Override
    public int getItemCount() {
        //return mStoriesDataSet.size();
        return 0;
    }
}
