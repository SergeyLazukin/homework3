package ru.digitalhabbits.homework3.dao;

import org.junit.jupiter.api.BeforeEach;
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
import ru.digitalhabbits.homework3.util.DepartmentHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.digitalhabbits.homework3.util.DepartmentHelper.buildDepartment;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DepartmentDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentDao departmentDao;

    private static final Integer COUNT = 2;
    private static List<Department> departments;

    @BeforeEach
    void persistDepartments() {
        departments = IntStream.range(0, COUNT).mapToObj(DepartmentHelper::buildDepartment)
                .map(item -> entityManager.persist(item))
                .collect(Collectors.toList());
    }

    @Test
    void create() {
        Department departmentCreate = departmentDao.create(buildDepartment());
        assertNotNull(departmentCreate.getId());
    }

    @Test
    void findById() {
        // TODO: NotImplemented
        Integer id = departments.get(0).getId();
        Department departmentFound = departmentDao.findById(id);
        assertThat(departmentFound).isEqualToComparingFieldByField(departments.get(0));
    }

    @Test
    void findAll() {
        // TODO: NotImplemented
        List<Department> departmentsFound = departmentDao.findAll();
        assertEquals(departmentsFound.size(), COUNT);
        assertThat(departmentsFound).isEqualTo(departments);
    }

    @Test
    void update() {
        // TODO: NotImplemented
        Department department = departments.get(0);
        Integer id = department.getId();
        String nameOld = department.getName();
        department.setName("Production");
        Department departmentUpdate = departmentDao.update(department);
        assertEquals(departmentUpdate.getId(), id);
        assertEquals(departmentUpdate.getName(), "Production");
        assertNotEquals(departmentUpdate.getName(), nameOld);
    }

    @Test
    void delete() {
        // TODO: NotImplemented
    }
}