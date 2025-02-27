package com.example.songify.song.domain.service;

import com.example.songify.song.domain.model.Song;
import com.example.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SongAdder {

    private final SongRepository songRepository;

    @Autowired
    SongAdder(SongRepository songRepository){
        this.songRepository = songRepository;
    }
    public Song addSong(Song song) {
        log.info("adding new song: " + song);
        songRepository.saveToDatabase(song);

        return song;
    }
    // sprawdzam
    //next

}
