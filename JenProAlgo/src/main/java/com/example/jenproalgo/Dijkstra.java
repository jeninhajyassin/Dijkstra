package com.example.jenproalgo;

import com.example.jenproalgo.graph.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dijkstra<T> {

    Graph<T> graph;

    HashMap<T, Double> distances;
    HashMap<T, Vertex<T>> previous;


    //constructor
    public Dijkstra(Graph<T> g) {
        if (g == null) throw new IllegalArgumentException("Graph cannot be null");
        if (!g.isWeighted()) throw new IllegalArgumentException("Graph must be weighted");

        this.distances = new HashMap<>();
        this.previous = new HashMap<>();
        this.graph = g;
    }

    private void dijkstraAlgo(Vertex<T> start) {

        //reset distances and previous
        distances = new HashMap<>();
        previous = new HashMap<>();

        PriorityQueue<QueueObject<T>> queue = new PriorityQueue<>();

        //add start vertex
        queue.add(new QueueObject<>(start, 0.0));

        //initialize distances and previous
        for (Vertex<T> vertex : graph.getVertices().values()) {
            distances.put(vertex.getData(), Double.POSITIVE_INFINITY);
            previous.put(vertex.getData(), null);
        }

        //set start distance to 0
        distances.put(start.getData(), 0.0);

        while (queue.size() != 0){
            Vertex<T> v = queue.poll().vertex();


            for (Edge<T> e : v.getEdges()){

                double alt = distances.get(v.getData()) + e.getWeight();
                Vertex<T> to = e.getTo();

                // nodes that are already visited would already be populated with the shortest path, so naturally,
                // the condition if (distanceThroughU < v.minDistance) will never be true for visited nodes.
                // so there is no need to check if a node is visited or not.

                if (alt < distances.get(to.getData())){
                    distances.put(to.getData(), alt);
                    previous.put(to.getData(), v);
                    queue.add(new QueueObject<>(to, distances.get(to.getData())));
                }
            }
        }
    }


    public LinkedList<Vertex<T>> getShortestPath(Vertex<T> start , Vertex<T> end){
        if (start == null || end == null) throw new IllegalArgumentException("Start and end vertices cannot be null");
        if (start.equals(end)) throw new IllegalArgumentException("Start and end vertices cannot be the same");

        dijkstraAlgo(start);

        LinkedList<Vertex<T>> path = new LinkedList<>();

        Vertex<T> current = end;
        while (current != null){
            path.addFirst(current);
            current = previous.get(current.getData());
        }


        if (path.getFirst().equals(start)) return path;
        else return null;
    }



}
