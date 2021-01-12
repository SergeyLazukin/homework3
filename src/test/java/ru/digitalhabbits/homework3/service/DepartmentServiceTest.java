package ru.digitalhabbits.homework3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.homework3.dao.DepartmentDao;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentRequest;
import ru.digitalhabbits.homework3.model.DepartmentResponse;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;
import ru.digitalhabbits.homework3.util.DepartmentHelper;
import ru.digitalhabbits.homework3.util.PersonHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DepartmentServiceImpl.class)
class DepartmentServiceTest {

    @MockBean
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentService departmentService;

    private static Integer COUNT = 2;
    private List<Department> departments;

    @BeforeEach
    void generateDepartments() {
        departments = IntStream.range(0, COUNT).mapToObj(DepartmentHelper::buildDepartmentWithId)
                .collect(Collectors.toList());
    }

    @Test
    void findAllDepartments() {
        // TODO: NotImplemented
        when(departmentDao.findAll()).thenReturn(departments);

        List<DepartmentShortResponse> departmentsFound = departmentService.findAllDepartments();
        assertEquals(departmentsFound.size(), COUNT);
    }

    @Test
    void getDepartment() {
        // TODO: NotImplemented
        Department department = departments.get(0);
        when(departmentDao.findById(any(Integer.class))).thenReturn(department);

        DepartmentResponse departmentResponse = departmentService.getDepartment(1);
        assertNotNull(departmentResponse);
        assertEquals(departmentResponse.getId(), department.getId());
        assertEquals(departmentResponse.getName(), department.getName());
        assertThat(departmentResponse.getPersons()).isEqualTo(department.getPeople());
    }

    @Test
    void createDepartment() {
        // TODO: NotImplemented
        when(departmentDao.create(any(Department.class))).thenReturn(departments.get(0));

        Integer id = departmentService.createDepartment(new DepartmentRequest().setName("qwerty"));
        assertNotNull(id);
    }

    @Test
    void updateDepartment() {
        // TODO: NotImplemented
        DepartmentRequest departmentRequest = new DepartmentRequest().setName("qwerty");
        Department department = departments.get(0);
        Integer id = department.getId();
        String nameOld = department.getName();
        Department newDepartment = new Department()
                .setId(department.getId())
                .setClosed(department.isClosed())
                .setName(departmentRequest.getName())
                .setPeople(department.getPeople());
        when(departmentDao.findById(any(Integer.class))).thenReturn(department);
        when(departmentDao.update(any(Department.class))).thenReturn(newDepartment);

        DepartmentResponse departmentResponse = departmentService.updateDepartment(department.getId(), departmentRequest);
        assertEquals(departmentResponse.getId(), id);
        assertEquals(departmentResponse.getName(), departmentRequest.getName());
        assertNotEquals(departmentResponse.getName(), nameOld);

    }

    @Test
    void deleteDepartment() {
        // TODO: NotImplemented
        Department department = departments.get(0);
        List<Person> personList = IntStream.range(0,2)
                .mapToObj(i -> PersonHelper.buildPersonWithoutId(department).setId(i))
                .collect(Collectors.toList());

        for(Person person : personList) {
            person.setDepartment(department);
        }
        department.setPeople(personList);

        Integer id = department.getId();

        when(departmentDao.findById(any(Integer.class))).thenReturn(department);
//        when(departmentDao.delete(any(Integer.class))).thenReturn(department);

        departmentService.deleteDepartment(department.getId());
        verify(departmentDao, times(1)).findById(id);
        verify(departmentDao, times(1)).update(department);
        verify(departmentDao, times(1)).delete(id);
    }

    @Test
    void addPersonToDepartment() {
        // TODO: NotImplemented
    }

    @Test
    void removePersonToDepartment() {
        // TODO: NotImplemented
    }

    @Test
    void closeDepartment() {
        // TODO: NotImplemented
    }
}