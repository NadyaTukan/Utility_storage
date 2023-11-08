package org.example.api.messages;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.example.api.ErrorDto;
import org.example.api.messages.dto.CreateMaterialDto;
import org.example.api.messages.dto.MaterialDto;
import org.example.api.messages.dto.UpdateMaterialDto;
import org.example.api.messages.mapper.MaterialMapper;
import org.example.model.UsefulMaterial;
import org.example.storage.Storage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StorageControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private Storage storage;

    @Autowired
    private MaterialMapper mapper;

    @Test
    @Order(1)
    void postCreate(SoftAssertions assertions) {
        //Arrange
        MaterialDto material = MaterialDto.builder()
                .name("book6")
                .description("book6")
                .link("book6")
                .build();

        CreateMaterialDto dto = new CreateMaterialDto(material.getName(), material.getDescription(), material.getLink());

        //Act
        MaterialDto responce = webTestClient.post()
                .uri("materials/create")
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MaterialDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        MaterialDto expectedDto = MaterialDto.builder()
                .id(6L)
                .name("book6")
                .description("book6")
                .link("book6")
                .build();

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    @Order(2)
    void deleteDeleteIsOk(SoftAssertions assertions) {

        //Act
        webTestClient.delete()
                .uri("materials/6")
                .exchange()
                .expectStatus()
                .isOk();

        //Assert
        assertions.assertThat(storage.searchByID(6L)).isEqualTo(null);
    }

    @Test
    @Order(3)
    void deleteDeleteNotFound(SoftAssertions assertions) {

        //Act
        ErrorDto responce = webTestClient.delete()
                .uri("materials/100")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        ErrorDto expectedDto = ErrorDto.builder()
                .errorMessage("Материал не найден.")
                .build();

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    @Order(4)
    void postUpdateByIDIsOk(SoftAssertions assertions) {
        //Arrange
        MaterialDto material = MaterialDto.builder()
                .name("newbook4")
                .description("newbook4")
                .link("newbook4")
                .build();

        CreateMaterialDto dto = new CreateMaterialDto(material.getName(),
                                                    material.getDescription(),
                                                    material.getLink());

        //Act
        webTestClient.post()
                .uri("materials/4/update")
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus()
                .isOk();

        //Assert
        MaterialDto expectedDto = MaterialDto.builder()
                .id(4L)
                .name("newbook4")
                .description("newbook4")
                .link("newbook4")
                .build();

        MaterialDto postedMaterial = mapper.toDto(storage.searchByID(4L));

        assertions.assertThat(postedMaterial)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    @Order(5)
    void postUpdateByIDNotFound(SoftAssertions assertions) {
        //Arrange
        MaterialDto material = MaterialDto.builder()
                .name("newbook100")
                .description("newbook100")
                .link("newbook100")
                .build();

        CreateMaterialDto dto = new CreateMaterialDto(material.getName(), material.getDescription(), material.getLink());

        //Act
        ErrorDto responce = webTestClient.post()
                .uri("materials/100/update")
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        ErrorDto expectedDto = ErrorDto.builder()
                .errorMessage("Материал не найден.")
                .build();

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    @Order(6)
    void getByIDIsOk(SoftAssertions assertions) {

        //Act
        MaterialDto responce = webTestClient.get()
                .uri("materials/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MaterialDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        MaterialDto expectedDto = MaterialDto.builder()
                .id(1L)
                .name("Testbook1")
                .description("book1")
                .link("book1")
                .build();

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    @Order(7)
    void getByIDNotFound(SoftAssertions assertions) {

        //Act
        ErrorDto responce = webTestClient.get()
                .uri("materials/100")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        ErrorDto expectedDto = ErrorDto.builder()
                .errorMessage("Материал не найден.")
                .build();

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    @Order(8)
    void getSearchIsOk(SoftAssertions assertions) {

        //Act
        List<MaterialDto> responce = webTestClient.get()
                .uri("materials/search/?partOfName=Testbook")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MaterialDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        List<MaterialDto> expectedDto = new ArrayList<>();
        expectedDto.add(MaterialDto.builder()
                .id(1L)
                .name("Testbook1")
                .description("book1")
                .link("book1")
                .build());
        expectedDto.add(MaterialDto.builder()
                .id(2L)
                .name("Testbook2")
                .description("book2")
                .link("book2")
                .build());
        expectedDto.add(MaterialDto.builder()
                .id(3L)
                .name("Testbook3")
                .description("book3")
                .link("book3")
                .build());

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    @Order(9)
    void getSearchNotFound(SoftAssertions assertions) {

        //Act
        List<ErrorDto> responce = webTestClient.get()
                .uri("materials/search/?partOfName=Testbook100")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBodyList(ErrorDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        List<ErrorDto> expectedDto = new ArrayList<>();
        expectedDto.add(ErrorDto.builder()
                .errorMessage("Материал не найден.")
                .build());


        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }


}
