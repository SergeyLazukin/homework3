package ru.digitalhabbits.homework3.util;

import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentInfo;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class PersonHelper {

    public static PersonRequest buildCreatePersonRequest() {
        return new PersonRequest()
                .setFirstName(randomAlphabetic(8))
                .setMiddleName(randomAlphabetic(8))
                .setLastName(randomAlphabetic(8))
                .setAge(nextInt(10, 50));
    }

    public static Person buildPerson() {
        return new Person()
                .setId(nextInt())
                .setAge(nextInt(10, 50))
                .setFirstName(randomAlphabetic(8))
                .setMiddleName(randomAlphabetic(8))
                .setLastName(randomAlphabetic(8))
                .setDepartment(new Department()
                        .setId(2)
                        .setName(randomAlphabetic(8)));
    }

    public static Person buildPersonWithoutId() {
        return new Person()
                .setAge(nextInt(10, 50))
                .setFirstName(randomAlphabetic(8))
                .setMiddleName(randomAlphabetic(8))
                .setLastName(randomAlphabetic(8));
    }

    public static Person buildPersonWithoutId(Department department) {
        return new Person()
                .setAge(nextInt(10, 50))
                .setFirstName(randomAlphabetic(8))
                .setMiddleName(randomAlphabetic(8))
                .setLastName(randomAlphabetic(8))
                .setDepartment(department);
    }

    public static Person buildNewPerson(PersonRequest request) {
        return new Person()
                .setId(nextInt())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge())
                .setDepartment(null);
    }

    public static Person buildPersonFromPersonRequest(PersonRequest request) {
        return new Person()
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge())
                .setDepartment(null);
    }

    public static String buildFullName(Person person) {
        String fullName = person.getFirstName();
        if(person.getMiddleName() != null) {
            fullName += " " + person.getMiddleName();
        }
        fullName += " " + person.getLastName();
        return fullName;
    }

    public static String buildFullName(String firstName, String middleName, String lastName) {
        String fullName = firstName;
        if(middleName != null) {
            fullName += " " + middleName;
        }
        fullName += " " + lastName;
        return fullName;
    }

    public static PersonResponse buildPersonResponse(Integer id) {
        return new PersonResponse().setId(id).setAge(20).setFullName("Name")
                .setDepartment(new DepartmentInfo().setId(1).setName("Development"));
    }
}
