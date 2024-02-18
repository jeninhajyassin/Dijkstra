module com.example.jenproalgo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jenproalgo to javafx.fxml;
    exports com.example.jenproalgo;
    exports com.example.jenproalgo.graph;
    opens com.example.jenproalgo.graph to javafx.fxml;
}