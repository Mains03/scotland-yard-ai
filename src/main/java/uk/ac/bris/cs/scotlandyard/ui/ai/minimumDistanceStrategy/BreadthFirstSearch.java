package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

/**
 * Performs a breadth first search to find the minimum distance.
 * Doesn't consider tickets.
 */
public class BreadthFirstSearch implements MinimumDistance{
    protected static final int POSITIVE_INFINITY = 10000000;

    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;

    public BreadthFirstSearch(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        this.graph = graph;
    }

    @Override
    public int minimumDistance(AiPlayer a, AiPlayer b) {
        int source = a.getLocation();
        int destination = b.getLocation();
        Queue<Integer> priorityQueue = new ArrayDeque<>();
        priorityQueue.add(source);
        int distance[] = new int[graph.nodes().size()+1];
        for (int i=0; i<distance.length; ++i)
            distance[i] = -1;
        distance[source] = 0;
        while (!priorityQueue.isEmpty()) {
            int node = priorityQueue.poll();
            if (node == destination)
                return distance[node];
            for (int adjacentNode : graph.adjacentNodes(node)) {
                if (distance[adjacentNode] < 0) {
                    priorityQueue.add(adjacentNode);
                    distance[adjacentNode] = distance[node] + 1;
                }
            }
        }
        return POSITIVE_INFINITY;
    }
}
