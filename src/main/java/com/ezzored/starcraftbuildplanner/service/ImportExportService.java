/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ezzored.starcraftbuildplanner.service;

import com.google.gson.Gson;
import com.ezzored.starcraftbuildplanner.model.Build;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Ezzored
 */
public class ImportExportService {

    Gson gson;

    public ImportExportService() {
        gson = new Gson();
    }

    public void saveBuilds(List<Build> buildList) {

        String jsonString = gson.toJson(buildList);

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.home")+"/builds.sc2builds"), StandardCharsets.UTF_8))) {
            writer.write(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Build> loadBuilds(File file) {
        
        String inputString = null;

        try {
            byte[] encoded = Files.readAllBytes(file.toPath());
            inputString = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Build[] mcArray = gson.fromJson(inputString, Build[].class);

        List<Build> returnList = Arrays.asList(mcArray);
        
        return returnList;
    }
}
