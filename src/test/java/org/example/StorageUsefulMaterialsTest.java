package org.example;

import org.example.model.UsefulMaterial;
import org.example.action.reader.Reader;
import org.example.repository.StorageUsefulMaterials;
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

public class StorageUsefulMaterialsTest {

    private StorageUsefulMaterials storageUsefulMaterials;
    Resource stateFile = new ClassPathResource("dataTest.json");

    @BeforeEach
    public void setUp() throws IOException {
        File testFile = stateFile.getFile();
        storageUsefulMaterials = new StorageUsefulMaterials(testFile.getPath());
        storageUsefulMaterials.init();
    }

    @Test
    void addInStorage() throws IOException {
        //Arrange
        Map<Long, UsefulMaterial> materialsTest = Reader.read(stateFile.getFile().getPath());

        //Act
        StorageUsefulMaterials storageUsefulMaterialsExpect = new StorageUsefulMaterials(stateFile.getFile().getPath());
        storageUsefulMaterialsExpect.init();

        //Assert
        assertEquals(materialsTest, storageUsefulMaterialsExpect.getMaterials());
    }

    @Test
    void searchByIDWithNonZeroResultsTest() {
        //Act
        var resultExistingID = storageUsefulMaterials.searchByID(1L);

        //Assert
        assertEquals(resultExistingID, storageUsefulMaterials.getMaterials().get(1L));
    }

    @Test
    void searchByIDWithZeroResultsTest() {
        //Act
        var resultUnExistingID = storageUsefulMaterials.searchByID(100L);

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
        var resultPartOfNameExisting = storageUsefulMaterials.searchByPartOfName("Test");

        //Assert
        assertEquals(expectList, resultPartOfNameExisting);
    }

    @Test
    void searchByPartOfNameWithZeroResultsTest() {
        //Act
        var resultPartOfNameUnExisting = storageUsefulMaterials.searchByPartOfName("book8");

        //Assert
        assertNull(resultPartOfNameUnExisting);
    }
}
