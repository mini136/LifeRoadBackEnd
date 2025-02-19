package com.liferoad.liferoad_database_api.controller;

import com.liferoad.liferoad_database_api.model.Song;
import com.liferoad.liferoad_database_api.repository.SongRepository;
import com.liferoad.liferoad_database_api.service.SongMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongRepository songRepository;

    @Autowired
    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Endpoint pro získání seznamu všech písniček (nebo podle filtru).
     *
     * @param title Filtrování podle názvu písničky.
     * @param artist Filtrování podle interpreta.
     * @param album Filtrování podle alba.
     * @param genre Filtrování podle žánru.
     * @param mood Filtrování podle moodu.
     * @return Seznam písniček odpovídajících kritériím.
     */
    @GetMapping
    public ResponseEntity<List<Song>> getSongs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String mood) {

        List<Song> songs;
        if (title != null) {
            songs = songRepository.findByTitleContainingIgnoreCase(title);
        } else if (artist != null) {
            songs = songRepository.findByArtistContainingIgnoreCase(artist);
        } else if (album != null) {
            songs = songRepository.findByAlbumContainingIgnoreCase(album);
        } else if (genre != null) {
            songs = songRepository.findByGenreContainingIgnoreCase(genre);
        } else if (mood != null) {
            songs = songRepository.findByMoodContainingIgnoreCase(mood);
        } else {
            songs = songRepository.findAll();
        }
        return ResponseEntity.ok(songs);
    }

    /**
     * Endpoint pro získání detailů o jedné písničce podle jejího názvu.
     *
     * @param title Název písničky.
     * @return Detailní informace o písničce.
     */
    @GetMapping("/api/{title}")
    public ResponseEntity<Song> getSongByTitle(@PathVariable String title) {
        List<Song> songs = songRepository.findByTitleContainingIgnoreCase(title);
        if (!songs.isEmpty()) {
            return ResponseEntity.ok(songs.get(0)); // Vrátíme první shodnou písničku
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Endpoint pro stažení souboru písničky podle jejího názvu.
     *
     * @param title Název písničky.
     * @return Audio soubor písničky.
     */
    @GetMapping("/api/{title}/file")
    public ResponseEntity<byte[]> getSongFile(@PathVariable String title) {
        List<Song> songs = songRepository.findByTitleContainingIgnoreCase(title);
        if (!songs.isEmpty()) {
            Song song = songs.get(0);
            File file = new File(song.getFilePath());
            if (file.exists()) {
                try {
                    byte[] audioData = Files.readAllBytes(file.toPath());
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Type", "audio/mpeg"); // nebo podle formátu souboru (např. .mp3)
                    return ResponseEntity.ok().headers(headers).body(audioData);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
