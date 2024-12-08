package com.example.songify.song.controller;

import com.example.songify.song.dto.DeleteSongResponseDto;
import com.example.songify.song.dto.SingleSongResponseDto;
import com.example.songify.song.dto.SongRequestDto;
import com.example.songify.song.dto.SongResponseDto;
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

    Map<Integer, String> database = new HashMap<>(Map.of(
            1, "shawnmendes song1",
            2, "ariana grande spong2"
    ));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer limit){
        if(limit != null) {
            Map<Integer, String> limitedMap = database.entrySet()
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
        String song = database.get(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request){
        String songName = request.songName();
        log.info("adding new song: " + songName);
        database.put(database.size() + 1, songName);
        return ResponseEntity.ok(new SingleSongResponseDto(songName));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id){
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");

//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorDeleteSongResponseDto("Song with id " + id + " not found", HttpStatus.NOT_FOUND));
        }
        database.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK));
    }

}
