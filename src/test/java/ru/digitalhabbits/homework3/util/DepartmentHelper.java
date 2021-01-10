package ru.digitalhabbits.homework3.util;

import ru.digitalhabbits.homework3.domain.Department;

import java.util.ArrayList;

public class DepartmentHelper {

    public static Department buildDepartment() {
        return new Department()
                .setName("Department")
                .setClosed(false)
                .setPeople(new ArrayList<>());
    }
}
