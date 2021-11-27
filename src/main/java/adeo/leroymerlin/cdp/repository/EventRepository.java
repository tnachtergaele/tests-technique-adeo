package adeo.leroymerlin.cdp.repository;

import adeo.leroymerlin.cdp.entity.Event;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface EventRepository extends Repository<Event, Long> {

    @Transactional
    void delete(Long eventId);

    @Transactional
    void save(Event event);

    Optional<Event> findById(Long id);

    List<Event> findAllBy();
}
