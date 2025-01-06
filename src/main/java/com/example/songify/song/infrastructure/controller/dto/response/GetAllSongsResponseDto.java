package com.example.songify.song.infrastructure.controller.dto.response;

import com.example.songify.song.domain.model.Song;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record GetAllSongsResponseDto(@JsonProperty("songs")Map<Integer, Song> songs) {

}
