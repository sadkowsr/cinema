package org.sadkowski.cinema.domain.shows.ports.application;

import org.sadkowski.cinema.domain.shows.appservices.commands.AddShowCommand;

public interface ApplicationServiceShowAdd {

    void addShow(AddShowCommand query);
}
