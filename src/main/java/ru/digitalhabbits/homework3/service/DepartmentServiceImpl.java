package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import ru.digitalhabbits.homework3.dao.DepartmentDao;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentInfo;
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

    @Nonnull
    @Override
    public List<DepartmentShortResponse> findAllDepartments() {
        // TODO: NotImplemented: получение краткой информации о всех департаментах
        List<Department> departments = departmentDao.findAll();
        return departments.stream()
                .map(DepartmentServiceImpl::buildDepartmentShortResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
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
    @Override
    public Integer createDepartment(@Nonnull DepartmentRequest request) {
        // TODO: NotImplemented: создание нового департамента
        Department departmentNew = new Department().setName(request.getName());
        return departmentDao.create(departmentNew).getId();
    }

    @Nonnull
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

    @Override
    public void deleteDepartment(@Nonnull Integer id) {
        // TODO: NotImplemented: удаление всех людей из департамента и удаление самого департамента.
        //  Если не найдено, то ничего не делать
        throw new NotImplementedException();
    }

    @Override
    public void addPersonToDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {
        // TODO: NotImplemented: добавление нового человека в департамент.
        //  Если не найден человек или департамент, отдавать 404:NotFound.
        //  Если департамент закрыт, то отдавать 409:Conflict
        throw new NotImplementedException();
    }

    @Override
    public void removePersonToDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {
        // TODO: NotImplemented: удаление человека из департамента.
        //  Если департамент не найден, отдавать 404:NotFound, если не найден человек в департаменте, то ничего не делать
        throw new NotImplementedException();
    }

    @Override
    public void closeDepartment(@Nonnull Integer id) {
        // TODO: NotImplemented: удаление всех людей из департамента и установка отметки на департаменте,
        //  что он закрыт для добавления новых людей. Если не найдено, отдавать 404:NotFound
        throw new NotImplementedException();
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
