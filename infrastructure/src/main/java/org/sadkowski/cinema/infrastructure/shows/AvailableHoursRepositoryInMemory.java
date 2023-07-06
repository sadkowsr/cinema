package org.sadkowski.cinema.infrastructure.shows;

import lombok.extern.slf4j.Slf4j;
import org.sadkowski.cinema.domain.shows.model.ShowTypeDto;
import org.sadkowski.cinema.domain.shows.model.AvailableHours;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.AvailableHoursReadRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.AvailableHoursWriteRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static org.sadkowski.cinema.domain.shows.model.ShowTypeDto.REGULAR;

@Service
@Slf4j
public class AvailableHoursRepositoryInMemory implements AvailableHoursReadRepository, AvailableHoursWriteRepository {
    public static final ShowTypeDto DEFAULT_TYPE = REGULAR;
    private final Map<ShowTypeDto, AvailableHours> availableHoursByShowTypeMap;

    public AvailableHoursRepositoryInMemory() {
        this.availableHoursByShowTypeMap = new EnumMap<>(ShowTypeDto.class);
    }

    @Override
    public Optional<AvailableHours> findAllByShowType(ShowTypeDto type) {
        log.debug("Find available hours by show type: {}", type);
        Optional<AvailableHours> availableHoursByShowTypeNullable = Optional.ofNullable(availableHoursByShowTypeMap.get(type));
        //if ShowTypeDto is not found, then return default type (REGULAR)
        AvailableHours availableHours = availableHoursByShowTypeNullable.orElse(availableHoursByShowTypeMap.get(DEFAULT_TYPE));
        log.info("Found available hours by show type: {}", availableHours);
        return Optional.of(availableHours);
    }

    @Override
    public void save(AvailableHours availableHours) {
        log.debug("Save available hours by show type: {}", availableHours);
        availableHoursByShowTypeMap.put(availableHours.showType(), availableHours);
        log.info("Saved available hours by show type: {}", availableHours);
    }

}
