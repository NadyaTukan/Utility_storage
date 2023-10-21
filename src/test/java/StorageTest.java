import org.example.Reader;
import org.example.Storage;
import org.example.UsefulMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class StorageTest {

    private Storage storageTest;

    @BeforeEach
    public void createNewStorage() {
       storageTest = new Storage("./src/test/java/dataTest.json");
    }

    @Test
    void addInStorage() {
        Map<Integer, UsefulMaterial> materialsTest = Reader.read("./src/test/java/dataTest.json");
        Storage storageExpect = new Storage(materialsTest);
        for (Map.Entry<Integer, UsefulMaterial> resultMaterial: storageTest.getMaterials().entrySet()) {
            var keyResultMaterial =   resultMaterial.getKey();
            var expectMaterial = storageExpect.getMaterials().get(keyResultMaterial).getInfo();
            assertEquals(expectMaterial, resultMaterial.getValue().getInfo());
        }
    }

    @Test
    void searchByIDTest() {

        var resultExistingID  =  storageTest.searchByID(1);
        var resultUnExistingID = storageTest.searchByID(100);

        assertEquals(resultExistingID, storageTest.getMaterials().get(1).getInfo());
        assertNull(resultUnExistingID);
    }
    @Test
    void searchByPartOfNameTest() {

        var resultPartOfNameExisting  =  storageTest.searchByPartOfName("book1");
        var resultPartOfNameUnExisting = storageTest.searchByPartOfName("none");

        ArrayList<UsefulMaterial> expectList = new ArrayList<>();
        expectList.add(new UsefulMaterial(1, "book1", "book1", "book1"));

        for (UsefulMaterial result: resultPartOfNameExisting) {
            for (UsefulMaterial expect: expectList) {
                assertEquals(result.getInfo(), expect.getInfo());
            }
        }
        assertTrue(resultPartOfNameUnExisting.isEmpty());
    }
}
