package org.example.api.usefulMaterial;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.example.api.ErrorDto;
import org.example.api.usefulMaterial.dto.CreateUsefulMaterialDto;
import org.example.api.usefulMaterial.dto.UsefulMaterialDto;
import org.example.api.usefulMaterial.mapper.UsefulMaterialMapper;
import org.example.model.UsefulMaterial;
import org.example.repository.UsefulMaterialRepository;
import org.junit.jupiter.api.*;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsefulMaterialRepositoryControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UsefulMaterialRepository usefulMaterialRepository;

    @Autowired
    private UsefulMaterialMapper mapper;

    UsefulMaterial usefulMaterialForPostCreate;
    UsefulMaterial usefulMaterialForDeleteDelete;
    UsefulMaterial usefulMaterialForPostUpdateByID;
    UsefulMaterial usefulMaterialForGetByID;


    @BeforeAll
    void init() {
        usefulMaterialForPostCreate = UsefulMaterial.builder()
                                                    .name("bookForPostCreate")
                                                    .description("book")
                                                    .link("book")
                                                    .build();

        usefulMaterialForDeleteDelete = usefulMaterialRepository.create(UsefulMaterial.builder()
                                                                                      .name("bookForDeleteDelete")
                                                                                      .description("book")
                                                                                      .link("book")
                                                                                      .build());

        usefulMaterialForPostUpdateByID = usefulMaterialRepository.create(UsefulMaterial.builder()
                                                                                        .name("bookForPostUpdateByID")
                                                                                        .description("book")
                                                                                        .link("book")
                                                                                        .build());

    }

    @Test
    void postCreate(SoftAssertions assertions) {
        //Arrange
        UsefulMaterialDto material = mapper.toDto(usefulMaterialForPostCreate);

        CreateUsefulMaterialDto dto = new CreateUsefulMaterialDto(material.getName(),
                material.getDescription(),
                material.getLink());

        //Act
        UsefulMaterialDto responce = webTestClient.post()
                                                  .uri("materials/create")
                                                  .contentType(APPLICATION_JSON)
                                                  .bodyValue(dto)
                                                  .exchange()
                                                  .expectStatus()
                                                  .isOk()
                                                  .expectBody(UsefulMaterialDto.class)
                                                  .returnResult()
                                                  .getResponseBody();

        //Assert
        UsefulMaterialDto expectedDto = UsefulMaterialDto.builder()
                                                         .id(8L)
                                                         .name("bookForPostCreate")
                                                         .description("book")
                                                         .link("book")
                                                         .build();

        assertions.assertThat(responce)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void deleteDeleteIsOk(SoftAssertions assertions) {

        //Act
        webTestClient.delete()
                     .uri("materials/6")
                     .exchange()
                     .expectStatus()
                     .isOk();

        //Assert
        assertions.assertThat(usefulMaterialRepository.searchByID(6L)).isEqualTo(null);
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
    void postUpdateByIDIsOk(SoftAssertions assertions) {
        //Arrange
        UsefulMaterialDto material = mapper.toDto(usefulMaterialForPostUpdateByID);

        CreateUsefulMaterialDto dto = new CreateUsefulMaterialDto(material.getName(),
                material.getDescription(),
                material.getLink());

        //Act
        UsefulMaterialDto responce = webTestClient.post()
                                                  .uri("materials/7/update")
                                                  .contentType(APPLICATION_JSON)
                                                  .bodyValue(dto)
                                                  .exchange()
                                                  .expectStatus()
                                                  .isOk()
                                                  .expectBody(UsefulMaterialDto.class)
                                                  .returnResult()
                                                  .getResponseBody();

        //Assert
        UsefulMaterialDto expectedDto = UsefulMaterialDto.builder()
                                                         .id(7L)
                                                         .name("bookForPostUpdateByID")
                                                         .description("book")
                                                         .link("book")
                                                         .build();

        assertions.assertThat(responce)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void postUpdateByIDNotFound(SoftAssertions assertions) {
        //Arrange
        UsefulMaterialDto material = UsefulMaterialDto.builder()
                                                      .name("newbook100")
                                                      .description("newbook100")
                                                      .link("newbook100")
                                                      .build();

        CreateUsefulMaterialDto dto = new CreateUsefulMaterialDto(material.getName(), material.getDescription(),
                material.getLink());

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
    void getByIDIsOk(SoftAssertions assertions) {

        //Act
        UsefulMaterialDto responce = webTestClient.get()
                                                  .uri("materials/1")
                                                  .exchange()
                                                  .expectStatus()
                                                  .isOk()
                                                  .expectBody(UsefulMaterialDto.class)
                                                  .returnResult()
                                                  .getResponseBody();

        //Assert
        UsefulMaterialDto expectedDto = UsefulMaterialDto.builder()
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
        List<UsefulMaterialDto> responce = webTestClient.get()
                                                        .uri("materials/search/?partOfName=Testbook")
                                                        .exchange()
                                                        .expectStatus()
                                                        .isOk()
                                                        .expectBodyList(UsefulMaterialDto.class)
                                                        .returnResult()
                                                        .getResponseBody();

        //Assert
        List<UsefulMaterialDto> expectedDto = new ArrayList<>();
        expectedDto.add(UsefulMaterialDto.builder()
                                         .id(1L)
                                         .name("Testbook1")
                                         .description("book1")
                                         .link("book1")
                                         .build());
        expectedDto.add(UsefulMaterialDto.builder()
                                         .id(2L)
                                         .name("Testbook2")
                                         .description("book2")
                                         .link("book2")
                                         .build());
        expectedDto.add(UsefulMaterialDto.builder()
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
