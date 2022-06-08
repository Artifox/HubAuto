package ui.tests;

import org.testng.annotations.Test;
import utils.PropertyManager;

public class TestAutomation {

    @Test
    public void testReader(){
        PropertyManager propertyManager = new PropertyManager();
        System.out.println(System.getenv().getOrDefault("default_env", propertyManager.getProperty("env")));
    }
}
