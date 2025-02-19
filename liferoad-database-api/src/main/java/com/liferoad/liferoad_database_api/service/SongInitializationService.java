package com.liferoad.liferoad_database_api.service;

import com.liferoad.liferoad_database_api.model.Song;
import com.liferoad.liferoad_database_api.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SongInitializationService implements CommandLineRunner {

    @Autowired
    private SongMetadataService songMetadataService;

    @Autowired
    private SongRepository songRepository;

    @Value("${music.directory}")
    private String musicDirectory;

    @Override
    public void run(String... args) throws Exception {
        loadSongsFromDirectory(musicDirectory);
    }

    private void loadSongsFromDirectory(String directoryPath) {
        File musicDirectory = new File(directoryPath);
        File[] files = musicDirectory.listFiles((dir, name) -> name.endsWith(".mp3"));

        if (files != null) {
            for (File file : files) {
                try {
                    // Načteme metadata písničky pomocí služby
                    Song song = songMetadataService.extractMetadata(file);

                    // Uložíme písničku do databáze
                    if (song != null) {
                        songRepository.save(song);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}