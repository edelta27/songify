package com.example.songify.song.dto.response;

import com.example.songify.song.controller.Song;

public record PartiallyUpdateSongResponseDto(Song updateSong) {
}