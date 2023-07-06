package org.sadkowski.cinema.application.shows.controllers;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadkowski.cinema.application.shows.adapters.ShowCommandHandler;
import org.sadkowski.cinema.application.shows.adapters.ShowQueryHandler;
import org.sadkowski.cinema.application.shows.dto.ShowCreateDto;
import org.sadkowski.cinema.application.shows.dto.ShowDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
@Slf4j
public class ShowController {

    private final ShowCommandHandler showCommandHandler;
    private final ShowQueryHandler showQueryHandler;

    @PostMapping
    public ResponseEntity<UUID> createShow(@Valid @RequestBody ShowCreateDto showCreateDto) {
        return new ResponseEntity<>(showCommandHandler.handle(showCreateDto), CREATED);
    }

    @GetMapping("/{start}")
    public ResponseEntity<List<ShowDTO>> getAllShows(@DateTimeFormat(iso = DATE_TIME) @Valid @PathVariable LocalDateTime start) {
        List<ShowDTO> shows = showQueryHandler.getShows(start);
        return new ResponseEntity<>(shows, OK);
    }

    @ExceptionHandler({ValidationException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleValidationException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }


}
