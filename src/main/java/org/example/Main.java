package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String fileName = "data.xml";
        String fileOutputName = "data.json";

        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json, fileOutputName);

    }

    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));

        NodeList nodeList = doc.getElementsByTagName("employee");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element employee = (Element) node;
                String id = employee.getElementsByTagName("id").item(0).getTextContent();
                String firstName = employee.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = employee.getElementsByTagName("lastName").item(0).getTextContent();
                String country = employee.getElementsByTagName("country").item(0).getTextContent();
                String age = employee.getElementsByTagName("age").item(0).getTextContent();
                Employee employer = new Employee(Long.parseLong(id), firstName, lastName, country, Integer.parseInt(age));
                list.add(employer);
            }
        }

        return list;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();

        return gson.toJson(list, listType);
    }

    public static void writeString(String json, String file) {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}