package ru.javawebinar.topjava.repository.jdbc.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.jdbc.AbstractJdbcMealRepository;

import java.time.LocalDateTime;

@Repository
@Profile(Profiles.POSTGRES_DB)
public class PostgresJdbcMealRepository extends AbstractJdbcMealRepository {

    public PostgresJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public LocalDateTime convertDateTime(LocalDateTime dateTime) {
        return dateTime;
    }
}