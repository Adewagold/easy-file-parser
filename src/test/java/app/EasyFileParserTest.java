package app;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.walenotes.app.EasyFileParser;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.FileSystemNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

public class EasyFileParserTest {
    public static final String TEST_DIR = "src/test/resources/testdir";
    public static final String DIRECTORY_WITH_FILES = "src/test/resources/testdir/testfiles";
    public static final String EMPTY_DIR = "src/test/resources/testdir/empty";

    @Test(expected = FileSystemNotFoundException.class)
    public void getFilesFromNonExistingDirectory() {
        List<String> getFiles = EasyFileParser.getFilePaths("NotFound");
    }

    @Test
    public void getFilesFromEmptyDirectory() {
        List<String> getFiles = EasyFileParser.getFilePaths(EMPTY_DIR);
        Assert.assertEquals(getFiles.size(),0);
    }

    @Test
    public void getFilesFromDirectory() {
        List<String> getFiles = EasyFileParser.getFilePaths(DIRECTORY_WITH_FILES);
        Assert.assertNotNull("Method returned files", getFiles);
    }

    @Test
    public void getFilesFromDirectoryWithLimit(){
        int limit = 2;
        List<String> getFiles = EasyFileParser.getFilePaths(DIRECTORY_WITH_FILES,limit);
        Assert.assertEquals(getFiles.size(),limit);
    }

    @Test
    public void getFilesFromDirectoryWithZeroLimit(){
        int limit = 0;
        List<String> getFiles = EasyFileParser.getFilePaths(DIRECTORY_WITH_FILES, limit);
        Assert.assertEquals(limit, getFiles.size());
    }

    @Test
    public void getFilesWithParticularExtension(){
        String testExtension = ".txt";
        List<String> filesWithExtension = EasyFileParser.getFilePaths(DIRECTORY_WITH_FILES,testExtension);
        filesWithExtension.forEach(path -> {
            Assert.assertEquals(path.substring(path.length()-testExtension.length()), testExtension);
        });
    }

    @Test
    public void getFilesWithJsonExtension(){
        String testExtension = ".json";
        List<String> filesWithExtension = EasyFileParser.getFilePaths(DIRECTORY_WITH_FILES, testExtension);
        filesWithExtension.forEach(path -> {
            Assert.assertEquals(path.substring(path.length()-testExtension.length()), testExtension);
        });
    }

    @Test
    public void readTestFiles(){
        String testExtension = ".txt";
        List<String> filesWithExtension = EasyFileParser.getFilePaths(DIRECTORY_WITH_FILES, testExtension);
        List<String> fileLines = EasyFileParser.readLines(filesWithExtension.get(0));
        Assert.assertEquals(fileLines.get(0), "This is a sample test file");
    }

    @Test
    public void readMultipleTestFileLines(){
        String testExtension = ".txt";
        List<String> filesWithExtension = EasyFileParser.getFilePaths(DIRECTORY_WITH_FILES, testExtension);
        filesWithExtension.forEach(sPath->{
            List<String> lines = EasyFileParser.readLines(sPath);
            Assert.assertEquals(lines.get(0), "This is a sample test file");
        });


    }

    @Test(expected = JsonParseException.class)
    public void readInvalidJsonFile() throws JsonProcessingException {
        String testExtension = ".json";
        String jsonFile = "src/test/resources/testdir/testfiles/invalid.json";
        String jsonString = EasyFileParser.readLines(jsonFile).get(0);
        EasyFileParser.readJsonObject(jsonString);

    }

    @Test()
    public void readValidJsonFile() throws JsonProcessingException {
        String testExtension = ".json";
        String jsonFile = "src/test/resources/testdir/testfiles/json_file_one.json";
        String jsonString = EasyFileParser.readLines(jsonFile).get(0);
        JsonNode jsonObject = EasyFileParser.readJsonObject(jsonString);
        assertNotNull(jsonObject);
    }

}