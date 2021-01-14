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
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.model.DepartmentRequest;
import ru.digitalhabbits.homework3.model.DepartmentResponse;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;
import ru.digitalhabbits.homework3.model.PersonInfo;
import ru.digitalhabbits.homework3.service.DepartmentService;

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
import static ru.digitalhabbits.homework3.util.DepartmentHelper.buildDepartment;
import static ru.digitalhabbits.homework3.util.DepartmentHelper.buildDepartmentResponse;
import static ru.digitalhabbits.homework3.util.DepartmentHelper.buildDepartmentShortResponse;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DepartmentController.class)
@AutoConfigureRestDocs
class DepartmentControllerTest {

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new GsonBuilder().create();

    @Test
    void departments() throws Exception {
        // TODO: NotImplemented
        DepartmentShortResponse department1 = buildDepartmentShortResponse(1);
        DepartmentShortResponse department2 = buildDepartmentShortResponse(2);
        List<DepartmentShortResponse> departmentResponseList = List.of(department1, department2);
        when(departmentService.findAllDepartments()).thenReturn(departmentResponseList);
        mockMvc.perform(get("/api/v1/departments")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(departmentResponseList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(departmentResponseList.get(0).getName()))
                .andDo(document("departments",
                        responseFields(
                                fieldWithPath("[].id").description("ID"),
                                fieldWithPath("[].name").description("Name")
                        )));
    }

    @Test
    void department() throws Exception {
        // TODO: NotImplemented
        DepartmentResponse departmentResponse = new DepartmentResponse().setId(1).setName("Development").setClosed(false)
                .setPersons(List.of(new PersonInfo().setId(2).setFullName("Full name")));
        when(departmentService.getDepartment(departmentResponse.getId())).thenReturn(departmentResponse);
        mockMvc.perform(get("/api/v1/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departmentResponse.getId()))
                .andExpect(jsonPath("$.name").value(departmentResponse.getName()))
                .andExpect(jsonPath("$.closed").value(departmentResponse.isClosed()))
                .andExpect(jsonPath("$.persons").isArray())
                .andExpect(jsonPath("$.persons[0].id").value(departmentResponse.getPersons().get(0).getId()))
                .andExpect(jsonPath("$.persons[0].fullName").value(departmentResponse.getPersons().get(0).getFullName()))
            .andDo(document("department",
                    responseFields(
                            fieldWithPath("id").description("ID"),
                            fieldWithPath("name").description("Name"),
                            fieldWithPath("closed").description("Closed"),
                            fieldWithPath("persons[].id").description("ID Person"),
                            fieldWithPath("persons[].fullName").description("Full name person")
                    )));
    }

    @Test
    void createDepartment() throws Exception {
        // TODO: NotImplemented
        DepartmentRequest departmentRequest = new DepartmentRequest().setName("Development");
        Department department = buildDepartment().setId(1);
        when(departmentService.createDepartment(any(DepartmentRequest.class))).thenReturn(department.getId());
        mockMvc.perform(post("/api/v1/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(departmentRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/api/v1/departments/" + department.getId()))
                .andDo(document("createDepartment",
                        requestFields(
                                fieldWithPath("name").description("Name")
                        )));
    }

    @Test
    void updateDepartment() throws Exception {
        // TODO: NotImplemented
        PersonInfo personInfo = new PersonInfo().setId(1).setFullName("Full name");
        DepartmentRequest departmentRequest = new DepartmentRequest().setName("Department");
        DepartmentResponse departmentResponse = buildDepartmentResponse(List.of(personInfo));
        when(departmentService.updateDepartment(1, departmentRequest)).thenReturn(departmentResponse);
        mockMvc.perform(patch("/api/v1/departments/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(departmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departmentResponse.getId()))
                .andExpect(jsonPath("$.name").value(departmentResponse.getName()))
                .andExpect(jsonPath("$.closed").value(departmentResponse.isClosed()))
                .andExpect(jsonPath("$.persons").isArray())
                .andExpect(jsonPath("$.persons[0].id").value(departmentResponse.getPersons().get(0).getId()))
                .andExpect(jsonPath("$.persons[0].fullName").value(departmentResponse.getPersons().get(0).getFullName()))
                .andDo(document("updateDepartment",
                        requestFields(
                                fieldWithPath("name").description("Name")
                        ),
                        responseFields(
                                fieldWithPath("id").description("ID"),
                                fieldWithPath("name").description("Name"),
                                fieldWithPath("closed").description("Closed"),
                                fieldWithPath("persons[].id").description("ID Person"),
                                fieldWithPath("persons[].fullName").description("Full name person")
                        )));
    }

    @Test
    void deleteDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(delete("/api/v1/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).deleteDepartment(1);
    }

    @Test
    void addPersonToDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(post("/api/v1/departments/1/51")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).addPersonToDepartment(1, 51);
    }

    @Test
    void removePersonToDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(delete("/api/v1/departments/1/51")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).removePersonToDepartment(1, 51);
    }

    @Test
    void closeDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(post("/api/v1/departments/1/close")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).closeDepartment(1);
    }
}