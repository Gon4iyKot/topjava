package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> userRepository = new ConcurrentSkipListMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userRepository.put(user.getId(), user);
            return user;
        }

        return userRepository.computeIfPresent(user.getId(), (key, value) -> user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userRepository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userRepository.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userRepository.values().stream()
                .filter(value -> value.getEmail().equals(email)).findAny().orElse(null);
    }
}