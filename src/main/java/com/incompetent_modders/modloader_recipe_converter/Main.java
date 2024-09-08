package com.incompetent_modders.modloader_recipe_converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incompetent_modders.modloader_recipe_converter.foundation.Modloaders;
import com.incompetent_modders.modloader_recipe_converter.foundation.convertion.AmountConverting;
import com.incompetent_modders.modloader_recipe_converter.foundation.convertion.TagConverting;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        // Create a new JFrame object
        JFrame window = new JFrame();
        
        // Set the window properties
        window.setTitle("Modloader Data Converter");
        window.setSize(1000, 600);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ImageIcon icon = new ImageIcon("icon.gif");
        window.setIconImage(icon.getImage());
        //window.setIconImage(new ImageIcon("icon.png").getImage());
        window.setBackground(Color.darkGray);
        // Add components to the window
        Container contentPane = window.getContentPane();
        
        contentPane.setBackground(Color.darkGray);
        //Add a drop-down list of values from Modloaders.java
        
        TextField searchField = new TextField();
        
        Button searchButton = new Button("Search...");
        searchButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedFolder = fileChooser.getSelectedFile().getAbsolutePath();
                searchField.setText(selectedFolder);
                // Perform any additional operations with the selected folder
            }
        });
        searchField.setPreferredSize(new Dimension(800, 22));
        searchButton.setPreferredSize(new Dimension(50, 22));
        contentPane.add(searchField, BorderLayout.CENTER);
        contentPane.add(searchButton, BorderLayout.EAST);
        
        
        contentPane.setLayout(new FlowLayout());
        Choice convertingFrom = new Choice();
        convertingFrom.add("Convert From Forge");
        convertingFrom.add("Convert From Fabric");
        contentPane.add(convertingFrom);
        convertingFrom.addItemListener(e -> System.out.println("Selected: " + e.getItem()));
        
        Choice convertingTo = new Choice();
        convertingTo.add("Convert To Forge");
        convertingTo.add("Convert To Fabric");
        contentPane.add(convertingTo);
        convertingTo.addItemListener(e -> System.out.println("Selected: " + e.getItem()));
        
        Button convertButton = new Button("Convert");
        contentPane.add(convertButton);
        convertButton.addActionListener(e -> {
            if (searchField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(window, "Please select a folder to convert.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (convertingFrom.getSelectedItem().equals(convertingTo.getSelectedItem())) {
                JOptionPane.showMessageDialog(window, "The 'From' and 'To' modloaders cannot be the same.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            File folder = new File(searchField.getText());
            
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
            if (files != null) {
                for (File file : files) {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.enable(SerializationFeature.INDENT_OUTPUT);
                        ObjectNode jsonNode = (ObjectNode) mapper.readTree(file);
                        if (jsonNode.has("convertedTo") && jsonNode.get("convertedTo").asText().equals(getConvert(convertingTo.getSelectedItem()).getName())) {
                            System.out.println(file.getAbsolutePath() + " has already been converted.");
                            continue;
                        }
                        // Modify the JSON parameters as needed
                        TagConverting.convertItemTag(jsonNode, getConvert(convertingFrom.getSelectedItem()), getConvert(convertingTo.getSelectedItem()));
                        TagConverting.convertFluidTag(jsonNode, getConvert(convertingFrom.getSelectedItem()), getConvert(convertingTo.getSelectedItem()));
                        AmountConverting.convertFluidAmount(jsonNode, getConvert(convertingFrom.getSelectedItem()), getConvert(convertingTo.getSelectedItem()));
                        jsonNode.put("info", "This recipe has been modified by the Incompetent Modders Recipe Converter.");
                        jsonNode.put("convertedFrom", getConvert(convertingFrom.getSelectedItem()).getName());
                        jsonNode.put("convertedTo", getConvert(convertingTo.getSelectedItem()).getName());
                        // Write the modified JSON back to the file
                        mapper.writeValue(file, jsonNode);
                        
                        System.out.println("Converting from " + getConvert(convertingFrom.getSelectedItem()) + " to " + getConvert(convertingTo.getSelectedItem()) + "...");
                        System.out.println("Modified file: " + file.getAbsolutePath());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        //TextField logField = new TextField();
        //logField.setPreferredSize(new Dimension(1000, 400));
        //logField.setEditable(false);
        //contentPane.add(logField);
        
        
        // Add your components here
        
        // Display the window
        window.setVisible(true);
    }
    
    private static Modloaders getConvert(String choiceText) {
        return switch (choiceText) {
            case "Convert From Forge", "Convert To Forge" -> Modloaders.FORGE;
            case "Convert From Fabric", "Convert To Fabric" -> Modloaders.FABRIC;
            default -> null;
        };
    }
}