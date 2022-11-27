package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {

    List<Request> findAllByRequester(User user);

    Optional<Request> findByIdAndRequester(Long id, User user);

    Optional<Request> findByRequesterAndEvent(User user, Event event);
}