package org.sadkowski.cinema.domain.shows.appservices;

import org.sadkowski.cinema.domain.shows.appservices.commands.AddShowCommand;

public interface ApplicationServiceShow {

    void addShow(AddShowCommand query);
}
