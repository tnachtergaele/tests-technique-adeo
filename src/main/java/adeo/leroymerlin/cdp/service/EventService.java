package adeo.leroymerlin.cdp.service;

import adeo.leroymerlin.cdp.entity.Event;
import adeo.leroymerlin.cdp.entity.Member;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    public void saveEvent(Long id, Event event)  {
        if(eventRepository.findById(id).isPresent()) {
            Event eventToUpdate = eventRepository.findById(id).get();
            eventToUpdate.setNbStars(event.getNbStars());
            eventToUpdate.setComment(event.getComment());
            eventRepository.save(eventToUpdate);
        }
    }

    public List<Event> getFilteredEventsByMember(String query) {
        List<Event> matchingEvents = eventRepository.findDistinctByBands_Members_NameContaining(query);
        return filteringEvents(matchingEvents, query);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here
        List<Event> matchingEvents = events.stream().filter(event -> event.getBands().stream()
                .anyMatch(band -> band.getMembers().stream()
                        .anyMatch(member -> matchingMember(member, query))))
                .collect(Collectors.toList());
        return filteringEvents(matchingEvents, query);
    }

    List<Event> filteringEvents(List<Event> events, String query) {
        events.forEach(event -> event.getBands()
                .forEach(band -> band.setMembers(
                        band.getMembers().stream().filter(member -> matchingMember(member, query))
                                .collect(Collectors.toSet())
                )));
        events.forEach(event -> event.setBands(
                event.getBands().stream().filter(band -> !band.getMembers().isEmpty())
                        .collect(Collectors.toSet()))
        );
        return events;
    }

    private boolean matchingMember(Member member, String query) {
        return member.getName() != null && member.getName().contains(query);
    }

}
