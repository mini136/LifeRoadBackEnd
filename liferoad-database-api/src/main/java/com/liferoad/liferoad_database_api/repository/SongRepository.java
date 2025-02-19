package com.liferoad.liferoad_database_api.repository;

import com.liferoad.liferoad_database_api.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitleContainingIgnoreCase(String title);
    List<Song> findByArtistContainingIgnoreCase(String artist);
    List<Song> findByAlbumContainingIgnoreCase(String album);
    List<Song> findByGenreContainingIgnoreCase(String genre);
    List<Song> findByMoodContainingIgnoreCase(String mood);
}
