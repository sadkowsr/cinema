# Build and run
1. Please build and run tests:
```bash
./mvnw -T 3 clean install
```
2. Please run application:

```bash
 mvn spring-boot:run -pl application
```
or
```bash
 java -jar ./application/target/application-0.0.1-SNAPSHOT.jar
```

3. Open client API to query the service (Application must be running):
#### http://localhost:8080/swagger-ui/index.html

# Decision description for future developers
1. The application was split into modules using a hexagonal architecture.
2. We decided to use Spring Boot for quick start and easy configuration.

## Domain
1. The ShowValidator class is used to check the domain rules.
2. We decide to add a "planner" as a reference for each event (to learn about the addition of an event or future user permission hierarchy).
3. We decide to add 'movie' as a reference for each event (to find out what movie is displayed) without an additional REST query. An InMemory implementation, i.e. saving the object with the event, but there should only be a reference to the movie in the database.
4. Each event has one 'room'. For the moment, we do not foresee the need to e.g. create empty templates for later use.
5. We have decided to implement 'room not be available' as a special type of video.
6. We used a synchronisation mechanism to avoid concurrency problems by synchronising the block for only 2 block operations: checkIntersecting and saving the event. checkIntersecting is used in a typical validation, and before the saving itself to reduce the time needed for the transaction as much as possible.
7. The optional parameter in the domain should be wrapped in Optional, mandatory should not.
8. We use a separate interface for writing/reading data from memory.

## API
1. We decided to add an optional 'end' parameter to change the default time of an event (instead of from the movie directory) or to use an inaccessible room for multiple days.
2. a mandatory parameter to retrieve events is the 'start' parameter. We download events from this date for the next 7 days for all rooms (as shown in Cinema - screen 1).
3. We also retrieve an additional parameter as 'cleaningDuration', which can be different and independent of the room. For example, when we change this parameter for room 1, we do not change it in the event. If we use the one assigned to 'room' when we change the time for the room, all the graphics stored in the past are changed.

## Infrastructure
1. A domain object without API is automatically generated before the application is launched.
2. We use interface implementations, based on a repository, stored in a map to store data. The key is the id, the value is the object.
3. We do not block the storage map by multithreaded access, we implement the transaction at the domain layer.
4. instead of using a separate interface for reading and writing in 'map' based storage, we decided to implement both interfaces in one class - to avoid using one map through two implementations.

# High Way Cinema

Our client has a cinema in Wrocław, Poland. Currently, all movies schedule is done by Pen and Paper on big board where there is plan for given time for all movies the cinema shows. Planner Jadwiga needs to schedule seans(seans is movie schedule at given time)for best used of the space.

## Board overview

![Cinema - page 1 copy](https://user-images.githubusercontent.com/34231627/150541482-0b1e4a66-4298-4d3e-846f-c62ba1c8e37b.png)

## Dictionary

* **Show** - it a movie which embed in schedule meaning it have point of time and room picked.
* **Planner** - Person who works in cinema and manage week schedule of shows.
* **Movie Catalog** - A place where we have list of movies which are created, those are not necessary mean we are going showObjectDto those(but most likely we do).
* **Cleaning slot** -  Time slot after each showObjectDto we need care of cleaning the room.

## Domain requirements

We would like to help Jadwiga to do better job with his weekly task with planning the showObjectDto. Idea is to create virtual board that she will be able to add showObjectDto to the board.

User Stories:
- Planner Jadwiga will be able to schedule showObjectDto for given movie at particular time every day week from 8:00-22:00
- Any 2 scheduled movies can't be on same time and same room. Even the overlapping is forbidden.
- Every showObjectDto need to have maintenance slot to clean up whole Room. Every room have different cleaning slot.
- Some movies can have 3d glasses required.
- Not every movie are equal e.g. Premier need to be after working hours around 17:00-21:00
- There is possibilities that given room may not be available for particular time slot time or even days.


You task is to model the week planning of the showObjectDto by Jadwiga.

## Assumption
- Catalog of movies already exists(telling if it needs 3d glasses, how long the movie will take)

### Challenge notes

* Movie Catalog is not in scope of this challenge but some model will be required to fulfill given task
* Consider concurrency modification. How to solve problem
  when two Jadwiga's add different movies to same time and same room.
* If you have question to requirements simply just ask us.
* If during the assignment you will work on real database and UI you will lose precious time, so we encourage you to not do so.

#### What we care for:
- Solid domain model
- Quality of tests
- Clean code
- Proper module/context modeling

#### What we don’t care for:
- UI to be implemented
- Using database
- All the cases to be covered.

#### What we expect from solution:
- Treat it like production code. Develop your software in the same way that you would for any code that is intended to be deployed to production.
- Would be good to describe decision you make so future developers won't be scratching the head about the reasoning.
- Test should be green
- Code should be on github repo.
