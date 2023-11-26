package org.example;

import org.example.action.usefulMaterial.FillStorageUsefulMaterialsRunner;
import org.example.model.UsefulMaterial;
import org.example.action.reader.Reader;
import org.example.repository.UsefulMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UsefulMaterialRepositoryTest {

    private UsefulMaterialRepository usefulMaterialRepository;
    private FillStorageUsefulMaterialsRunner fillStorageUsefulMaterialsRunner;
    Resource stateFile = new ClassPathResource("dataTest.json");

    @BeforeEach
    public void setUp() throws IOException {
        File testFile = stateFile.getFile();
        usefulMaterialRepository = new UsefulMaterialRepository();
        usefulMaterialRepository.putAllInUsefulMaterials(Reader.read(stateFile.getFile().getPath()));
    }

    @Test
    void addInUsefulMaterialRepository() throws IOException {
        //Arrange
        Map<Long, UsefulMaterial> materialsTest = Reader.read(stateFile.getFile().getPath());

        //Act
        UsefulMaterialRepository usefulMaterialRepositoryExpect = new UsefulMaterialRepository();
        usefulMaterialRepositoryExpect.putAllInUsefulMaterials(Reader.read(stateFile.getFile().getPath()));

        //Assert
        assertEquals(materialsTest, usefulMaterialRepositoryExpect.getUsefulMaterials());
    }

    @Test
    void searchByIDWithNonZeroResultsTest() {
        //Act
        var resultExistingID = usefulMaterialRepository.searchByID(1L);

        //Assert
        assertEquals(resultExistingID, usefulMaterialRepository.getUsefulMaterials().get(1L));
    }

    @Test
    void searchByIDWithZeroResultsTest() {
        //Act
        var resultUnExistingID = usefulMaterialRepository.searchByID(100L);

        //Assert
        assertNull(resultUnExistingID);
    }

    @Test
    void searchByPartOfNameWithNonZeroResultsTest() {
        //Arrange
        ArrayList<UsefulMaterial> expectList = new ArrayList<>();
        expectList.add(UsefulMaterial.builder()
                                     .id(1L)
                                     .name("Testbook1")
                                     .description("book1")
                                     .link("book1")
                                     .build());
        expectList.add(UsefulMaterial.builder()
                                     .id(2L)
                                     .name("Testbook2")
                                     .description("book2")
                                     .link("book2")
                                     .build());
        expectList.add(UsefulMaterial.builder()
                                     .id(3L)
                                     .name("Testbook3")
                                     .description("book3")
                                     .link("book3")
                                     .build());

        //Act
        var resultPartOfNameExisting = usefulMaterialRepository.searchByPartOfName("Test");

        //Assert
        assertEquals(expectList, resultPartOfNameExisting);
    }

    @Test
    void searchByPartOfNameWithZeroResultsTest() {
        //Act
        var resultPartOfNameUnExisting = usefulMaterialRepository.searchByPartOfName("book8");

        //Assert
        assertNull(resultPartOfNameUnExisting);
    }
}
