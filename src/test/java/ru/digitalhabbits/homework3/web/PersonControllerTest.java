package ru.digitalhabbits.homework3.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;
import ru.digitalhabbits.homework3.service.PersonService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildCreatePersonRequest;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildNewPerson;
import static ru.digitalhabbits.homework3.util.PersonHelper.buildPersonResponse;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
@AutoConfigureRestDocs
class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new GsonBuilder().create();

    @Test
    void persons() throws Exception {
        // TODO: NotImplemented
        PersonResponse person1 = buildPersonResponse(1);
        PersonResponse person2 = buildPersonResponse(2);
        List<PersonResponse> personList = new ArrayList<>(List.of(person1, person2));
        when(personService.findAllPersons()).thenReturn(personList);
        mockMvc.perform(get("/api/v1/persons")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(personList.get(0).getId()))
                .andExpect(jsonPath("$[0].age").value(personList.get(0).getAge()))
                .andExpect(jsonPath("$[0].fullName").value(personList.get(0).getFullName()))
                .andExpect(jsonPath("$[0].department.id").value(personList.get(0).getDepartment().getId()))
                .andExpect(jsonPath("$[0].department.name").value(personList.get(0).getDepartment().getName()))
        .andDo(document("persons",
                responseFields(
                        fieldWithPath("[].id").description("ID"),
                        fieldWithPath("[].age").description("Age"),
                        fieldWithPath("[].fullName").description("FullName"),
                        fieldWithPath("[].department.id").description("Department ID"),
                        fieldWithPath("[].department.name").description("Department Name")
                )));
    }

    @Test
    void person() throws Exception {
        // TODO: NotImplemented
        PersonResponse person = buildPersonResponse(1);
        when(personService.getPerson(1)).thenReturn(person);
        mockMvc.perform(get("/api/v1/persons/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(person.getId()))
                .andExpect(jsonPath("$.age").value(person.getAge()))
                .andExpect(jsonPath("$.fullName").value(person.getFullName()))
                .andExpect(jsonPath("$.department.id").value(person.getDepartment().getId()))
                .andExpect(jsonPath("$.department.name").value(person.getDepartment().getName()))
                .andDo(document("person",
                        responseFields(
                                fieldWithPath("id").description("ID"),
                                fieldWithPath("age").description("Age"),
                                fieldWithPath("fullName").description("FullName"),
                                fieldWithPath("department.id").description("Department ID"),
                                fieldWithPath("department.name").description("Department Name")
                        )));
    }

    @Test
    void createPerson() throws Exception {
        // TODO: NotImplemented
        PersonRequest personRequest = buildCreatePersonRequest();
        Person personResponse = buildNewPerson(personRequest);
        when(personService.createPerson(any(PersonRequest.class))).thenReturn(personResponse.getId());
        mockMvc.perform(post("/api/v1/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(personRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/api/v1/persons/" + personResponse.getId()))
                .andDo(document("createPerson",
                        requestFields(
                                fieldWithPath("age").description("Age"),
                                fieldWithPath("firstName").description("First Name"),
                                fieldWithPath("middleName").description("Middle Name"),
                                fieldWithPath("lastName").description("Last Name")
                        )));
    }

    @Test
    void updatePerson() throws Exception {
        // TODO: NotImplemented
        PersonRequest personRequest = new PersonRequest().setAge(18)
                .setFirstName("Andrey").setMiddleName("Andreevich").setLastName("Andreev");
        PersonResponse personResponse = buildPersonResponse(1);
        when(personService.updatePerson(1, personRequest)).thenReturn(personResponse);
        mockMvc.perform(patch("/api/v1/persons/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(personRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personResponse.getId()))
                .andExpect(jsonPath("$.age").value(personResponse.getAge()))
                .andExpect(jsonPath("$.fullName").value(personResponse.getFullName()))
                .andExpect(jsonPath("$.department.id").value(personResponse.getDepartment().getId()))
                .andExpect(jsonPath("$.department.name").value(personResponse.getDepartment().getName()))
            .andDo(document("updatePerson",
                    requestFields(
                            fieldWithPath("age").description("Age"),
                            fieldWithPath("firstName").description("First Name"),
                            fieldWithPath("middleName").description("Middle Name"),
                            fieldWithPath("lastName").description("Last Name")
                    ),
                    responseFields(
                            fieldWithPath("id").description("ID"),
                            fieldWithPath("age").description("Age"),
                            fieldWithPath("fullName").description("FullName"),
                            fieldWithPath("department.id").description("Department ID"),
                            fieldWithPath("department.name").description("Department Name")
                    )));
    }

    @Test
    void deletePerson() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(delete("/api/v1/persons/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(personService, times(1)).deletePerson(1);
    }
}