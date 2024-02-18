package com.example.jenproalgo.graph;


import java.util.Iterator;
import java.util.LinkedList;

public class Vertex<T> {

    private final T data;
    private final LinkedList<Edge<T>> edges;


    public Vertex(T inputData) {
        this.data = inputData;
        this.edges = new LinkedList<>();

    }

    public void addEdge(Vertex<T> endVertex, Double weight) {
        edges.add(new Edge<T>(endVertex, weight));
    }
    public void removeEdge(Vertex<T> endVertex) {
        edges.removeIf(edge -> edge.getTo().equals(endVertex));
    }

    public T getData() {
        return this.data;
    }

    public LinkedList<Edge<T>> getEdges(){
        return this.edges;
    }


    public void print(boolean showWeight) {
        StringBuilder str = new StringBuilder();

        if (this.edges.size() == 0) {
            System.out.println(this.data + " -->");
            return;
        }

        Iterator<Edge<T>> iter = this.edges.iterator();
        while (iter.hasNext()) {
            Edge<T> edge = iter.next();
            str.append(edge.getTo().getData());
            if (showWeight) {
                str.append("(").append(edge.getWeight()).append(")");
            }
            if (iter.hasNext()) {
                str.append(" --> ");
            }
        }

        System.out.println(this.data + " --> " + str);

    }

    //to string method
    @Override
    public String toString() {
        return this.data.toString();
    }

}
