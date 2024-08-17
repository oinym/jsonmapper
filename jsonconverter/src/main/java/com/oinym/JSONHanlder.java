package com.oinym;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JSONHanlder {

    private static final String delimiter = "/";

    public static JsonNode readFromJson(String path) throws IOException {
        File filePath = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(filePath);
        return jsonNode;
    }

    public static JsonNode updateAttribute(JsonNode node, String keyPath, JsonNode value) {
        String[] hirerarchy = keyPath.split(delimiter);
        String[] hirerarchyToParent=Arrays.copyOf(hirerarchy,hirerarchy.length-1);
        JsonNode newNode = node;
        String pathToParent=String.join(delimiter,hirerarchyToParent);
        JsonNode parentNode=getAttribute(newNode,pathToParent);
        JsonNode currentChildNode=getAttribute(newNode,keyPath);
        // first let's check if the attribute already exists
        if(parentNode==null||parentNode.equals("")) throw new NullPointerException("Parent node " +hirerarchy[hirerarchy.length-1]+" does not exist");
        JsonNode attribute = getAttribute(newNode, pathToParent);
        // if not exist
        if(!currentChildNode.equals("")){
            ((ObjectNode) parentNode).remove(hirerarchy[hirerarchy.length - 1]);
        }
        ((ObjectNode) parentNode).put(hirerarchy[hirerarchy.length - 1], value);
        return newNode;

    }
    public static JsonNode addAttribute(JsonNode node, String keyPath, JsonNode value) {
        // check that node exists
        String[] hirerarchy = keyPath.split(delimiter);
        String[] hirerarchyToParent=Arrays.copyOf(hirerarchy,hirerarchy.length-1);
        JsonNode newNode=node;
        JsonNode currentNode=getAttribute(node,keyPath);
        if(currentNode==null||currentNode.equals("")) throw new NullPointerException("Node "+keyPath+" Does not exist");
        if(value.getNodeType()== JsonNodeType.ARRAY){

            ArrayNode newArrayNode=(ArrayNode) currentNode;
            newArrayNode.addAll( (ArrayNode) value);
            String pathToParent=String.join(delimiter,hirerarchyToParent);
            JsonNode parentNode=getAttribute(newNode,pathToParent);
            ((ObjectNode) parentNode).remove(hirerarchy[hirerarchy.length - 1]);
            ((ObjectNode) parentNode).put(hirerarchy[hirerarchy.length - 1],newArrayNode);
        }else{

        ((ObjectNode) currentNode).put(hirerarchy[hirerarchy.length - 1], value);
        }
        return newNode;
    }


    public static JsonNode getAttribute(JsonNode node, String keyPath) {

        String[] hirerarchy = keyPath.split(delimiter);
        JsonNode newNode = node;
        for (String key : hirerarchy) {
            newNode = newNode.path(key);
        }
        return newNode;

    }

}
