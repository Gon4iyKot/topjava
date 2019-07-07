import org.junit.runner.RunWith;
import ru.javawebinar.topjava.service.meal.DataJpaMealServiceTest;
import ru.javawebinar.topjava.service.meal.JdbcMealServiceTest;
import ru.javawebinar.topjava.service.meal.JpaMealServiceTest;
import ru.javawebinar.topjava.service.user.DataJpaUserServiceTest;
import ru.javawebinar.topjava.service.user.JdbcUserServiceTest;
import ru.javawebinar.topjava.service.user.JpaUserServiceTest;
import ru.javawebinar.topjava.web.user.InMemoryAdminRestControllerSpringTest;
import ru.javawebinar.topjava.web.user.InMemoryAdminRestControllerTest;

@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        DataJpaMealServiceTest.class,
        JpaMealServiceTest.class,
        JdbcMealServiceTest.class,
        DataJpaUserServiceTest.class,
        JpaUserServiceTest.class,
        JdbcUserServiceTest.class,
        InMemoryAdminRestControllerSpringTest.class,
        InMemoryAdminRestControllerTest.class
})
public class AllInOne {
}
