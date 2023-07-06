package org.sadkowski.cinema.application.shows.startup;

import lombok.RequiredArgsConstructor;
import org.sadkowski.cinema.domain.shows.model.AvailableHours;
import org.sadkowski.cinema.domain.shows.model.Movie;
import org.sadkowski.cinema.domain.shows.model.Planner;
import org.sadkowski.cinema.domain.shows.model.Room;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.AvailableHoursWriteRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.MovieWriteRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.PlannerWriteRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.RoomWriteRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.UUID;

import static org.sadkowski.cinema.domain.shows.model.ShowTypeDto.PREMIERE;
import static org.sadkowski.cinema.domain.shows.model.ShowTypeDto.REGULAR;

@Component
@RequiredArgsConstructor
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    private final MovieWriteRepository movieWriteRepository;
    private final PlannerWriteRepository plannerWriteRepository;
    private final RoomWriteRepository roomWriteRepository;
    private final AvailableHoursWriteRepository availableHoursWriteRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        generateMoviesCatalog();
        generatePlanners();
        generateRooms();
        generateAvailableHours();
    }

    private void generateAvailableHours() {
        availableHoursWriteRepository.save(
                AvailableHours.builder()
                        .showType(REGULAR)
                        .start(LocalTime.of(8, 0))
                        .end(LocalTime.of(22, 0))
                        .build()
        );

        availableHoursWriteRepository.save(
                AvailableHours.builder()
                        .showType(PREMIERE)
                        .start(LocalTime.of(17, 0))
                        .end(LocalTime.of(21, 0))
                        .build()
        );
    }

    private static final String UUID_STRING = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
    private void generateRooms() {
        roomWriteRepository.save(Room.builder()
                .roomId(UUID.fromString(UUID_STRING))
                .cleanRoomDurationMinutes(30L)
                .name("Cinema Room 1")
                .build());
    }

    private void generatePlanners() {
        plannerWriteRepository.save(Planner.builder()
                .plannerId(UUID.fromString(UUID_STRING))
                .name("Jadwiga")
                .surname("Kowalska")
                .build());
    }

    private void generateMoviesCatalog() {
        movieWriteRepository.save(Movie.builder()
                .movieId(UUID.fromString(UUID_STRING))
                .title("Lion King")
                .durationMovieInMinutes(120L)
                .glasses3DRequired(false)
                .build());

        movieWriteRepository.save(Movie.builder()
                .movieId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa7"))
                .title("Avatar 2: 3D")
                .durationMovieInMinutes(180L)
                .glasses3DRequired(true)
                .build());

        movieWriteRepository.save(Movie.builder()
                .movieId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa8"))
                .title("TECHNICAL BREAK")
                .durationMovieInMinutes(0L)
                .glasses3DRequired(false)
                .build());
    }
}