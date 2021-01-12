package ru.digitalhabbits.homework3.util;

import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;

import java.util.List;

public class DepartmentHelper {

    public static Department buildDepartment() {
        return new Department().setClosed(false).setName("Department");
    }

    public static Department buildDepartment(Integer i) {
        return new Department().setClosed(false).setName("development" + i);
    }

    public static Department buildDepartment(Integer i, List<Person> personList) {
        return new Department().setClosed(false).setName("development" + i).setPeople(personList);
    }

    public static Department buildDepartmentWithId(Integer id) {
        return new Department().setId(id).setClosed(false).setName("development" + id);
    }

    public static DepartmentShortResponse buildDepartmentShortResponseFromDepartment(Department department) {
        return new DepartmentShortResponse().setId(department.getId())
                                            .setName(department.getName());
    }
}
