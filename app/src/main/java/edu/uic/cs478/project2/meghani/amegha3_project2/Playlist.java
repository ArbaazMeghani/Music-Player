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
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

//playlist activity to view playlist
public class Playlist extends AppCompatActivity {

    //instance variables
    ArrayList<String> songNames, songLinks, artistLinks, songInfoLinks, contextMenuLinks;
    ArrayList<Integer> thumbnailIds;
    GridView thumbnailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set title
        getSupportActionBar().setTitle("Your Playlist");
        setContentView(R.layout.activity_playlist);
        //get view
        thumbnailView = (GridView)findViewById(R.id.gridView);
        //register context menu
        registerForContextMenu(thumbnailView);
        //init the lists
        initLists();

        //init the gridview
        iniThumbnailView();
        //set the listener
        setListener();
    }

    /*
    *   Function: Create each list to store links and thumbnails
    *   Parameters: none
    *   Return: none
     */
    private void initLists() {
        songNames = new ArrayList<>();
        songLinks = new ArrayList<>();
        artistLinks = new ArrayList<>();
        songInfoLinks = new ArrayList<>();
        thumbnailIds = new ArrayList<>();
        contextMenuLinks = new ArrayList<>();

        //This part looks through the bundle the was passed in intent to see which songs were selected
        //then it adds only those links to the arraylists that were selected
        Bundle songs = getIntent().getBundleExtra("songs");
        for(int i = 0; i < getResources().getStringArray(R.array.songNames).length; i++) {
            if(songs.get(getResources().getStringArray(R.array.songNames)[i]) != null) {
                songNames.add(getResources().getStringArray(R.array.songNames)[i]);
                songLinks.add(getResources().getStringArray(R.array.songLinks)[i]);
                artistLinks.add(getResources().getStringArray(R.array.artistLinks)[i]);
                songInfoLinks.add(getResources().getStringArray(R.array.songInfoLinks)[i]);
                thumbnailIds.add(getResources().obtainTypedArray(R.array.thumbnails).getResourceId(i, -1));
            }
        }
    }


    /*
    *   Function: initalize the gridview by creating adapter and attaching it.
    *   Parameters: none
    *   Return: none
     */
    private void iniThumbnailView() {
        ThumbnailAdapter madapter = new ThumbnailAdapter(this, thumbnailIds);
        thumbnailView.setAdapter(madapter);
    }

    /*
    *   Function: set item click listener to gridview
    *   Parameters: none
    *   Return: none
     */
    private void setListener() {
        thumbnailView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent playSong = new Intent(Intent.ACTION_VIEW, Uri.parse(songLinks.get(i)));
                startActivity(playSong);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        //crate context menu
        super.onCreateContextMenu(menu, view, menuInfo);
        //inflate context menu
        getMenuInflater().inflate(R.menu.playlist_context_menu, menu);

        //get position of item in gridview
        int itemPosition = ((AdapterView.AdapterContextMenuInfo)menuInfo).position;
        //clear any links in arraylist
        contextMenuLinks.clear();

        //store links for current selection
        contextMenuLinks.add(songLinks.get(itemPosition));
        contextMenuLinks.add(songInfoLinks.get(itemPosition));
        contextMenuLinks.add(artistLinks.get(itemPosition));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //find the menu item that was selected
        int itemPosition;
        if(item.getTitle().equals("Play"))
            itemPosition = 0;
        else if(item.getTitle().equals("Song Info"))
            itemPosition = 1;
        else
            itemPosition = 2;

        //create intent with uri of selected item
        Intent contextIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contextMenuLinks.get(itemPosition)));

        //start activity
        startActivity(contextIntent);
        return true;
    }
}
