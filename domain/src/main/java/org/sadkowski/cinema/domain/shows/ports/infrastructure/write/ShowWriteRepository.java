package org.sadkowski.cinema.domain.shows.ports.infrastructure.write;

import org.sadkowski.cinema.domain.shows.model.Show;

import java.util.UUID;

public interface ShowWriteRepository {
  void save (Show command);
  boolean delete(UUID showId);
}
