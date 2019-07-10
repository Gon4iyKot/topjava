package ru.javawebinar.topjava.repository.jdbc.HSQLDB;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.jdbc.AbstractJdbcMealRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile(Profiles.HSQL_DB)
public class HSQLDBJdbcMealRepository extends AbstractJdbcMealRepository<Timestamp> {

    public HSQLDBJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);

    }

    @Override
    public Timestamp convertDateTime(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}