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

public class EasyFileParser {

    private final String directory;

    public EasyFileParser(String directory) {
        this.directory = directory;
    }

    private Stream<Path> getFiles(String dirPath){

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

    private List<String> fetchPathList(){
        List<String> files;
        files = getFiles(directory)
                .map(Path::toString)
                .collect(Collectors.toList());
        return files;
    }

    public List<String> getFilePaths(){
        List<String> files;
        files = new ArrayList<>(fetchPathList());
        return files;
    }

    public List<String> getFilePaths(int limit) {
        List<String> files;
        files = fetchPathList()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
        return files;
    }

    public List<String> getFilePaths(String extension) {
        List<String> files;
        files = fetchPathList()
                .stream()
                .filter(path -> path.endsWith(extension))
                .collect(Collectors.toList());
        return files;
    }

    public List<String> readLines(String s) {
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

    public JsonNode readJsonObject(String jsonString) throws JsonProcessingException {
        try{
            return new ObjectMapper().readTree(jsonString);
        }
        catch (IOException ex){
            throw ex;
        }
    }
}
