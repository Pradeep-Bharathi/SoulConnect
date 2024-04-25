package com.example.first;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {

    private ListView listView;
    private List<Song> songs;
    private MediaPlayer mediaPlayer;
    private int[] backgroundResources = {
            R.drawable.rain,
            R.drawable.piano
            // Add more background resources as needed
    };

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        listView = view.findViewById(R.id.listViewSongs);
        mediaPlayer = new MediaPlayer();

        // Create a list of songs
        songs = new ArrayList<>();
        songs.add(new Song("Rain Music", R.raw.song1));
        songs.add(new Song("Piano Music", R.raw.song1));
        // Add more songs as needed

        // Create a custom adapter with background resource for each song
        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(getContext(), R.layout.list_item_song, songs) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = convertView;
                if (itemView == null) {
                    itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_song, parent, false);
                }

                // Set the background resource for the song item
                itemView.setBackgroundResource(backgroundResources[position % backgroundResources.length]);

                // Set fixed height for the view
                ViewGroup.LayoutParams params = itemView.getLayoutParams();
                params.height = 300; // Set fixed height in pixels
                itemView.setLayoutParams(params);

                TextView textViewSongName = itemView.findViewById(R.id.textViewSongName);

                TextView textViewPlaying = itemView.findViewById(R.id.textViewPlaying);

                // Get the current song
                Song currentSong = getItem(position);

                // Set the song name and artist name
                textViewSongName.setText(currentSong.getSongName());


                // Hide "Playing" text by default
                textViewPlaying.setVisibility(View.GONE);

                return itemView;
            }
        };


        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        // Set click listener for ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSong(position);
            }
        });

        return view;
    }

    // Play the selected song
    private void playSong(int position) {
        try {
            // Reset playing status of all songs
            for (int i = 0; i < listView.getChildCount(); i++) {
                View listItem = listView.getChildAt(i);
                TextView textViewPlaying = listItem.findViewById(R.id.textViewPlaying);
                textViewPlaying.setVisibility(View.GONE);
            }

            // Show "Playing" text beside the selected song
            View selectedListItem = listView.getChildAt(position);
            TextView textViewPlaying = selectedListItem.findViewById(R.id.textViewPlaying);
            textViewPlaying.setVisibility(View.VISIBLE);

            mediaPlayer.reset();
            mediaPlayer.setDataSource(getContext(), Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + songs.get(position).getSongResource()));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
