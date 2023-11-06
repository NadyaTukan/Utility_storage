package org.example.api.messages;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.example.api.ErrorDto;
import org.example.api.messages.dto.CreateMaterialDto;
import org.example.api.messages.dto.MaterialDto;
import org.junit.jupiter.api.Test;
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
class StorageControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
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
    void deleteDeleteIsOk(SoftAssertions assertions) {

        //Act
        MaterialDto responce = webTestClient.delete()
                .uri("materials/5")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MaterialDto.class)
                .returnResult()
                .getResponseBody();

        //Assert
        MaterialDto expectedDto = MaterialDto.builder()
                .id(5L)
                .name("book5")
                .description("book5")
                .link("book5")
                .build();

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
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
    void putUpdateByIDIsOk(SoftAssertions assertions) {
        //Arrange
        MaterialDto material = MaterialDto.builder()
                .name("newbook4")
                .description("newbook4")
                .link("newbook4")
                .build();

        CreateMaterialDto dto = new CreateMaterialDto(material.getName(), material.getDescription(), material.getLink());

        //Act
        MaterialDto responce = webTestClient.put()
                .uri("materials/4")
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
                .id(4L)
                .name("newbook4")
                .description("newbook4")
                .link("newbook4")
                .build();

        assertions.assertThat(responce)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedDto);
    }

    @Test
    void putUpdateByIDNotFound(SoftAssertions assertions) {
        //Arrange
        MaterialDto material = MaterialDto.builder()
                .name("newbook100")
                .description("newbook100")
                .link("newbook100")
                .build();

        CreateMaterialDto dto = new CreateMaterialDto(material.getName(), material.getDescription(), material.getLink());

        //Act
        ErrorDto responce = webTestClient.put()
                .uri("materials/100")
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
    void getSearchIsOk(SoftAssertions assertions) {

        //Act
        List<MaterialDto> responce = webTestClient.get()
                .uri("materials/search/Testbook")
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
    void getSearchNotFound(SoftAssertions assertions) {

        //Act
        List<ErrorDto> responce = webTestClient.get()
                .uri("materials/search/Testbook100")
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
