import org.example.Reader;
import org.example.Storage;
import org.example.UsefulMaterial;
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
    public void createNewStorage() throws IOException {
        File testFile = stateFile.getFile();
        storage = new Storage(testFile.getPath());
    }

    @Test
    void addInStorage() {
        //Arrange
        Map<Integer, UsefulMaterial> materialsTest = Reader.read("./src/test/resources/dataTest.json");

        //Act
        Storage storageExpect = new Storage("./src/test/resources/dataTest.json");

        //Assert
        assertEquals(materialsTest, storageExpect.getMaterials());
    }

    @Test
    void searchByIDWithNonZeroResultsTest() {
        //Act
        var resultExistingID = storage.searchByID(1);

        //Assert
        assertEquals(resultExistingID, storage.getMaterials().get(1).toString());
    }

    @Test
    void searchByIDWithZeroResultsTest() {
        //Act
        var resultUnExistingID = storage.searchByID(100);

        //Assert
        assertNull(resultUnExistingID);
    }

    @Test
    void searchByPartOfNameWithNonZeroResultsTest() {
        //Arrange
        ArrayList<UsefulMaterial> expectList = new ArrayList<>();
        expectList.add(new UsefulMaterial(1, "Testbook1", "book1", "book1"));

        //Act
        var resultPartOfNameExisting = storage.searchByPartOfName("Testbook1");

        //Assert
        assertEquals(expectList, resultPartOfNameExisting);
    }

    @Test
    void searchByPartOfNameWithZeroResultsTest() {
        //Act
        var resultPartOfNameUnExisting = storage.searchByPartOfName("book8");

        //Assert
        assertTrue(resultPartOfNameUnExisting.isEmpty());
    }
}
