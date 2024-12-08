package com.example.songify.song.infrastructure.controller.dto.response;

import com.example.songify.song.domain.model.Song;

public record GetSongResponseDto(Song song) {
}
