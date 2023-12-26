package org.example.api.usefulMaterial;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.example.api.ErrorDto;
import org.example.api.usefulMaterial.dto.CreateUsefulMaterialDto;
import org.example.api.usefulMaterial.dto.UpdateUsefulMaterialDto;
import org.example.api.usefulMaterial.dto.UsefulMaterialDto;
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
class UsefulMaterialControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UsefulMaterialRepository usefulMaterialRepository;

    @BeforeEach
    void beforeEach() {
        usefulMaterialRepository.clear();

    }

    @Test
    void postCreate(SoftAssertions assertions) {
        //Arrange
        UsefulMaterialDto usefulMaterialDto = UsefulMaterialDto.builder()
                                                               .name("bookForPostCreate")
                                                               .description("book")
                                                               .link("book")
                                                               .build();

        CreateUsefulMaterialDto createUsefulMaterialDto = new CreateUsefulMaterialDto(usefulMaterialDto.getName(),
                usefulMaterialDto.getDescription(),
                usefulMaterialDto.getLink());

        //Act
        UsefulMaterialDto response = webTestClient.post()
                                                  .uri("materials/create")
                                                  .contentType(APPLICATION_JSON)
                                                  .bodyValue(createUsefulMaterialDto)
                                                  .exchange()
                                                  .expectStatus()
                                                  .isOk()
                                                  .expectBody(UsefulMaterialDto.class)
                                                  .returnResult()
                                                  .getResponseBody();

        //Assert
        UsefulMaterialDto expectedDto = UsefulMaterialDto.builder()
                                                         .id(1L)
                                                         .name("bookForPostCreate")
                                                         .description("book")
                                                         .link("book")
                                                         .build();

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void deleteDeleteIsOk(SoftAssertions assertions) {
        //Arrange
        usefulMaterialRepository.create(UsefulMaterial.builder()
                                                      .name("bookForDeleteDelete")
                                                      .description("book")
                                                      .link("book")
                                                      .build());
        //Act
        webTestClient.delete()
                     .uri("materials/1")
                     .exchange()
                     .expectStatus()
                     .isOk();

        //Assert
        assertions.assertThat(usefulMaterialRepository.searchByID(1L)).isEqualTo(null);
    }

    @Test
    void deleteDeleteNotFound(SoftAssertions assertions) {

        //Act
        ErrorDto response = webTestClient.delete()
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

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void postUpdateByIDIsOk(SoftAssertions assertions) {
        //Arrange
        UsefulMaterial usefulMaterial = usefulMaterialRepository.create(UsefulMaterial.builder()
                                                                                      .name("bookForPostUpdateByID")
                                                                                      .description("book")
                                                                                      .link("book")
                                                                                      .build());

        UpdateUsefulMaterialDto createUsefulMaterialDto = new UpdateUsefulMaterialDto(usefulMaterial.getName(),
                usefulMaterial.getDescription(),
                usefulMaterial.getLink());

        //Act
        UsefulMaterialDto response = webTestClient.post()
                                                  .uri("materials/1/update")
                                                  .contentType(APPLICATION_JSON)
                                                  .bodyValue(createUsefulMaterialDto)
                                                  .exchange()
                                                  .expectStatus()
                                                  .isOk()
                                                  .expectBody(UsefulMaterialDto.class)
                                                  .returnResult()
                                                  .getResponseBody();

        //Assert
        UsefulMaterialDto expectedDto = UsefulMaterialDto.builder()
                                                         .id(1L)
                                                         .name("bookForPostUpdateByID")
                                                         .description("book")
                                                         .link("book")
                                                         .build();

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void postUpdateByIDNotFound(SoftAssertions assertions) {
        //Arrange
        UsefulMaterialDto usefulMaterialDto = UsefulMaterialDto.builder()
                                                               .name("newbook100")
                                                               .description("newbook100")
                                                               .link("newbook100")
                                                               .build();

        UpdateUsefulMaterialDto createUsefulMaterialDto = new UpdateUsefulMaterialDto(usefulMaterialDto.getName(),
                usefulMaterialDto.getDescription(),
                usefulMaterialDto.getLink());

        //Act
        ErrorDto response = webTestClient.post()
                                         .uri("materials/100/update")
                                         .contentType(APPLICATION_JSON)
                                         .bodyValue(createUsefulMaterialDto)
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

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void getByIDIsOk(SoftAssertions assertions) {
        //Arrange
        usefulMaterialRepository.create(UsefulMaterial.builder()
                                                      .name("bookForGetByID")
                                                      .description("book")
                                                      .link("book")
                                                      .build());


        //Act
        UsefulMaterialDto response = webTestClient.get()
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
                                                         .name("bookForGetByID")
                                                         .description("book")
                                                         .link("book")
                                                         .build();

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void getByIDNotFound(SoftAssertions assertions) {

        //Act
        ErrorDto response = webTestClient.get()
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

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void getSearchIsOk(SoftAssertions assertions) {
        //Arrange
        usefulMaterialRepository.create(UsefulMaterial.builder()
                                                      .name("bookForGetSearch1")
                                                      .description("book")
                                                      .link("book")
                                                      .build());

        usefulMaterialRepository.create(UsefulMaterial.builder()
                                                      .name("bookForGetSearch2")
                                                      .description("book")
                                                      .link("book")
                                                      .build());

        usefulMaterialRepository.create(UsefulMaterial.builder()
                                                      .name("bookForGetSearch3")
                                                      .description("book")
                                                      .link("book")
                                                      .build());


        //Act
        List<UsefulMaterialDto> response = webTestClient.get()
                                                        .uri("materials/search/?partOfName=bookForGetSearch")
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
                                         .name("bookForGetSearch1")
                                         .description("book")
                                         .link("book")
                                         .build());
        expectedDto.add(UsefulMaterialDto.builder()
                                         .id(2L)
                                         .name("bookForGetSearch2")
                                         .description("book")
                                         .link("book")
                                         .build());
        expectedDto.add(UsefulMaterialDto.builder()
                                         .id(3L)
                                         .name("bookForGetSearch3")
                                         .description("book")
                                         .link("book")
                                         .build());

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void getSearchNotFound(SoftAssertions assertions) {

        //Act
        List<ErrorDto> response = webTestClient.get()
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


        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }


}
