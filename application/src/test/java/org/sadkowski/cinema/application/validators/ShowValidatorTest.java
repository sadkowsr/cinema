package org.sadkowski.cinema.application.validators;

import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sadkowski.cinema.application.shows.dto.ShowCreateDto;
import org.sadkowski.cinema.application.shows.validators.ShowValidator;
import org.sadkowski.cinema.domain.shows.appservices.commands.AddShowCommand;
import org.sadkowski.cinema.domain.shows.model.*;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.ShowReadRepository;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.sadkowski.cinema.application.shows.adapters.ShowCommandHandler.buildAddShowCommand;
import static org.sadkowski.cinema.domain.shows.builders.ShowBuilder.showOf;
import static org.sadkowski.cinema.domain.shows.model.ShowTypeDto.PREMIERE;

@ExtendWith(MockitoExtension.class)
class ShowValidatorTest {
    @Mock
    ShowReadRepository showRepository;
    @InjectMocks
    ShowValidator showValidator;

    private static Object[][] classSupportInputData() {
        return new Object[][]{
                {Show.class, true},
                {Object.class, false}
        };
    }

    @ParameterizedTest()
    @MethodSource("classSupportInputData")
    void testSupports(Class clazz, boolean expected) {
        boolean result = showValidator.supports(clazz);
        assertEquals(expected, result);
    }

    @Test
    void shouldNotCollectAnyErrorWhenDataIsCorrect() {
        //given
        when(showRepository.intersect(any(), any(), any())).thenReturn(false);
        Errors errors = new BeanPropertyBindingResult(CREATE_SAMPLE_OK, "Show");
        showValidator.validate(CREATE_SAMPLE_OK, errors);
        //then
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    void shouldIntersectErrorWhenIntersectReturnTrue() {
        //given
        when(showRepository.intersect(any(), any(), any())).thenReturn(true);
        Errors errors = new BeanPropertyBindingResult(CREATE_SAMPLE_OK, "Show");
        //when
        showValidator.validate(CREATE_SAMPLE_OK, errors);
        //then
        assertEquals(2, errors.getErrorCount());
        assertEquals(1, errors.getFieldErrors().stream().filter(isFieldError("start", "Show is intersecting with other show")).count());
        assertEquals(1, errors.getFieldErrors().stream().filter(isFieldError("end", "Show is intersecting with other show")).count());
    }

    @Test
    void shouldEndBeforeStartErrorWhenEndBeforeStart() {
        //given
        when(showRepository.intersect(any(), any(), any())).thenReturn(false);
        Errors errors = new BeanPropertyBindingResult(CREATE_SAMPLE_OK, "Show");
        //when
        showValidator.validate(CREATE_SAMPLE_WHERE_END_BEFORE_START, errors);
        //then
        assertEquals(2, errors.getErrorCount());
        assertEquals(1, errors.getFieldErrors().stream().filter(isFieldError("start", "Start time is after end time")).count());
        assertEquals(1, errors.getFieldErrors().stream().filter(isFieldError("end", "End time is before start time")).count());
    }


    @Test
    void shouldReturnErrorForStartAndEndWhenStartAndEndOutsideServiceHours() {
        //given
        when(showRepository.intersect(any(), any(), any())).thenReturn(false);
        Errors errors = new BeanPropertyBindingResult(CREATE_SAMPLE_OK, "Show");
        //when
        showValidator.validate(CREATE_SAMPLE_WHERE_NOT_AVAILABLE_HOURS, errors);
        //then
        assertEquals(2, errors.getErrorCount());
        assertEquals(1, errors.getFieldErrors().stream().filter(isFieldError("start", "Start time is before starting date of working")).count());
        assertEquals(1, errors.getFieldErrors().stream().filter(isFieldError("end", "End time with cleaning is after ending date of working")).count());
    }

    @Test
    void shouldThrowValidationErrorWhenErrorIsNotEmpty() {
        //given
        Errors errors = new BeanPropertyBindingResult(CREATE_SAMPLE_OK, "Show");
        errors.rejectValue("start", "Start time is after end time.");
        //when
        assertThrows(ValidationException.class, () -> showValidator.handleValidationResult(errors));
    }

    private Predicate<FieldError> isFieldError(String field, String message) {
        return error -> error.getField().equals(field) &&
                error.getObjectName().equals("Show") &&
                error.getCode().contains(message);
    }

    private static final ShowCreateDto SHOW_CREATE_DTO_SAMPLE;
    private static final ShowCreateDto SHOW_CREATE_DTO_SAMPLE_END_BEFORE_START;
    private static final UUID SHOW_ID_SAMPLE;
    private static final AddShowCommand ADD_SHOW_COMMAND_SAMPLE;
    private static final AddShowCommand ADD_SHOW_COMMAND_SAMPLE_END_BEFORE_START;
    private static final Optional<Room> ROOM_SAMPLE;
    private static final Optional<Planner> PLANNER_SAMPLE;
    private static final Optional<Movie> MOVIE_SAMPLE;
    private static final Optional<AvailableHours> AVAILABLE_HOURS_SAMPLE;

    private static final Show CREATE_SAMPLE_OK;

    private static final Show CREATE_SAMPLE_WHERE_END_BEFORE_START;

    private static final ShowCreateDto SHOW_CREATE_DTO_SAMPLE_NOT_IN_AVAILABLE_HOUR;

    private static final AddShowCommand ADD_SHOW_COMMAND_SAMPLE_NOT_IN_AVAILABLE_HOURS;

    private static final Show CREATE_SAMPLE_WHERE_NOT_AVAILABLE_HOURS;

    static {
        SHOW_CREATE_DTO_SAMPLE = new ShowCreateDto(
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                parse("2023-07-03T19:29:55.818Z", ISO_DATE_TIME),
                PREMIERE,
                parse("2023-07-03T20:29:55.818Z", ISO_DATE_TIME));

        SHOW_CREATE_DTO_SAMPLE_END_BEFORE_START = new ShowCreateDto(
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                parse("2023-07-03T20:29:55.818Z", ISO_DATE_TIME),
                PREMIERE,
                parse("2023-07-03T19:29:55.818Z", ISO_DATE_TIME));

        SHOW_CREATE_DTO_SAMPLE_NOT_IN_AVAILABLE_HOUR = new ShowCreateDto(
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                parse("2023-07-03T06:29:55.818Z", ISO_DATE_TIME),
                PREMIERE,
                parse("2023-07-03T22:29:55.818Z", ISO_DATE_TIME));

        SHOW_ID_SAMPLE = UUID.randomUUID();
        ADD_SHOW_COMMAND_SAMPLE = buildAddShowCommand(SHOW_CREATE_DTO_SAMPLE, SHOW_ID_SAMPLE);
        ADD_SHOW_COMMAND_SAMPLE_END_BEFORE_START = buildAddShowCommand(SHOW_CREATE_DTO_SAMPLE_END_BEFORE_START, SHOW_ID_SAMPLE);
        ADD_SHOW_COMMAND_SAMPLE_NOT_IN_AVAILABLE_HOURS = buildAddShowCommand(SHOW_CREATE_DTO_SAMPLE_NOT_IN_AVAILABLE_HOUR, SHOW_ID_SAMPLE);

        ROOM_SAMPLE = Optional.of(Room.builder()
                .roomId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .cleanRoomDurationMinutes(30L)
                .name("Cinema Room 1")
                .build());
        PLANNER_SAMPLE = Optional.of(Planner.builder()
                .plannerId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .name("Jadwiga")
                .surname("Kowalska")
                .build());
        MOVIE_SAMPLE = Optional.of(Movie.builder()
                .movieId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .title("Lion King")
                .durationMovieInMinutes(120L)
                .glasses3DRequired(false)
                .build());
        AVAILABLE_HOURS_SAMPLE = Optional.of(AvailableHours.builder()
                .showType(SHOW_CREATE_DTO_SAMPLE.showType())
                .start(LocalTime.of(8, 0))
                .end(LocalTime.of(22, 0))
                .build());

        CREATE_SAMPLE_OK = showOf(ADD_SHOW_COMMAND_SAMPLE, PLANNER_SAMPLE, MOVIE_SAMPLE, ROOM_SAMPLE,
                AVAILABLE_HOURS_SAMPLE);
        CREATE_SAMPLE_WHERE_END_BEFORE_START = showOf(ADD_SHOW_COMMAND_SAMPLE_END_BEFORE_START,
                PLANNER_SAMPLE, MOVIE_SAMPLE, ROOM_SAMPLE, AVAILABLE_HOURS_SAMPLE);
        CREATE_SAMPLE_WHERE_NOT_AVAILABLE_HOURS = showOf(ADD_SHOW_COMMAND_SAMPLE_NOT_IN_AVAILABLE_HOURS,
                PLANNER_SAMPLE, MOVIE_SAMPLE, ROOM_SAMPLE, AVAILABLE_HOURS_SAMPLE);


    }
}

