package com.walenotes.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> fetchPathList(){
        List<String> files;
        files = getFiles(directory)
                .map(path->path.toString())
                .collect(Collectors.toList());
        return files;
    }

    public List<String> getFilePaths(){
        List<String> files;
        files = new ArrayList<>(fetchPathList());
        return files;
    }

    public static void main(String[] args){
        List<String> easyFileParser = new EasyFileParser("/Users/adewagold/javatemp").getFilePaths();
        easyFileParser.forEach(System.out::println);
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
}
