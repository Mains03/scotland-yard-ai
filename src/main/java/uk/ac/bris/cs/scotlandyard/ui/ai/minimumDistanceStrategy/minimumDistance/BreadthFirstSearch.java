package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;

import java.util.*;

/**
 * Performs a breadth first search to find the minimum distance.
 * Doesn't consider tickets.
 */
public class BreadthFirstSearch implements MinimumDistance {
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
        // Creates a que with the source/player a location as first element
        Queue<Integer> priorityQueue = new ArrayDeque<>();
        priorityQueue.add(source);
        // Creates array of ints with space for each node in graph
        // Set each int to -1, source has distance 0 from itself
        int distance[] = new int[graph.nodes().size()+1];
        for (int i=0; i<distance.length; ++i)
            distance[i] = -1;
        distance[source] = 0;
        while (!priorityQueue.isEmpty()) {
            // pop first node
            int node = priorityQueue.poll();
            // if destination, return its distance, if not, look at adjacent nodes
            if (node == destination)
                return distance[node];
            /*
            If adjacent node visited, no change, if not visited adds it to queue
            and stores distance (distance to current node + 1)
            as new nodes are added to end of queue, order of queue is:
            nodes with dist 1, nodes with dist 2, etc. so always finds minimum distance
            */
            for (int adjacentNode : graph.adjacentNodes(node)) {
                if (distance[adjacentNode] < 0) {
                    priorityQueue.add(adjacentNode);
                    distance[adjacentNode] = distance[node] + 1;
                }
            }
        }
        // If no route found, has infinite distance
        return POSITIVE_INFINITY;
    }
}
