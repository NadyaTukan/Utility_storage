import org.example.Reader;
import org.example.Storage;
import org.example.UsefulMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    private Storage storageTest;

    @BeforeEach
    public void createNewStorage() {
       storageTest = new Storage("./src/test/java/dataTest.json");
    }

    @Test
    void addInStorage() {
        //Act
        Map<Integer, UsefulMaterial> materialsTest = Reader.read("./src/test/java/dataTest.json");
        Storage storageExpect = new Storage(materialsTest);

        //Assert
        assertEquals(materialsTest, storageExpect.getMaterials());
    }

    @Test
    void searchByIDTest() {
        //Act
        var resultExistingID  =  storageTest.searchByID(1);
        var resultUnExistingID = storageTest.searchByID(100);

        //Assert
        assertEquals(resultExistingID, storageTest.getMaterials().get(1).getInfo());
        assertNull(resultUnExistingID);
    }

    @Test
    void searchByPartOfNameWithNonZeroResultsTest() {
        //Arrange
        ArrayList<UsefulMaterial> expectList = new ArrayList<>();
        expectList.add(new UsefulMaterial(1, "book1", "book1", "book1"));

        //Act
        var resultPartOfNameExisting  =  storageTest.searchByPartOfName("book1");

        //Assert
        assertEquals(expectList, resultPartOfNameExisting);
    }

    @Test
    void searchByPartOfNameWithZeroResultsTest() {
        //Act
        var resultPartOfNameUnExisting = storageTest.searchByPartOfName("book8");

        //Assert
        assertTrue(resultPartOfNameUnExisting.isEmpty());
    }
}
