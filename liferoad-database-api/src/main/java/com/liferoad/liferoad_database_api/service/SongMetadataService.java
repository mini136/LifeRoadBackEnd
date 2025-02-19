package com.liferoad.liferoad_database_api.service;

import com.liferoad.liferoad_database_api.model.Song;
import com.mpatric.mp3agic.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
@Service
public class SongMetadataService {

    public Song extractMetadata(File songFile) {
        try {
            Mp3File mp3File = new Mp3File(songFile);
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();

            // Získání názvu souboru bez přípony
            String title = songFile.getName().replaceFirst("[.][^.]+$", "");
            String artist = id3v2Tag != null ? id3v2Tag.getArtist() : null;
            String album = id3v2Tag != null ? id3v2Tag.getAlbum() : null;
            String genre = id3v2Tag != null ? id3v2Tag.getGenreDescription() : null;

            Song song = new Song();
            song.setTitle(title);
            song.setArtist(artist);
            song.setAlbum(album);
            song.setGenre(genre);
            song.setFilePath(songFile.getAbsolutePath());

            return song;
        } catch (UnsupportedTagException | InvalidDataException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}