package com.example.songify.song.infrastructure.controller.dto.response;

import com.example.songify.song.domain.model.Song;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateSongResponseDto(@JsonProperty("songs")Song song) {
}
