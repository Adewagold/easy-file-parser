package app;

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
        List<String> getFiles = new EasyFileParser("NotFound").getFilePaths();
    }

    @Test
    public void getFilesFromEmptyDirectory() {
        List<String> getFiles = new EasyFileParser(EMPTY_DIR).getFilePaths();
        Assert.assertEquals(getFiles.size(),0);
    }

    @Test
    public void getFilesFromDirectory() {
        List<String> getFiles = new EasyFileParser(DIRECTORY_WITH_FILES).getFilePaths();
        Assert.assertNotNull("Method returned files", getFiles);
    }

    @Test
    public void getFilesFromDirectoryWithLimit(){
        int limit = 2;
        List<String> getFiles = new EasyFileParser(DIRECTORY_WITH_FILES).getFilePaths(limit);
        Assert.assertEquals(getFiles.size(),limit);
    }

    @Test
    public void getFilesFromDirectoryWithZeroLimit(){
        int limit = 0;
        List<String> getFiles = new EasyFileParser(DIRECTORY_WITH_FILES).getFilePaths(limit);
        Assert.assertEquals(limit, getFiles.size());
    }

    @Test
    public void getFilesWithParticularExtension(){
        String testExtension = ".txt";
        List<String> filesWithExtension = new EasyFileParser(DIRECTORY_WITH_FILES).getFilePaths(testExtension);
        filesWithExtension.forEach(path -> {
            Assert.assertEquals(path.substring(path.length()-testExtension.length()), testExtension);
        });
    }

    @Test
    public void getFilesWithJsonExtension(){
        String testExtension = ".json";
        List<String> filesWithExtension = new EasyFileParser(DIRECTORY_WITH_FILES).getFilePaths(testExtension);
        filesWithExtension.forEach(path -> {
            Assert.assertEquals(path.substring(path.length()-testExtension.length()), testExtension);
        });
    }

    @Test
    public void readTestFiles(){
        String testExtension = ".txt";
        EasyFileParser fileParser = new EasyFileParser(DIRECTORY_WITH_FILES);
        List<String> filesWithExtension = fileParser.getFilePaths(testExtension);
        List<String> fileLines = fileParser.readLines(filesWithExtension.get(0));
        Assert.assertEquals(fileLines.get(0), "This is a sample test file");
    }

    @Test
    public void readMultipleTestFileLines(){
        String testExtension = ".txt";
        EasyFileParser fileParser = new EasyFileParser(DIRECTORY_WITH_FILES);
        List<String> filesWithExtension = fileParser.getFilePaths(testExtension);
        filesWithExtension.forEach(sPath->{
            List<String> lines = fileParser.readLines(sPath);
            Assert.assertEquals(lines.get(0), "This is a sample test file");
        });


    }
    @Test
    public void readJsonFile(){
//        JsonNode jsonObject = new JsonNode();
    }

}