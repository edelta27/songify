package com.example.songify.song.dto.request;

public record PartiallyUpdateSongRequstDto(
        String songName,
        String artist
) {
}
