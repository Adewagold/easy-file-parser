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
        List<String> getFiles = new EasyFileParser("NotFound").getFiles();
    }

    @Test
    public void getFilesFromEmptyDirectory() {
        List<String> getFiles = new EasyFileParser(EMPTY_DIR).getFiles();
        Assert.assertEquals(getFiles.size(),0);
    }

    @Test
    public void getFilesFromDirectory() {
        List<String> getFiles = new EasyFileParser(DIRECTORY_WITH_FILES).getFiles();
        Assert.assertEquals(getFiles.size(),3);
    }

}