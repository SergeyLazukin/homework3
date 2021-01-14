package ru.digitalhabbits.homework3.util;

import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentResponse;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;
import ru.digitalhabbits.homework3.model.PersonInfo;

import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextInt;

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

    public static DepartmentShortResponse buildDepartmentShortResponse(Integer id) {
        return new DepartmentShortResponse().setId(id).setName("development" + id);
    }

    public static DepartmentResponse buildDepartmentResponse(List<PersonInfo> personList) {
        return new DepartmentResponse().setId(1).setName("Development").setClosed(false).setPersons(personList);
    }
}
