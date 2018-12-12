package com.onurizmitlioglu.storyup;

public class Story {
    String storyId;
    String storyTitle;
    String storyCategory;
    String storyText;
    String authorMail;

    public Story(){

    }

    public Story(String authorMail, String storyId, String storyTitle, String storyCategory, String storyText){
        this.authorMail = authorMail;
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyCategory = storyCategory;
        this.storyText = storyText;
    }

    public String getAuthorMail() { return this.authorMail; }

    public String getStoryId(){
        return this.storyId;
    }

    public String getStoryTitle(){
        return this.storyTitle;
    }

    public String getStoryCategory(){
        return this.storyCategory;
    }

    public String getStoryText(){
        return this.storyText;
    }
}
