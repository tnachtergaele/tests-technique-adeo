package adeo.leroymerlin.cdp.service;

import adeo.leroymerlin.cdp.entity.Band;
import adeo.leroymerlin.cdp.entity.Event;
import adeo.leroymerlin.cdp.entity.Member;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

public class EventServiceTest {

    private static EventRepository eventRepository = mock(EventRepository.class);

    private static final EventService cut = new EventService(eventRepository);

    @Test
    public void filteringEventsTest() {
        Band bandMetallica = createBand("Metallica", "Queen Anika Walsh", "Queen Katy Stone", "Queen Aliyah Jarvis");
        Band bandMegadeth = createBand("Megadeth", "Queen Haleema Poole");
        Band bandOffSpring = createBand("Off Spring", "Queen Charlie Wolf (Chick)", "Queen Aaliyah York");
        Event eventGrasPop = createEvent("GrasPop Metal Meeting", bandMetallica, bandMegadeth);
        Event eventDownload = createEvent("Download Festival", bandOffSpring);
        List<Event> events = Arrays.asList(eventGrasPop, eventDownload);

        String query = "li";

        Band expectedBandMetallica = createBand("Metallica", "Queen Aliyah Jarvis");
        Band expectedBandOffSpring = createBand("Off Spring", "Queen Charlie Wolf (Chick)", "Queen Aaliyah York");
        Event expectedEventGrasPop = createEvent("GrasPop Metal Meeting", expectedBandMetallica);
        Event expectedEventDownload = createEvent("Download Festival", expectedBandOffSpring);

        List<Event> filteredEvents = cut.filteringEvents(events, query);
        assertThat(filteredEvents).containsOnly(expectedEventGrasPop, expectedEventDownload);
    }

    private static Event createEvent(String title, Band... bands) {
        Event event = new Event();
        event.setTitle(title);
        event.setBands(new HashSet<>(Arrays.asList(bands)));
        return event;
    }

    private static Band createBand(String name, String... memberNames) {
        Band band = new Band();
        band.setName(name);
        band.setMembers(Arrays.stream(memberNames).map(EventServiceTest::createMember).collect(Collectors.toSet()));
        return band;
    }

    private static Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        return member;
    }

}
