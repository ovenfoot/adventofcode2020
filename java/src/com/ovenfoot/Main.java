package com.ovenfoot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String filename = args[0];
        List<String> inputList = convertInputFileToStringList(filename);
        System.out.println(inputList);
    }

    private static List<String> convertInputFileToStringList(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            list = br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            // Whoopsie doodle
        }

        return list;
    }
}
