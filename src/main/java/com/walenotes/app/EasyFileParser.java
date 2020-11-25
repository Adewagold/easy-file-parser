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

public class EasyFileParser {
    private String directory;

    public EasyFileParser(String directory) {
        this.directory = directory;
    }

    private List<Path> getFiles(String dirPath){
        List<Path> filePaths = new ArrayList<>();
        Path directory = Paths.get(dirPath);
        if(!Files.exists(directory)){
            throw new FileSystemNotFoundException("Directory does not exists");
        }
        try {
            filePaths= Files.walk(directory).
                    filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePaths;
    }


    public List<String> getFilePaths(){
        List<String> files;
        files = getFiles(directory).stream()
                .map(path->path.toString())
                .collect(Collectors.toList());
        return files;
    }

    public static void main(String[] args){
        List<String> easyFileParser = new EasyFileParser("/Users/adewagold/javatemp").getFilePaths();
        easyFileParser.forEach(System.out::println);
    }

    public List<String> getFilePaths(int limit) {
        List<String> files;
        files = getFiles(directory)
                .stream()
                .map(path->path.toString())
                .limit(limit)
                .collect(Collectors.toList());
        return files;
    }
}
