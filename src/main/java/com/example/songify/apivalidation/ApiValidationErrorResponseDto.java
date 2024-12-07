package com.example.songify.apivalidation;

import java.util.List;

public record ApiValidationErrorResponseDto(List<String> errors) {
}
