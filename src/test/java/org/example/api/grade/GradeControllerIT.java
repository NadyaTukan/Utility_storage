package org.example.api.grade;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.example.api.ErrorDto;
import org.example.api.grade.dto.CreateGradeDto;
import org.example.api.grade.dto.GradeDto;
import org.example.model.Grade;
import org.example.repository.GradeRepository;
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
public class GradeControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GradeRepository gradeRepository;

    @BeforeEach
    void beforeEach() {
        gradeRepository.clear();

    }

    @Test
    void postCreate(SoftAssertions assertions) {
        //Arrange
        GradeDto gradeDto = GradeDto.builder()
                                    .usefulMaterialId(1L)
                                    .grade(3)
                                    .comment("not comment")
                                    .build();

        CreateGradeDto createGradeDto = new CreateGradeDto(gradeDto.getUsefulMaterialId(),
                gradeDto.getGrade(),
                gradeDto.getComment());

        //Act
        GradeDto response = webTestClient.post()
                                         .uri("grades/create")
                                         .contentType(APPLICATION_JSON)
                                         .bodyValue(createGradeDto)
                                         .exchange()
                                         .expectStatus()
                                         .isOk()
                                         .expectBody(GradeDto.class)
                                         .returnResult()
                                         .getResponseBody();

        //Assert
        GradeDto expectedDto = GradeDto.builder()
                                       .id(1L)
                                       .usefulMaterialId(1L)
                                       .grade(3)
                                       .comment("not comment")
                                       .build();

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }


    @Test
    void deleteDeleteIsOk(SoftAssertions assertions) {
        //Arrange
        Grade grade = gradeRepository.create(Grade.builder()
                                                  .usefulMaterialId(1L)
                                                  .grade(3)
                                                  .comment("gradeForDeleteDelete")
                                                  .build());
        //Act
        webTestClient.delete()
                     .uri("grades/1")
                     .exchange()
                     .expectStatus()
                     .isOk();

        //Assert
        assertions.assertThat(gradeRepository.searchByUsefulMaterialId(2L)).isEqualTo(null);
    }

    @Test
    void deleteDeleteNotFound(SoftAssertions assertions) {

        //Act
        ErrorDto response = webTestClient.delete()
                                         .uri("grades/100")
                                         .exchange()
                                         .expectStatus()
                                         .isNotFound()
                                         .expectBody(ErrorDto.class)
                                         .returnResult()
                                         .getResponseBody();

        //Assert
        ErrorDto expectedDto = ErrorDto.builder()
                                       .errorMessage("Оценка не найдена.")
                                       .build();

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void getSearchByUsefulMaterialIdIsOk(SoftAssertions assertions) {
        //Arrange
        gradeRepository.create(Grade.builder()
                                    .usefulMaterialId(2L)
                                    .grade(1)
                                    .comment("gradeForgetSearchByUsefulMaterialId1")
                                    .build());
        gradeRepository.create(Grade.builder()
                                    .usefulMaterialId(2L)
                                    .grade(1)
                                    .comment("gradeForgetSearchByUsefulMaterialId2")
                                    .build());
        gradeRepository.create(Grade.builder()
                                    .usefulMaterialId(2L)
                                    .grade(1)
                                    .comment("gradeForgetSearchByUsefulMaterialId3")
                                    .build());

        //Act
        List<GradeDto> response = webTestClient.get()
                                               .uri("grades/search/?UsefulMaterialId=2")
                                               .exchange()
                                               .expectStatus()
                                               .isOk()
                                               .expectBodyList(GradeDto.class)
                                               .returnResult()
                                               .getResponseBody();

        //Assert
        List<GradeDto> expectedDto = new ArrayList<>();
        expectedDto.add(GradeDto.builder()
                                .id(1L)
                                .usefulMaterialId(2L)
                                .grade(1)
                                .comment("gradeForgetSearchByUsefulMaterialId1")
                                .build());
        expectedDto.add(GradeDto.builder()
                                .id(2L)
                                .usefulMaterialId(2L)
                                .grade(1)
                                .comment("gradeForgetSearchByUsefulMaterialId2")
                                .build());
        expectedDto.add(GradeDto.builder()
                                .id(3L)
                                .usefulMaterialId(2L)
                                .grade(1)
                                .comment("gradeForgetSearchByUsefulMaterialId3")
                                .build());

        assertions.assertThat(response)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedDto);
    }

    @Test
    void getSearchByUsefulMaterialIdNotFound(SoftAssertions assertions) {

        //Act
        List<ErrorDto> response = webTestClient.get()
                                               .uri("grades/search/?UsefulMaterialId=100")
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
