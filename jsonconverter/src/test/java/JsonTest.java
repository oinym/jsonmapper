import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.oinym.JSONHanlder.*;
import static org.junit.Assert.assertTrue;

public class JsonTest {
    String userDirectory = new File("").getAbsolutePath();
    @Test
    public void testJsonArray(){

        JsonNode node;
        try {
            node = readFromJson(userDirectory +"/src/test/resources/array.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Json readable:"+node);
    }

    @Test
    public void testJsonObject(){

        JsonNode node;
        try {
            node = readFromJson(userDirectory+"/src/test/resources/object.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Json readable:"+node);
    }

    @Test
    public void testGetNode(){

        JsonNode attribute;
        try {
            JsonNode node = readFromJson(userDirectory+"/src/test/resources/object.json");
            attribute =getAttribute(node,"menu/popup/menuitem");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("attribute:\n"+attribute);
    }


    @Test
    public void testPutNode(){

        JsonNode newJson;
        try {
            JsonNode node = readFromJson(userDirectory+"/src/test/resources/object.json");
            JsonNode newValue = readFromJson(userDirectory+"/src/test/resources/array.json");
            newJson =updateAttribute(node,"menu/popup/menuitem",newValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("parent node:\n"+newJson);
    }

    @Test
    public void testAddNode(){

        JsonNode newJson;
        try {
            JsonNode node = readFromJson(userDirectory+"/src/test/resources/object.json");
            JsonNode newValue = readFromJson(userDirectory+"/src/test/resources/array.json");
            newJson =addAttribute(node,"menu/popup/menuitem",newValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("parent node:\n"+newJson);
    }


}
