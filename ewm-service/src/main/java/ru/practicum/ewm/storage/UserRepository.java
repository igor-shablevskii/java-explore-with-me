package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}