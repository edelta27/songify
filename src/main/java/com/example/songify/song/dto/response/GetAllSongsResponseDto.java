package com.example.songify.song.dto.response;

import com.example.songify.song.controller.Song;

import java.util.Map;

public record GetAllSongsResponseDto(Map<Integer, Song> songs) {

}
