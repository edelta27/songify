package com.example.songify.song.domain.service;

import com.example.songify.song.domain.model.Song;
import com.example.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class SongRetreiver {
    private final SongRepository songRepository;

    @Autowired
    SongRetreiver(SongRepository songRepository){
        this.songRepository = songRepository;
    }
    public Map<Integer, Song> findAll() {
        log.info("retrieving all songs: ");
        return songRepository.findAll();
    }

    //chcek
}
