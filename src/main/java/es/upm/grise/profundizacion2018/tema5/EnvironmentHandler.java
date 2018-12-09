package es.upm.grise.profundizacion2018.tema5;

import java.util.HashMap;

public class EnvironmentHandler {

    public String getVariable(String key){
        return System.getenv(key);
    }
}
