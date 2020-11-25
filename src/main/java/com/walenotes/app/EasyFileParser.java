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

    public List<String> getFiles(){
        List<String> files = new ArrayList<String>();
        Path dirPath = Paths.get(directory);
        if(!Files.exists(dirPath)){
            throw new FileSystemNotFoundException("Directory does not exists");
        }
        try {
            files = Files.walk(dirPath).
                    filter(Files::isRegularFile)
                    .map(path -> path.toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static void main(String[] args){
        List<String> easyFileParser = new EasyFileParser("/Users/adewagold/javatemp").getFiles();
        easyFileParser.forEach(System.out::println);
    }
}
