package org.sadkowski.cinema.application.controller;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.sadkowski.cinema.application.CinemaApplication;
import org.sadkowski.cinema.application.shows.dto.ShowCreateDto;
import org.sadkowski.cinema.application.shows.dto.ShowDTO;
import org.sadkowski.cinema.domain.shows.ports.application.command.ShowCommandRepository;
import org.sadkowski.cinema.domain.shows.appservices.commands.AddShowCommand;
import org.sadkowski.cinema.domain.shows.model.*;
import org.sadkowski.cinema.domain.shows.ports.application.query.ShowQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.UUID.fromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.sadkowski.cinema.application.shows.adapters.ShowCommandHandler.buildAddShowCommand;
import static org.sadkowski.cinema.domain.shows.model.ShowTypeDto.PREMIERE;
import static org.sadkowski.cinema.domain.shows.builders.ShowBuilder.showOf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CinemaApplication.class)
@ActiveProfiles("test")
@RequiredArgsConstructor
//don't run tests from this class in parallel
class ShowIntegrationTest extends IntegrationTestBase {

    private static final String START_TIME_STRING = "2023-07-03T19:29:55.818Z";
    private static final ShowCreateDto SHOW_CREATE_DTO_SAMPLE = new ShowCreateDto(
            fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
            fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
            fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
            parse("2023-07-03T19:29:55.818Z", ISO_DATE_TIME),
            PREMIERE,
            parse("2023-07-03T20:29:55.818Z", ISO_DATE_TIME));

    @Autowired
    private final ShowQueryRepository showQueryRepository;

    @Autowired
    private final ShowCommandRepository showCommandRepository;
    @Test
    void shouldSaveShowWithCorrectData() {
        //when
        Response response = given()
                .contentType(JSON)
                .body(SHOW_CREATE_DTO_SAMPLE)
                .when()
                .post("/shows")
                .then()
                .statusCode(201)
                .body(Matchers.not(blankOrNullString()))
                .extract().response();

        //then
        UUID showId = UUIDof(response);
        Optional<Show> resultInDatabase = showQueryRepository.findByShowId(showId);
        assertThat(resultInDatabase.isPresent(), Matchers.is(true));
        assertThat(resultInDatabase.get().showId(), Matchers.is(showId));
        assertThat(resultInDatabase.get().room().roomId(), Matchers.is(SHOW_CREATE_DTO_SAMPLE.roomId()));
        assertThat(resultInDatabase.get().planner().plannerId(), Matchers.is(SHOW_CREATE_DTO_SAMPLE.movieId()));
        assertThat(resultInDatabase.get().start(), Matchers.is(SHOW_CREATE_DTO_SAMPLE.start()));
        assertThat(resultInDatabase.get().showType(), Matchers.is(SHOW_CREATE_DTO_SAMPLE.showType()));

        // Clean up
        showCommandRepository.delete(showId);
    }

    @Test
    void shouldThrowValidationExceptionIfTryPlaceOnOccupiedPlace() {
        // Prepare request body

        //when
        Response response = given()
                .contentType(JSON)
                .body(SHOW_CREATE_DTO_SAMPLE)
                .when()
                .post("/shows")
                .then()
                .statusCode(201)
                .body(Matchers.not(blankOrNullString()))
                .extract().response();
        UUID showId = UUIDof(response);

        //when
        Response response2 = given()
                .contentType(JSON)
                .body(SHOW_CREATE_DTO_SAMPLE)
                .when()
                .post("/shows")
                .then()
                .statusCode(400)
                .body(Matchers.not(blankOrNullString()))
                .extract().response();
        String bodyString = response2.getBody().asString().replace('"', ' ').trim();
        assertThat(bodyString.contains("Show.start"), Matchers.is(true));
        assertThat(bodyString.contains("Show.end"), Matchers.is(true));
        // Clean up
        showCommandRepository.delete(showId);
    }

    @Test
    void shouldThrowValidationExceptionIfTryPlaceOnOccupiedPlaceInOneTime() throws InterruptedException {
        // given
        final int NUM_THREADS = 3;

        //when
        ExtractableResponse<Response>[] responses = getExtractableResponses(NUM_THREADS, SHOW_CREATE_DTO_SAMPLE);

        //then
        long countSavedObject = Stream.of(responses)
                .filter(response -> response.statusCode() == HttpStatus.CREATED.value())
                .count();

        long countBadRequestResponse = Stream.of(responses)
                .filter(response -> response.statusCode() == HttpStatus.BAD_REQUEST.value())
                .count();

        assertThat(countSavedObject, Matchers.is(1L));
        assertThat(countBadRequestResponse, Matchers.is(NUM_THREADS - 1L));

        //cleanUp
        Stream.of(responses)
                .filter(response -> response.statusCode() == HttpStatus.CREATED.value())
                .map(response -> UUIDof(response.response()))
                .forEach(showCommandRepository::delete);
    }


    @Test
    void shouldRetrieverEmptyJsonWhenShowNotExists() {
        //when
        Response response = given()
                .pathParam("start", START_TIME_STRING)
                .contentType(JSON)
                .when()
                .get("/shows/{start}")
                //then
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .response();

        List<ShowDTO> shows = response.jsonPath().getList(".", ShowDTO.class);
        assertThat(shows, hasSize(0));
    }

    @Test
    void shouldRetrieverErrorWithBadParameter() {
        //given

        given()
                .pathParam("start", "bad_request_local_datetime")
                .contentType(JSON)

                //when
                .when()
                .get("/shows/{start}")

                //then
                .then()
                .statusCode(400);
    }

    @Test
    void shouldRetrieveListOfShowDTOListWhenUseCorrectStart() {
        //given
        Show show = createSampleShow();
        showCommandRepository.save(show);

        Response response = given()
                .pathParam("start", "2023-07-03T19:29:55.818Z")
                .contentType(JSON)

                //when
                .when()
                .get("/shows/{start}")

                //then
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .response();

        List<ShowDTO> shows = response.jsonPath().getList(".", ShowDTO.class);
        assertThat(shows, hasSize(1));

        ShowDTO showActual = shows.get(0);
        assertThat(showActual.showId(), Matchers.is(show.showId()));
        assertThat(showActual.roomId(), Matchers.is(show.room().roomId()));
        assertThat(showActual.movie().movieId(), Matchers.is(show.movie().movieId()));
        assertThat(showActual.movie().title(), Matchers.is(show.movie().title()));
        assertThat(showActual.roomId(), Matchers.is(show.room().roomId()));
        assertThat(showActual.planerId(), Matchers.is(show.planner().plannerId()));
        assertThat(showActual.start(), Matchers.is(show.start()));
        assertThat(showActual.end(), Matchers.is(show.end()));
        assertThat(showActual.showType(), Matchers.is(show.showType()));
        assertThat(showActual.durationCleaningTime(), Matchers.is(show.room().cleanRoomDurationMinutes()));

        //cleanUp
        showCommandRepository.delete(show.showId());
    }

    private static Show createSampleShow() {
        UUID showId = UUID.randomUUID();
        AddShowCommand addShowCommand = buildAddShowCommand(SHOW_CREATE_DTO_SAMPLE, showId);

        Optional<Room> room = Optional.of(Room.builder()
                .roomId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .cleanRoomDurationMinutes(30L)
                .name("Cinema Room 1")
                .build());

        Optional<Planner> planner = Optional.of(Planner.builder()
                .plannerId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .name("Jadwiga")
                .surname("Kowalska")
                .build());


        Optional<Movie> movie = Optional.of(Movie.builder()
                .movieId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .title("Lion King")
                .durationMovieInMinutes(120L)
                .glasses3DRequired(false)
                .build());

        Optional<AvailableHours> availableHours = Optional.of(AvailableHours.builder()
                .showType(SHOW_CREATE_DTO_SAMPLE.showType())
                .start(LocalTime.of(8, 0))
                .end(LocalTime.of(22, 0))
                .build());

        return showOf(addShowCommand, planner, movie, room, availableHours);
    }

    private static ExtractableResponse<Response>[] getExtractableResponses(int numberOfThread, ShowCreateDto showCreateDto) throws InterruptedException {
        Thread[] threads = new Thread[numberOfThread];
        ExtractableResponse<Response>[] responses = (ExtractableResponse<Response>[]) new ExtractableResponse[numberOfThread];
        CountDownLatch latch = new CountDownLatch(numberOfThread);
        for (int i = 0; i < numberOfThread; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                responses[index] = getAddingShowResponse(showCreateDto);
                latch.countDown();
            });
            threads[i].start();
        }
        latch.await();
        return responses;
    }

    private static ExtractableResponse
            <Response> getAddingShowResponse(ShowCreateDto showCreateDto) {
        return given()
                .contentType(JSON)
                .body(showCreateDto)
                .when()
                .post("/shows")
                .then()
                .body(Matchers.not(blankOrNullString()))
                .extract();
    }

    private static UUID UUIDof(Response response) {
        return fromString(response.getBody().asString().replace('"', ' ').trim());
    }
}