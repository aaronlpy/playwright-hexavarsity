package utils;

import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties properties;

    static {
        properties = new Properties();
        try{
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        }catch (IOException e){
            System.out.printf("Error %s", e.getMessage());
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
