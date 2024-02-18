package com.example.jenproalgo.graph;

public class Edge<T> {

    private final Vertex<T> to;
    private final Double weight ;

    public Edge(Vertex<T> endV, Double inputWeight) {
        this.to = endV;
        this.weight = inputWeight;
    }

    public Vertex<T> getTo() {
        return this.to;
    }

    public Double getWeight() {
        return this.weight;
    }



}
