package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.dao.DepartmentDao;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.exceptions.ConflictException;
import ru.digitalhabbits.homework3.model.DepartmentRequest;
import ru.digitalhabbits.homework3.model.DepartmentResponse;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;
import ru.digitalhabbits.homework3.model.PersonInfo;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
        implements DepartmentService {
    private final DepartmentDao departmentDao;
    private final PersonDao personDao;

    @Nonnull
    @Transactional
    @Override
    public List<DepartmentShortResponse> findAllDepartments() {
        // TODO: NotImplemented: получение краткой информации о всех департаментах
        List<Department> departments = departmentDao.findAll();
        return departments.stream()
                .map(DepartmentServiceImpl::buildDepartmentShortResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Transactional
    @Override
    public DepartmentResponse getDepartment(@Nonnull Integer id) {
        // TODO: NotImplemented: получение подробной информации о департаменте и краткой информации о людях в нем.
        //  Если не найдено, отдавать 404:NotFound
        Department department = Optional.ofNullable(departmentDao.findById(id)).orElseThrow(() ->
                new EntityNotFoundException("Department with id " + id + " not found"));
        List<Person> personList = department.getPeople();
        return new DepartmentResponse().setId(department.getId())
                    .setClosed(department.isClosed())
                    .setName(department.getName())
                    .setPersons(personList.stream().map(person -> new PersonInfo()
                                    .setId(person.getId()).setFullName(buildFullName(person)))
                                .collect(Collectors.toList()));
    }

    @Nonnull
    @Transactional
    @Override
    public Integer createDepartment(@Nonnull DepartmentRequest request) {
        // TODO: NotImplemented: создание нового департамента
        Department departmentNew = new Department().setName(request.getName());
        return departmentDao.create(departmentNew).getId();
    }

    @Nonnull
    @Transactional
    @Override
    public DepartmentResponse updateDepartment(@Nonnull Integer id, @Nonnull DepartmentRequest request) {
        // TODO: NotImplemented: обновление данных о департаменте. Если не найдено, отдавать 404:NotFound
        Department department = Optional.ofNullable(departmentDao.findById(id)).orElseThrow(() ->
                new EntityNotFoundException("Department with id " + id + " not found"));
        department.setName(request.getName());
        departmentDao.update(department);
        return new DepartmentResponse()
                .setId(department.getId())
                .setName(department.getName())
                .setClosed(department.isClosed())
                .setPersons(department.getPeople().stream().map(person -> new PersonInfo()
                                .setId(person.getId()).setFullName(buildFullName(person)))
                            .collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void deleteDepartment(@Nonnull Integer id) {
        // TODO: NotImplemented: удаление всех людей из департамента и удаление самого департамента.
        //  Если не найдено, то ничего не делать
        Optional<Department> optional = Optional.ofNullable(departmentDao.findById(id));
        if(optional.isPresent()) {
            Department department = optional.get();
            List<Person> personList = department.getPeople();
            for(Person person : personList) {
                person.setDepartment(null);
            }
            departmentDao.update(department);
            departmentDao.delete(department.getId());
        }
    }

    @Transactional
    @Override
    public void addPersonToDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {
        // TODO: NotImplemented: добавление нового человека в департамент.
        //  Если не найден человек или департамент, отдавать 404:NotFound.
        //  Если департамент закрыт, то отдавать 409:Conflict
        Department department = Optional.ofNullable(departmentDao.findById(departmentId)).orElseThrow(() ->
                new EntityNotFoundException("Department with id " + departmentId + " not found"));
        Person person = Optional.ofNullable(personDao.findById(personId)).orElseThrow(() ->
                new EntityNotFoundException("Person with id " + personId + " not found"));
        if(department.isClosed()) {
            throw new ConflictException("Department is closed");
        }
        person.setDepartment(department);
        department.getPeople().add(person);
        departmentDao.update(department);
    }

    @Transactional
    @Override
    public void removePersonToDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {
        // TODO: NotImplemented: удаление человека из департамента.
        //  Если департамент не найден, отдавать 404:NotFound, если не найден человек в департаменте, то ничего не делать
        Department department = Optional.ofNullable(departmentDao.findById(departmentId)).orElseThrow(() ->
                new EntityNotFoundException("Department with id " + departmentId + " not found"));
        Optional<Person> personOptional = Optional.ofNullable(personDao.findById(personId));
        if(personOptional.isEmpty()) return;
        Person person = personOptional.get();
        List<Person> personList = department.getPeople();
        if(personList.contains(person)) {
            person.setDepartment(null);
            personList.remove(person);
            departmentDao.update(department);
        }
    }

    @Transactional
    @Override
    public void closeDepartment(@Nonnull Integer id) {
        // TODO: NotImplemented: удаление всех людей из департамента и установка отметки на департаменте,
        //  что он закрыт для добавления новых людей. Если не найдено, отдавать 404:NotFound
        Department department = Optional.ofNullable(departmentDao.findById(id)).orElseThrow(() ->
                new EntityNotFoundException("Department with id " + id + " not found"));
        List<Person> personList = department.getPeople();
        for(Person person : personList) {
            person.setDepartment(null);
        }
        department.getPeople().clear();
        department.setClosed(true);
        departmentDao.update(department);
    }

    private static DepartmentShortResponse buildDepartmentShortResponse(Department department) {
        return new DepartmentShortResponse().setId(department.getId()).setName(department.getName());
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
