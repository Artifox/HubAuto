package utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
@Log4j2
public class PropertyManager {

    private final String propertyFilePath;
    private Properties prop;

    public PropertyManager() {
        propertyFilePath = System.getProperty("user.dir") + "/src/test/resources/" + System.getProperty("stage", "dev") + "_env.properties";
        loadData();
    }

    private void loadData() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String propertyName) {
        String value = prop.getProperty(propertyName);
        if (StringUtils.isEmpty(value)) {
            log.error(String.format("Property: '%s' doesn't exist or empty", propertyName));
            throw new IllegalStateException(String.format("Property: '%s' doesn't exist or empty", propertyName));
        } else {
            log.debug(String.format("Obtained property '%s' with value '%s'", propertyName, value));
        }
        return value;
    }
}