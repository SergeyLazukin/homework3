package ru.digitalhabbits.homework3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildCreatePersonRequest;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildFullName;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildNewPerson;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildPerson;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersonServiceImpl.class)
class PersonServiceTest {

    private static final Integer COUNT = 2;

    @MockBean
    private PersonDao personDao;

    @Autowired
    private PersonService personService;

    @Test
    void findAllPersons() {
        // TODO: NotImplemented
        when(personDao.findAll()).thenReturn(range(0, COUNT)
                .mapToObj(i -> buildPerson()).collect(Collectors.toList()));

        List<PersonResponse> array = personService.findAllPersons();
        assertEquals(COUNT, array.size());
    }

    @Test
    void getPerson() {
        // TODO: NotImplemented
        Person person = buildPerson();
        when(personDao.findById(any(Integer.class))).thenReturn(person);
        PersonResponse personResponse = personService.getPerson(person.getId());

        assertEquals(personResponse.getId(), person.getId());
        assertEquals(personResponse.getAge(), person.getAge());
        assertEquals(personResponse.getFullName(), buildFullName(person));
        assertEquals(personResponse.getDepartment().getId(), person.getDepartment().getId());
        assertEquals(personResponse.getDepartment().getName(), person.getDepartment().getName());
    }

    @Test
    void createPerson() {
        // TODO: NotImplemented
        PersonRequest request = buildCreatePersonRequest();
        Person person = buildNewPerson(request);
        when(personDao.create(any(Person.class))).thenReturn(person);

        assertEquals(personService.createPerson(request), person.getId());
    }

    @Test
    void updatePerson() {
        // TODO: NotImplemented
        Person person = buildPerson();
        Integer id = person.getId();
        String fullNameOld = buildFullName(person);
        Integer age = person.getAge();
        PersonRequest personRequest = new PersonRequest().setFirstName(randomAlphabetic(4));
        Person personLoad = new Person().setId(person.getId())
                .setAge(personRequest.getAge() == null ? person.getAge() : personRequest.getAge())
                .setFirstName(personRequest.getFirstName() == null ? person.getFirstName() : personRequest.getFirstName())
                .setMiddleName(personRequest.getMiddleName() == null ? person.getMiddleName() : personRequest.getMiddleName())
                .setLastName(personRequest.getLastName() == null ? person.getLastName() : personRequest.getLastName());

        when(personDao.findById(any(Integer.class))).thenReturn(person);
        when(personDao.update(any(Person.class))).thenReturn(personLoad);

        PersonResponse personResponse = personService.updatePerson(person.getId(), personRequest);

        assertEquals(personResponse.getId(), id);
        assertEquals(personResponse.getAge(), age);
        assertNotEquals(personResponse.getFullName(), fullNameOld);
    }

    @Test
    void deletePerson() {
        // TODO: NotImplemented
        Person person = buildPerson();
        Integer id = person.getId();

        when(personDao.findById(any(Integer.class))).thenReturn(person);
        when(personDao.delete(any(Integer.class))).thenReturn(person);

        personService.deletePerson(person.getId());
        verify(personDao, times(1)).findById(id);
        verify(personDao, times(1)).delete(id);
    }
}