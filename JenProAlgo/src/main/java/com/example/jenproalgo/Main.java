package com.example.jenproalgo;

import com.example.jenproalgo.graph.Edge;
import com.example.jenproalgo.graph.Graph;
import com.example.jenproalgo.graph.Vertex;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main extends Application {

    HashMap<String, Building> buildingHashMap = new HashMap<>();
    HashMap<String, Vertex<Building>> vertices = new HashMap<>();

    Graph<Building> graph = new Graph<>(true, true);


    @Override
    public void start(Stage stage){

        BorderPane mainPane = new BorderPane();
        Scene mainPageScene = new Scene(mainPane, 1200, 700);
        mainPane.setPadding(new Insets(20));


        //style the scene
        mainPageScene.getStylesheets().add("style.css");

        //right side
        VBox rightSide = new VBox();
        rightSide.setSpacing(30);

        Label pathLabel = new Label("  Path Details  ");
        pathLabel.setStyle("-fx-font-weight: bold;-fx-background-color: #574E95; -fx-border-radius: 10px; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 20px;");


        //table
        TableView<TableRow> pathTable = new TableView<>();
        pathTable.setPrefWidth(300);
        pathTable.setPrefHeight(300);

        //add columns
        TableColumn<TableRow, String> fromColumn = new TableColumn<>("From");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        fromColumn.setPrefWidth(100);
        TableColumn<TableRow, String> toColumn = new TableColumn<>("To");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        toColumn.setPrefWidth(100);
        TableColumn<TableRow, String> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distanceColumn.setPrefWidth(100);

        pathTable.getColumns().add(fromColumn);
        pathTable.getColumns().add(toColumn);
        pathTable.getColumns().add(distanceColumn);


        //total distance
        Label totalDistanceLabel = new Label(" Total Distance: ");
        totalDistanceLabel.setStyle("-fx-font-weight: bold;-fx-background-color: #574E95; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: white; -fx-font-size: 16px;");
        TextField totalDistanceField = new TextField();
        totalDistanceField.setPrefWidth(100);
        totalDistanceField.setEditable(false);

        HBox totalDistanceBox = new HBox();
        totalDistanceBox.setSpacing(10);
        totalDistanceBox.setAlignment(Pos.CENTER);
        totalDistanceBox.getChildren().addAll(totalDistanceLabel, totalDistanceField);


        //path box
        VBox pathBox = new VBox();
        pathBox.setSpacing(10);
        pathBox.setAlignment(Pos.CENTER);
        pathBox.getChildren().addAll(pathLabel, pathTable, totalDistanceBox);


        //from and to
        Label fromLabel = new Label("From:");
        fromLabel.setStyle("-fx-font-weight: bold;-fx-background-color: #574E95; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: white; -fx-font-size: 16px;");
        fromLabel.setMaxWidth(80);
        fromLabel.setMinWidth(80);
        fromLabel.setPrefWidth(80);
        fromLabel.setAlignment(Pos.CENTER);

        ComboBox<String> sourceBuilding = new ComboBox<>();
        sourceBuilding.setPrefHeight(30);
        sourceBuilding.setPrefWidth(150);
        new AutoCompleteComboBoxListener<>(sourceBuilding);
        sourceBuilding.setStyle("-fx-font-size: 14px;-fx-border-radius: 10px; -fx-background-radius: 10px;");

        HBox fromBox = new HBox();
        fromBox.setSpacing(10);
        fromBox.setAlignment(Pos.CENTER);
        fromBox.getChildren().addAll(fromLabel, sourceBuilding);


        Label toLabel = new Label("To:");
        toLabel.setStyle("-fx-font-weight: bold;-fx-background-color: #574E95; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: white; -fx-font-size: 16px;");
        toLabel.setMaxWidth(80);
        toLabel.setMinWidth(80);
        toLabel.setPrefWidth(80);
        toLabel.setAlignment(Pos.CENTER);

        ComboBox<String> destinationBuilding = new ComboBox<>();
        destinationBuilding.setPrefHeight(30);
        destinationBuilding.setPrefWidth(150);
        destinationBuilding.setStyle("-fx-font-size: 14px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        new AutoCompleteComboBoxListener<>(destinationBuilding);

        HBox toBox = new HBox();
        toBox.setSpacing(10);
        toBox.setAlignment(Pos.CENTER);
        toBox.getChildren().addAll(toLabel, destinationBuilding);

        //from and to box
        VBox srcDestBox = new VBox();
        srcDestBox.setSpacing(10);
        srcDestBox.setAlignment(Pos.CENTER);
        srcDestBox.getChildren().addAll(fromBox, toBox);

        //button
        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);

        Button findPath = new Button("Find Path");
        findPath.setPrefSize(150, 35);
        findPath.setMaxSize(150, 35);
        findPath.setMinSize(150, 35);
        findPath.setStyle("-fx-background-color: #F9F7F2;-fx-border-color: #574E95; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 16px; -fx-text-fill: #574E95;");
        findPath.setOnMouseEntered(e -> findPath.setStyle("-fx-background-color: #574E95;-fx-border-color: #574E95; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 16px; -fx-text-fill: white;"));
        findPath.setOnMouseExited(e -> findPath.setStyle("-fx-background-color: #F9F7F2;-fx-border-color: #574E95; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 16px; -fx-text-fill: #574E95;"));

        Button clearPath = new Button("Clear");
        clearPath.setPrefSize(150, 35);
        clearPath.setMaxSize(150, 35);
        clearPath.setMinSize(150, 35);
        clearPath.setStyle("-fx-background-color: #F9F7F2;-fx-border-color: #574E95; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 16px;-fx-text-fill: #574E95;");
        clearPath.setOnMouseEntered(e -> clearPath.setStyle("-fx-background-color: #574E95;-fx-border-color: #574E95; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 16px; -fx-text-fill: white;"));
        clearPath.setOnMouseExited(e -> clearPath.setStyle("-fx-background-color: #F9F7F2;-fx-border-color: #574E95; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 16px;-fx-text-fill: #574E95;"));
        buttons.getChildren().addAll(findPath, clearPath);


        rightSide.setAlignment(Pos.TOP_CENTER);
        rightSide.getChildren().addAll(pathBox, srcDestBox, buttons);
        mainPane.setRight(rightSide);

        //map
        Image mapImage = new Image("bzuMap.jpg");
        ImageView mapImageView = new ImageView(mapImage);
        mapImageView.setPreserveRatio(true);
        mapImageView.setFitWidth(700 );
        mapImageView.setFitHeight(466.930022574);

        //466.930022574

        //map pane
        Pane mapPane = new Pane();
        mapPane.getChildren().add(mapImageView);
        mapPane.setMaxSize(700 +10, 466.930022574 + 10);
        mapPane.setMinSize(700 +10, 466.930022574 +10);
        mapPane.setPrefSize(700 + 10, 466.930022574 + 10);
        mapPane.setStyle("-fx-border-color: #574E95; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        //align map to center of the pane
        mapImageView.layoutXProperty().bind(mapPane.widthProperty().subtract(mapImageView.getFitWidth()).divide(2));
        mapImageView.layoutYProperty().bind(mapPane.heightProperty().subtract(mapImageView.getFitHeight()).divide(2));

        mainPane.setLeft(mapPane);
        BorderPane.setAlignment(mapPane, Pos.CENTER);

        //Title Label
        Image logoImage = new Image("logoJ.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(400);
        logoImageView.setPreserveRatio(true);
        mainPane.setTop(logoImageView);
        BorderPane.setAlignment(logoImageView, Pos.CENTER);


        //read Buildings
        readData();


        //add countries to choice boxes
        String[] BuildingNames = getBuildingNames();
        sourceBuilding.getItems().addAll(BuildingNames);
        destinationBuilding.getItems().addAll(BuildingNames);


        //add points to the map
        for(String building : buildingHashMap.keySet()){
            Circle circle = new Circle(5);

            double x = ratioX(buildingHashMap.get(building).getX());
            circle.setCenterX(x);

            double y = ratioY(buildingHashMap.get(building).getY());
            circle.setCenterY(y);

            circle.setStyle("-fx-fill: #ff0000; -fx-stroke: black; -fx-stroke-width: 1px;");
            circle.setOnMousePressed(e -> {
                //handle left click and right click
                if(e.getButton() == MouseButton.PRIMARY){
                    sourceBuilding.setValue(building);
                }else if(e.getButton() == MouseButton.SECONDARY){
                    destinationBuilding.setValue(building);
                }
            });
            mapPane.getChildren().add(circle);
        }


        findPath.setOnAction(e -> {

            String from = sourceBuilding.getValue();
            String to = destinationBuilding.getValue();
            if(from == null || to == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please select a building");
                alert.showAndWait();
            }else if (!vertices.containsKey(from)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Source building not found");
                alert.showAndWait();
            } else if (!vertices.containsKey(to)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Destination building not found");
                alert.showAndWait();
            } else{
                Dijkstra<Building> dijkstra = new Dijkstra<>(graph);
                try {
                    LinkedList<Vertex<Building>> shortestPath =  dijkstra.getShortestPath(vertices.get(from), vertices.get(to));

                    if (shortestPath == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("No path found");
                        alert.showAndWait();
                    }else{

                        Image image = new Image("girl.png");
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        imageView.setPreserveRatio(true);
                        imageView.setId("girl");


                        pathTable.getItems().clear();
                        mapPane.getChildren().removeIf(node -> node instanceof Line);
                        mapPane.getChildren().removeIf(node -> node instanceof ImageView && node.getId() != null && node.getId().equals("girl"));
                        double totalDistance = 0;
                        SequentialTransition sequentialTransition = new SequentialTransition();
                        for(int i = 0; i < shortestPath.size() - 1; i++){
                            Vertex<Building> fromVertex = shortestPath.get(i);
                            Vertex<Building> toVertex = shortestPath.get(i + 1);
                            String fromName = fromVertex.getData().getName();
                            String toName = toVertex.getData().getName();


                            Line line = new Line();
                            line.setStartX(ratioX(buildingHashMap.get(fromName).getX()));
                            line.setStartY(ratioY(buildingHashMap.get(fromName).getY()));
                            line.setEndX(ratioX(buildingHashMap.get(toName).getX()));
                            line.setEndY(ratioY(buildingHashMap.get(toName).getY()));
//                            line.setStroke(new Color(0.3412, 0.3059, 0.5843, 1));
                            line.setStroke(Color.RED);
                            line.setStrokeWidth(2);

                            //add line to map in sequential order
                            FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), line);
                            fadeTransition.setFromValue(0);
                            fadeTransition.setToValue(1);

                            //add person to move along the line
                            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1300), imageView);
                            translateTransition.setFromX(line.getStartX());
                            translateTransition.setFromY(line.getStartY());
                            translateTransition.setToX(line.getEndX());
                            translateTransition.setToY(line.getEndY());
                            translateTransition.setCycleCount(1);
                            translateTransition.setAutoReverse(false);


                            sequentialTransition.getChildren().add(fadeTransition);
                            sequentialTransition.getChildren().add(translateTransition);

                            mapPane.getChildren().add(line);

                            double distance = getDistance(fromName, toName);
                            totalDistance += distance;
                            TableRow row = new TableRow(fromName, toName, String.format("%.2f", distance) + " m");
                            pathTable.getItems().add(row);
                        }
                        mapPane.getChildren().add(imageView);
                        sequentialTransition.play();
                        totalDistanceField.setText(String.format("%.2f", totalDistance) + " m");


                    }
                }catch (IllegalArgumentException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You are already in the destination building");
                    alert.showAndWait();
                }
            }
        });


        //clear path button
        clearPath.setOnAction(e -> {
            pathTable.getItems().clear();
            mapPane.getChildren().removeIf(node -> node instanceof Line);
            totalDistanceField.setText("");
            mapPane.getChildren().removeIf(node -> node instanceof ImageView && node.getId() != null && node.getId().equals("girl"));
            sourceBuilding.setValue(null);
            destinationBuilding.setValue(null);

        });


        stage.setTitle("Birzeit Map");
        stage.setScene(mainPageScene);
        stage.show();
    }

    private double getDistance(String fromName, String toName) {
        Vertex<Building> fromVertex = vertices.get(fromName);
        Vertex<Building> toVertex = vertices.get(toName);
        for (Edge<Building> edge : fromVertex.getEdges()) {
            if (edge.getTo().equals(toVertex)) {
                return edge.getWeight();
            }
        }
        return 0;

    }

    private String[] getBuildingNames() {
        String[] buildings = new String[buildingHashMap.size()];
        //get the names of the countries
        int i = 0;
        for(String key : buildingHashMap.keySet()){
            buildings[i] = key;
            i++;
        }
        return buildings;
    }


    private void readData() {

        int numberOfBuildings;
        int numberOfPaths;

        try{

            File file = new File("data.txt");
            Scanner scanner = new Scanner(file);

            numberOfBuildings = scanner.nextInt();
            numberOfPaths = scanner.nextInt();
            scanner.nextLine();

            for(int i = 0; i < numberOfBuildings; i++){
                String line = scanner.nextLine();
                if (line.length() > 0) {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String name = parts[0];
                        double lat = Double.parseDouble(parts[1]);
                        double lon = Double.parseDouble(parts[2]);
                        Building building = new Building(name, lat, lon);
                        buildingHashMap.put(name, building);

                    }
                }
            }

            //add vertices to graph
            for(String buildingName : buildingHashMap.keySet()){
                Vertex<Building> vertex = graph.addVertex(buildingHashMap.get(buildingName));
                vertices.put(buildingName, vertex);
            }


            for(int i = 0; i < numberOfPaths; i++){
                String line = scanner.nextLine();
                if (line.length() > 0) {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String from = parts[0];
                        String to = parts[1];
                        String distanceString = parts[2];
                        if (buildingHashMap.containsKey(from) && buildingHashMap.containsKey(to)){
                            double distance = Double.parseDouble(distanceString);
                            graph.addEdge(vertices.get(from), vertices.get(to), distance);
                            //add the reverse edge
                            graph.addEdge(vertices.get(to), vertices.get(from), distance);
                        }
                    }
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File not found");
            alert.showAndWait();
        }


    }


    private double ratioX(double real){
        return (real / 1772) * (700+10);
    }
    private double ratioY(double real){
        return (real / 1182) * (466.930022574+10);
    }

    public static void main(String[] args) {
        launch();
    }
}