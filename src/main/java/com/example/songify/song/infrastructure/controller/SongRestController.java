package com.example.songify.song.infrastructure.controller;

import com.example.songify.song.domain.service.SongAdder;
import com.example.songify.song.domain.service.SongRetreiver;
import com.example.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequstDto;
import com.example.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.example.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.example.songify.song.domain.model.SongNotFoundException;
import com.example.songify.song.domain.model.Song;
import com.example.songify.song.infrastructure.controller.dto.response.*;
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
@RequestMapping("/songs")
public class SongRestController {
    private final SongAdder songAdder;
    private final SongRetreiver songRetreiver;
    private Map<Integer, Song> allSongs;

    public SongRestController(SongAdder songAdder, SongRetreiver songRetreiver) {
        this.songAdder = songAdder;
        this.songRetreiver = songRetreiver;
    }

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit){
        Map<Integer, Song> allSongs = songRetreiver.findAll();
        if(limit != null) {
            Map<Integer, Song> limitedMap = allSongs.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }

        GetAllSongsResponseDto response = new GetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Integer id, @RequestHeader(required = false) String requestId){
        log.info(requestId);
        if(!songRetreiver.findAll().containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        Song song = songRetreiver.findAll().get(id);
        GetSongResponseDto response = new GetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request){
        Song song = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        songAdder.addSong(song);
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(song);
        return ResponseEntity.ok(body);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id){
        if(!songRetreiver.findAll().containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        songRetreiver.findAll().remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request){
        if(!songRetreiver.findAll().containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        String newSongName = request.songName();
        String newArtist = request.artist();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = songRetreiver.findAll().put(id, newSong);
        log.info("Update song with id: " + id +
                " with oldSongName: " + oldSong.name() + " to newSongName: " + newSong.name() +
                " oldArtist: " + oldSong.artist() + " to newArtist: " + newSong.artist()
        );
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(), newSong.artist()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody PartiallyUpdateSongRequstDto request){
        if (!songRetreiver.findAll().containsKey(id)){
            throw new SongNotFoundException("Song with id " + id + " not found");
        }
        Song songFromDatabase = songRetreiver.findAll().get(id);
        Song.SongBuilder builder = Song.builder();
        if(request.songName() != null){
            builder.name(request.songName());
            log.info("partially updated song name");
        } else {
            builder.name(songFromDatabase.name());
        }
        if(request.artist() != null){
            builder.artist(request.artist());
            log.info("partially updated artist");
        } else {
            builder.artist(songFromDatabase.artist());
        }
        Song updatedSong = builder.build();
        songRetreiver.findAll().put(id, updatedSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong));
    }
}
