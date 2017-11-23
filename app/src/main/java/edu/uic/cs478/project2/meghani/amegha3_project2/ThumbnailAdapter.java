/*
*   Arbaaz Meghani
*   Description: This project starts as a list of songs that the user can create a play list from.
*           When the user creates the playlist, it is viewed in a gridview with a thumbnail for
*           each song.  The user can press a thumbnail to launch the song in a browser or
*           the user can long press to open a context menu. In the context menu, the user can
*           play the song, view a wiki page about the song, or view the wiki page about the artist.
 */

//package
package edu.uic.cs478.project2.meghani.amegha3_project2;

//import statements
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

//class to create custom adapter to hold thumbnails
public class ThumbnailAdapter extends BaseAdapter {

    //instance variables
    private Context context;
    private ArrayList<Integer> thumbnailIds;

    //constructor
    public ThumbnailAdapter(Context context, ArrayList<Integer> thumbnailIds) {
        this.context = context;
        this.thumbnailIds = thumbnailIds;
    }

    //return size of arraylist
    @Override
    public int getCount() {
        return thumbnailIds.size();
    }

    //return item in ids
    @Override
    public Object getItem(int i) {
        if(i >= thumbnailIds.size())
            return null;
        return thumbnailIds.get(i);
    }

    //return id of item
    @Override
    public long getItemId(int i) {
        if(i >= thumbnailIds.size())
            return -1;
        return thumbnailIds.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //make sure valid position
        if(i >= thumbnailIds.size())
            return null;

        //create image
        ImageView image = (ImageView)view;

        //if image does not exist then create a new one
        if(image == null) {
            image = new ImageView(context);
            image.setLayoutParams(new GridView.LayoutParams(400, 400));
            image.setPadding(5, 5, 5, 5);
        }

        //set the image resource
        image.setImageResource(thumbnailIds.get(i));

        //return the image
        return image;
    }
}
