package org.example;

import org.example.models.Config;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    private String configFilePath;

    public FileHandler(String configFilePath){
        if(configFilePath.equals(""))
            this.setConfigFilePath(System.getProperty("user.dir") + File.separator + "default-config.txt");
        else
            this.setConfigFilePath(configFilePath);
    }

    public Config readConfigSettings(){
        try{
            File configFile = new File(this.getConfigFilePath());
            Scanner fileScanner = new Scanner(configFile);
            Config config = new Config();
            
            int i = 1;

            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine().strip();
                switch (i) {
                    case 1 -> config.setxSize(line);
                    case 2 -> config.setySize(line);
                    case 3 -> config.setIterationCount(line);
                    case 4 -> config.setInitialLiveCellCount(line);
                    default -> config.addInitialCellCoords(line);
                }
                i++;

            }
            config.validateInitialCoordsLength();

            return config;

        } catch (IOException e){
            System.out.println("Cannot read given config file with path : " + this.configFilePath);
        } catch (InvalidParameterException e){
            System.out.println("Error occured during parsing config : " + e.getMessage());
        }

        return null;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }
}
