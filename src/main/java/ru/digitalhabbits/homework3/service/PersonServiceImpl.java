package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentInfo;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl
        implements PersonService {

    private final PersonDao personDao;

    @Nonnull
    @Transactional
    @Override
    public List<PersonResponse> findAllPersons() {
        // TODO: NotImplemented: получение информации о всех людях во всех отделах
        return personDao.findAll().stream()
                .map(PersonServiceImpl::buildPersonResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Transactional
    @Override
    public PersonResponse getPerson(@Nonnull Integer id) {
        // TODO: NotImplemented: получение информации о человеке. Если не найдено, отдавать 404:NotFound
        Person person = Optional.ofNullable(personDao.findById(id)).orElseThrow(() ->
                new EntityNotFoundException("Person with id " + id + " not found"));
        Department department = person.getDepartment();
        return new PersonResponse()
                .setId(person.getId())
                .setFullName(buildFullName(person))
                .setAge(person.getAge())
                .setDepartment(department != null ?
                        new DepartmentInfo().setId(department.getId()).setName(department.getName()) : null);
    }

    @Nonnull
    @Transactional
    @Override
    public Integer createPerson(@Nonnull PersonRequest request) {
        // TODO: NotImplemented: создание новой записи о человеке
        Person person = new Person();
        person.setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());
        return personDao.create(person).getId();
    }

    @Nonnull
    @Transactional
    @Override
    public PersonResponse updatePerson(@Nonnull Integer id, @Nonnull PersonRequest request) {
        // TODO: NotImplemented: обновление информации о человеке. Если не найдено, отдавать 404:NotFound
        Person person = Optional.ofNullable(personDao.findById(id)).orElseThrow(() ->
                new EntityNotFoundException("Person with id " + id + " not found"));
        person.setFirstName(request.getFirstName() == null ? person.getFirstName() : request.getFirstName())
                .setMiddleName(request.getMiddleName() == null ? person.getMiddleName() : request.getMiddleName())
                .setLastName(request.getLastName() == null ? person.getLastName() : request.getLastName())
                .setAge(request.getAge() == null ? person.getAge() : request.getAge());
        personDao.update(person);
        Department department = person.getDepartment();
        return new PersonResponse()
                .setId(person.getId())
                .setAge(person.getAge())
                .setFullName(buildFullName(person))
                .setDepartment(department != null ? new DepartmentInfo().setId(department.getId())
                                                                        .setName(department.getName()) : null);
    }

    @Transactional
    @Override
    public void deletePerson(@Nonnull Integer id) {
        // TODO: NotImplemented: удаление информации о человеке и удаление его из отдела. Если не найдено, ничего не делать
        Optional.ofNullable(personDao.findById(id)).ifPresent(item -> personDao.delete(id));
    }

    private static PersonResponse buildPersonResponse(Person person) {
        Department department = person.getDepartment();
        return new PersonResponse()
                .setId(person.getId())
                .setFullName(buildFullName(person))
                .setAge(person.getAge())
                .setDepartment(department != null ?
                        new DepartmentInfo().setId(department.getId()).setName(department.getName()) : null);
    }

    private static String buildFullName(Person person) {
        String fullName = person.getFirstName();
        if(person.getMiddleName() != null) {
            fullName += " " + person.getMiddleName();
        }
        fullName += " " + person.getLastName();
        return fullName;
    }
}
