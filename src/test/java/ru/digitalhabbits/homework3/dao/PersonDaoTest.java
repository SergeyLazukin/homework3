package ru.digitalhabbits.homework3.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.util.PersonHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.digitalhabbits.homework3.util.DepartmentHelper.buildDepartment;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildPersonFromPersonRequest;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildPersonWithoutId;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PersonDaoTest {

    private static final Integer COUNT = 2;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonDao personDao;

    @Test
    void create() {
        Person person = buildPersonFromPersonRequest(PersonHelper.buildCreatePersonRequest());
        Person personCreated = personDao.create(person);
        assertNotNull(personCreated);
    }

    @Test
    void findById() {
        // TODO: NotImplemented
        Department department = buildDepartment();
        entityManager.persist(department);
        Person person = buildPersonWithoutId(department);
        entityManager.persist(person);
        Person personLoad = personDao.findById(person.getId());

        assertThat(person).isEqualToComparingFieldByField(personLoad);
    }

    @Test
    void findAll() {
        // TODO: NotImplemented
        Department department = buildDepartment();
        entityManager.persist(department);
        List<Person> personList = IntStream.range(0, COUNT).mapToObj(i -> buildPersonWithoutId(department))
                .map(person -> entityManager.persist(person)).collect(Collectors.toList());
        List<Person> personListLoad = personDao.findAll();

        assertThat(personList).isEqualTo(personListLoad);
    }

    @Test
    void update() {
        // TODO: NotImplemented
        Person person = buildPersonWithoutId();
        Person personOld = entityManager.persist(person);
        Integer id = personOld.getId();
        personOld.setFirstName("Ivan");
        Person personUpdate = entityManager.merge(personOld);
        assertEquals(personUpdate.getId(), id);
        assertEquals(personUpdate.getFirstName(), "Ivan");
    }

    @Test
    void delete() {
        // TODO: NotImplemented
        Person person = entityManager.persist(buildPersonWithoutId());
        Person personDeleted = personDao.delete(person.getId());
        Person personNotFound = entityManager.find(Person.class, personDeleted.getId());
        assertNull(personNotFound);
    }
}