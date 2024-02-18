package com.example.jenproalgo;

import com.example.jenproalgo.graph.Vertex;

public record QueueObject<T>(Vertex<T> vertex, Double priority) implements Comparable<QueueObject<T>> {
    @Override
    public int compareTo(QueueObject<T> other) {
        return this.priority.compareTo(other.priority());
    }
}