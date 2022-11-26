package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}