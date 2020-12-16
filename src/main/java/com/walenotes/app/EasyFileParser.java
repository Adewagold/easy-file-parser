package com.walenotes.app;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * This class consists exclusively of static methods that operate on files,
 * directories, or other types of files.
 *
 * <p> In most cases, the methods defined here will delegate to the associated
 * file system provider to perform the file operations.
 *
 * @since 1.7
 */
public class EasyFileParser {

    private EasyFileParser() {
    }

    private static Stream<Path> getFiles(String dirPath){

        Path directory = Paths.get(dirPath);
        if(!Files.exists(directory)){
            throw new FileSystemNotFoundException("Directory does not exists");
        }
        try {
            return Files.walk(directory).
                    filter(Files::isRegularFile);
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    private static List<String> fetchPathList(String directory){
        List<String> files;
        files = getFiles(directory)
                .map(Path::toString)
                .collect(Collectors.toList());
        return files;
    }

    public static List<String> getFilePaths(String directory){
        List<String> files;
        files = new ArrayList<>(fetchPathList(directory));
        return files;
    }

    public static List<String> getFilePaths(String directory, int limit) {
        List<String> files;
        files = fetchPathList(directory)
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
        return files;
    }

    public static List<String> getFilePaths(String directory, String extension) {
        List<String> files;
        files = fetchPathList(directory)
                .stream()
                .filter(path -> path.endsWith(extension))
                .collect(Collectors.toList());
        return files;
    }

    public static List<String> readLines(String s) {
         List<String> lines = new ArrayList<>();
        try(FileReader fileReader = new FileReader(s)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
        }catch (IOException ex){
            ex.printStackTrace();
            throw new Error(ex);
        }
        return lines;
    }

    public static JsonNode readJsonObject(String jsonString) throws JsonProcessingException {
        try{
            return new ObjectMapper().readTree(jsonString);
        }
        catch (IOException ex){
            throw ex;
        }
    }
}
