package com.example.baaja;

import android.support.annotation.NonNull;

public class Song {
    private String mSongName;
    private String mArtistName;
    private String mSiteOfDownload;

    Song(String songName,String artistName,String siteOfDownload){
        mSongName=songName;
        mArtistName=artistName;
        mSiteOfDownload=siteOfDownload;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getSongName() {
        return mSongName;
    }

    public String getSiteOfDownload() {
        return mSiteOfDownload;
    }

    @NonNull
    @Override
    public String toString() {
        String song="";
        song =song + "Song: " + getSongName() + "\n";
        song =song + "Artist: " + getArtistName() + "\n";
        song =song + "Site: " + getSiteOfDownload();

        return song;
    }
}
