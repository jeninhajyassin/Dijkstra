package com.example.jenproalgo.graph;


import java.util.HashMap;

public class Graph<T> {

    private final HashMap<T, Vertex<T>> vertices;

    private final boolean isWeighted;
    private final boolean isDirected;


    public Graph() {
        this.vertices = new HashMap<>();
        this.isWeighted = false;
        this.isDirected = false;
    }

    public Graph(boolean isWeighted, boolean isDirected) {
        this.vertices = new HashMap<>();
        this.isWeighted = isWeighted;
        this.isDirected = isDirected;
    }

    public Vertex<T> addVertex(T data) {
        Vertex<T> newVertex = new Vertex<>(data);
        this.vertices.put(data, newVertex);

        return newVertex;
    }


    public void addEdge(Vertex<T> vertex1, Vertex<T> vertex2, Double weight) {
        if (!this.isWeighted) {
            weight = null;
        }
        vertex1.addEdge(vertex2, weight);

        if (!this.isDirected) {
            vertex2.addEdge(vertex1, weight);
        }
    }

    public void removeEdge(Vertex<T> vertex1, Vertex<T> vertex2) {
        vertex1.removeEdge(vertex2);

        if (!this.isDirected) {
            vertex2.removeEdge(vertex1);
        }
    }

    public void removeVertex(Vertex<T> vertex) {
        //remove all edges that contain the vertex
        for (Vertex<T> v : this.vertices.values()) {
            v.removeEdge(vertex);
        }

        //remove the vertex
        this.vertices.remove(vertex.getData());
    }


    public HashMap<T, Vertex<T>> getVertices() {
        return this.vertices;
    }

    public boolean isWeighted() {
        return this.isWeighted;
    }

    public boolean isDirected() {
        return this.isDirected;
    }

    //get the vertex with the given data
    public Vertex<T> getVertex(T data) {
        return this.vertices.get(data);
    }

    public void print() {
        for (Vertex<T> vertex : this.vertices.values()) {
            vertex.print(this.isWeighted);
        }
    }






}
