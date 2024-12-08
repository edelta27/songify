package com.example.songify.song.infrastructure.controller.dto.request;

public record PartiallyUpdateSongRequstDto(
        String songName,
        String artist
) {
}
