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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

//starting activity with list of songs
public class MainActivity extends AppCompatActivity {

    //instance variables
    ListView songList;
    String[] songNames;
    ArrayAdapter<String> mAdapter;
    Bundle playListSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set a title
        getSupportActionBar().setTitle("Songs");
        setContentView(R.layout.activity_main);
        //find view and get resources
        songList = (ListView)findViewById(R.id.songList);
        songNames = getResources().getStringArray(R.array.songNames);
        //create a bundle to store songs
        playListSongs = new Bundle();
        //init list of songs
        initializeSongList();
    }

    /*
    *   Function: initialize the list of songs by creating an adapter and binding it to the listview.
    *   Parameters: none
    *   Return: none
     */
    public void initializeSongList() {
        mAdapter = new ArrayAdapter<String>(this, R.layout.song_checkbox, songNames);
        songList.setAdapter(mAdapter);
    }

    /*
    *   Function: click handler for checkbox
    *   Parameters: the view
    *   Return: none
     */
    public void onCheckBoxClick(View view) {
        CheckBox cb = (CheckBox)view;
        String cbText = cb.getText().toString();
        if(cb.isChecked())
            playListSongs.putString(cbText, cbText);
        else
            playListSongs.remove(cbText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get menu inflater
        MenuInflater inflater = getMenuInflater();
        //inflate the menu defined in the xml file
        inflater.inflate(R.menu.main_activity_options_menu,	menu);
        return true;
    }

    /*
    *   Function: create playlist option handler. Start playlist activity if songs selected otherwise show toast message.
    *   Parameters: menu item
    *   Return: non
     */
    public void onCreatePlaylist(MenuItem item) {
        if(playListSongs.size() == 0) {
            Toast.makeText(this, "Select One or More Songs", Toast.LENGTH_LONG).show();
            return;
        }
        Intent playListActivity = new Intent(this, Playlist.class);
        playListActivity.putExtra("songs", playListSongs);
        startActivity(playListActivity);
    }

    /*
    *   Function: clear selection handler.  Deselect all selected songs.
    *   Parameters: menu item
    *   Return: none
     */
    public void onClearSelection(MenuItem item) {
        playListSongs.clear();
        for(int i = 0; i < songList.getCount(); i++) {
            CheckBox cb = (CheckBox)songList.getChildAt(i);
            if(cb.isChecked())
                cb.setChecked(false);
        }
    }

    /*
    *   Function: invert selection handler. invert the selections made
    *   Parameters: menu item
    *   Return: none
     */
    public void onInvertSelection(MenuItem item) {
        playListSongs.clear();
        for(int i = 0; i < songList.getCount(); i++) {
            CheckBox cb = (CheckBox)songList.getChildAt(i);
            if(cb.isChecked())
                cb.setChecked(false);
            else {
                cb.setChecked(true);
                String cbText = cb.getText().toString();
                playListSongs.putString(cbText, cbText);
            }
        }
    }

    /*
    *   Function: select all handler. select all songs
    *   Parameters: menu item
    *   Return: none
     */
    public void onSelectAll(MenuItem item) {
        playListSongs.clear();
        for(int i = 0; i < songList.getCount(); i++) {
            CheckBox cb = (CheckBox)songList.getChildAt(i);
            cb.setChecked(true);
            String cbText = cb.getText().toString();
            playListSongs.putString(cbText, cbText);
        }
    }
}
