package com.example.songify.song.infrastructure.controller.dto.response;

import com.example.songify.song.domain.model.Song;

import java.util.Map;

public record GetAllSongsResponseDto(Map<Integer, Song> songs) {

}
