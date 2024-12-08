package com.example.songify.song.domain.repository;

import com.example.songify.song.domain.model.Song;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {
    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmendes song1", "Shawn Mendes"),
            2, new Song("ariana grande song2", "Ariana Grande"),
            3, new Song( "ariana grande song3", "Ariana Grande"),
            4, new Song( "ariana grande song4", "Ariana Grande")
    ));
    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    public Map<Integer, Song> findAll() {
        return database;
    }

}