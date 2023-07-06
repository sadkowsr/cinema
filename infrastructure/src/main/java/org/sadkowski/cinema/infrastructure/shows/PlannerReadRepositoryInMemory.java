package org.sadkowski.cinema.infrastructure.shows;

import lombok.extern.slf4j.Slf4j;
import org.sadkowski.cinema.domain.shows.model.Planner;

import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.PlannerReadRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.PlannerWriteRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class PlannerReadRepositoryInMemory implements PlannerReadRepository, PlannerWriteRepository {

    private final Map<UUID, Planner> planners;

    public PlannerReadRepositoryInMemory() {
        this.planners = new HashMap<>();
    }


    @Override
    public Optional<Planner> findAllById(UUID movieId) {
        log.debug("Finding planner by id: {}", movieId);
        Optional<Planner> planner = Optional.of(planners.get(movieId));
        log.info("Found planner: {}", planner);
        return planner;
    }

    @Override
    public void save(Planner planer) {
        log.debug("Saving planner: {}", planer);
        planners.put(planer.plannerId(), planer);
        log.info("Saved planner: {}", planer);
    }
}
