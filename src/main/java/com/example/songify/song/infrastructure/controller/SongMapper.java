package com.example.songify.song.infrastructure.controller;

import com.example.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.example.songify.song.infrastructure.controller.dto.response.CreateSongResponseDto;
import com.example.songify.song.domain.model.Song;

public class SongMapper {
    public static Song mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        Song song = new Song(dto.songName(), dto.artist());
        return song;
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        return new CreateSongResponseDto(song);
    }
}
