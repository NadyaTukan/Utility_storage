import org.example.model.UsefulMaterial;
import org.example.reader.Reader;
import org.example.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    private Storage storage;
    Resource stateFile = new ClassPathResource("dataTest.json");

    @BeforeEach
    public void setUp() throws IOException {
        File testFile = stateFile.getFile();
        storage = new Storage(testFile.getPath());
        storage.init();
    }

    @Test
    void addInStorage() throws IOException {
        //Arrange
        Map<Long, UsefulMaterial> materialsTest = Reader.read(stateFile.getFile().getPath());

        //Act
        Storage storageExpect = new Storage(stateFile.getFile().getPath());
        storageExpect.init();

        //Assert
        assertEquals(materialsTest, storageExpect.getMaterials());
    }

    @Test
    void searchByIDWithNonZeroResultsTest() {
        //Act
        var resultExistingID = storage.searchByID(1L);

        //Assert
        assertEquals(resultExistingID, storage.getMaterials().get(1L));
    }

    @Test
    void searchByIDWithZeroResultsTest() {
        //Act
        var resultUnExistingID = storage.searchByID(100L);

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
        var resultPartOfNameExisting = storage.searchByPartOfName("Test");

        //Assert
        assertEquals(expectList, resultPartOfNameExisting);
    }

    @Test
    void searchByPartOfNameWithZeroResultsTest() {
        //Act
        var resultPartOfNameUnExisting = storage.searchByPartOfName("book8");

        //Assert
        assertNull(resultPartOfNameUnExisting);
    }
}
