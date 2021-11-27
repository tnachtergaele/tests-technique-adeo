package adeo.leroymerlin.cdp.repository;

import adeo.leroymerlin.cdp.entity.Event;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EventRepository extends Repository<Event, Long> {

    @Transactional
    void delete(Long eventId);

    List<Event> findAllBy();
}
