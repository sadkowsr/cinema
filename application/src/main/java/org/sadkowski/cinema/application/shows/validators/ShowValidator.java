package org.sadkowski.cinema.application.shows.validators;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.sadkowski.cinema.domain.shows.model.Show;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.ShowReadRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowValidator implements Validator {

private final ShowReadRepository showReadRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Show.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Show show = (Show) target;
        checkStartEarlierThanEnd(errors, show);
        checkHours(errors, show.availableHours().start(), show.availableHours().end(), show);
        checkIntersect(errors, show);
    }

    private void checkStartEarlierThanEnd(Errors errors, Show show) {
        if (show.start().isAfter(show.end())) {
            errors.rejectValue("start", "Start time is after end time.");
            errors.rejectValue("end", "End time is before start time.");
        }
    }

    private void checkIntersect(Errors errors, Show show) {
        if(showReadRepository.intersect(show.room().roomId(), show.start(), show.end())){
            errors.rejectValue("start", "Show is intersecting with other show.");
            errors.rejectValue("end", "Show is intersecting with other show.");
        }
    }


    private static void checkHours(Errors errors, LocalTime startOfWorkingHours, LocalTime endOfWorkingHours, Show show) {

        LocalTime startTimeOfShow = show.start().toLocalTime();
        LocalTime endTimeOfShow = show.end().toLocalTime().plusMinutes(show.room().cleanRoomDurationMinutes());
        if (startTimeOfShow.isBefore(startOfWorkingHours)) {
            errors.rejectValue("start", "Start time is before starting date of working.");
        }
        if (endTimeOfShow.isAfter(endOfWorkingHours)) {
            errors.rejectValue("end", "End time with cleaning is after ending date of working.");
        }
    }

    public void handleValidationResult(Errors errors) {
        List<String> errorsList =
                errors.getFieldErrors().stream()
                        .map(fieldError -> String.format("Validate error field: %s.%s. %s%n",
                                fieldError.getObjectName(),
                                fieldError.getField(),
                                fieldError.getCode()))
                        .toList();

        if (!errorsList.isEmpty()) {
            throw new ValidationException(errorsList.toString());
        }
    }

}
