package com.example.songify.song.controller;

import com.example.songify.song.dto.request.SongRequestDto;
import com.example.songify.song.dto.request.UpdateSongRequestDto;
import com.example.songify.song.dto.response.DeleteSongResponseDto;
import com.example.songify.song.dto.response.SingleSongResponseDto;
import com.example.songify.song.dto.response.SongResponseDto;
import com.example.songify.song.dto.response.UpdateSongResponseDto;
import com.example.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SongRestController {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmendes song1", "Shawn Mendes"),
            2, new Song("ariana grande song2", "Ariana Grande"),
            3, new Song( "ariana grande song3", "Ariana Grande"),
            4, new Song( "ariana grande song4", "Ariana Grande")
    ));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer limit){
        if(limit != null) {
            Map<Integer, Song> limitedMap = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            SongResponseDto response = new SongResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        SongResponseDto response = new SongResponseDto(database);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongByID(@PathVariable Integer id, @RequestHeader(required = false) String requestId){
        log.info(requestId);
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        Song song = database.get(id);
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request){
        Song song = new Song(request.songName(), request.artist());
        log.info("adding new song: " + song);
        database.put(database.size() + 1, song);
        return ResponseEntity.ok(new SingleSongResponseDto(song));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id){
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        database.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK));
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request){
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        String newSongName = request.songName();
        String newArtist = request.artist();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = database.put(id, newSong);
        log.info("Update song with id: " + id +
                " with oldSongName: " + oldSong.name() + " to newSongName: " + newSong.name() +
                " oldArtist: " + oldSong.artist() + " to newArtist: " + newSong.artist()
        );
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(), newSong.artist()));
    }
}
