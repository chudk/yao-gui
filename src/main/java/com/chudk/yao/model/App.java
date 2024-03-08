package com.chudk.yao.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class App {
    private String name;
    private String description;
    private String path;
    private int port;

    @SneakyThrows
    public static App load(String appJson){
        ObjectMapper mapper= new ObjectMapper();
        JsonNode json = mapper.readTree(appJson);
        return load(json);
    }

    public static App load(JsonNode appJson){
        App app = new App();
        if(appJson.has("name")) {
            app.name = appJson.get("name").asText();
        }
        if(appJson.has("description")) {
            app.description = appJson.get("description").asText();
        }
        return app;
    }
}
