package com.example.first;

public class Song {
    private String songName;
    private int songResource;

    public Song(String songName, int songResource) {
        this.songName = songName;
        this.songResource = songResource;
    }

    public String getSongName() {
        return songName;
    }

    public int getSongResource() {
        return songResource;
    }
}
