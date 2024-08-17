package com.oinym;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class JSONHanlder {

    private static final String delimiter = "/";

    public static JsonNode readFromJson(String path) throws IOException {
        File filePath = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(filePath);
    }

    public static JsonNode updateAttribute(JsonNode node, String keyPath, JsonNode value) {
        String[] hirerarchy = keyPath.split(delimiter);
        String[] hirerarchyToParent=Arrays.copyOf(hirerarchy,hirerarchy.length-1);
        String pathToParent=String.join(delimiter,hirerarchyToParent);
        JsonNode parentNode=getAttribute(node,pathToParent);
        JsonNode currentChildNode=getAttribute(node,keyPath);
        // first let's check if the attribute already exists
        if(parentNode==null||parentNode.isEmpty()) throw new NullPointerException("Parent node " +hirerarchy[hirerarchy.length-1]+" does not exist");
        // JsonNode attribute = getAttribute(newNode, pathToParent);
        // if not exist
        if(!currentChildNode.isEmpty()){
            ((ObjectNode) parentNode).remove(hirerarchy[hirerarchy.length - 1]);
        }
        ((ObjectNode) parentNode).putIfAbsent(hirerarchy[hirerarchy.length - 1], value);
        return node;

    }
    public static JsonNode addAttribute(JsonNode node, String keyPath, JsonNode value) {
        // check that node exists
        String[] hirerarchy = keyPath.split(delimiter);
        String[] hirerarchyToParent=Arrays.copyOf(hirerarchy,hirerarchy.length-1);
        JsonNode currentNode=getAttribute(node,keyPath);
        if(currentNode==null||currentNode.isEmpty()) throw new NullPointerException("Node "+keyPath+" Does not exist");
        if(value.getNodeType()== JsonNodeType.ARRAY){

            ArrayNode newArrayNode=(ArrayNode) currentNode;
            newArrayNode.addAll( (ArrayNode) value);
            String pathToParent=String.join(delimiter,hirerarchyToParent);
            JsonNode parentNode=getAttribute(node,pathToParent);
            ((ObjectNode) parentNode).remove(hirerarchy[hirerarchy.length - 1]);
            ((ObjectNode) parentNode).putIfAbsent(hirerarchy[hirerarchy.length - 1],newArrayNode);
        }else{

        ((ObjectNode) currentNode).putIfAbsent(hirerarchy[hirerarchy.length - 1], value);
        }
        return node;
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
