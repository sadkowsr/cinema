package org.sadkowski.cinema.application.shows.adapters.domain;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadkowski.cinema.application.shows.validators.ShowValidator;
import org.sadkowski.cinema.domain.shows.ports.application.ApplicationServiceShowAdd;
import org.sadkowski.cinema.domain.shows.model.*;
import org.sadkowski.cinema.domain.shows.appservices.commands.AddShowCommand;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.*;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.ShowWriteRepository;
import org.springframework.stereotype.Component;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.*;

import static org.sadkowski.cinema.domain.shows.builders.ShowBuilder.showOf;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationServiceShowAddImp implements ApplicationServiceShowAdd {

    private final MovieReadRepository movieReadRepository;
    private final RoomReadRepository roomReadRepository;
    private final PlannerReadRepository plannerReadRepository;
    private final ShowReadRepository showReadRepository;
    private final ShowWriteRepository showWriteRepository;
    private final AvailableHoursReadRepository availableHoursReadRepository;
    private final ShowValidator validator;
    private final Object lock = new Object();

    @Override
    public void addShow(AddShowCommand command) {
        Show show = retrieveShowData(command);
        validateData(show);
        saveInTransaction(show);
    }

    private void saveInTransaction(Show show) {
        log.debug("Saving show: {}", show);
        //synchronized block and intersect //@Transactional on method level
        synchronized (lock) {
            log.info(("Starting transaction of saving show"));
            if (showReadRepository.intersect(show.room().roomId(), show.start(), show.end())) {
                throw new ValidationException("Show intersects with another show");
            }
            showWriteRepository.save(show);
            log.info(("Finish transaction of saving show"));
        }
    }

    private void validateData(Show show) {
        log.debug("Validating show: {}", show);
        Errors errors = new BeanPropertyBindingResult(show, "Show");
        validator.validate(show, errors);
        validator.handleValidationResult(errors);
    }

    private Show retrieveShowData(AddShowCommand command) {
        log.debug("Adding show: {}", command);
        Optional<Planner> planner = plannerReadRepository.findAllById(command.plannerId());
        Optional<Movie> movie = movieReadRepository.findAllById(command.movieId());
        Optional<Room> room = roomReadRepository.findById(command.roomId());
        Optional<AvailableHours> availableHoursByShowType = availableHoursReadRepository.findAllByShowType(command.showType());
        return showOf(command, planner, movie, room, availableHoursByShowType);
    }

}
