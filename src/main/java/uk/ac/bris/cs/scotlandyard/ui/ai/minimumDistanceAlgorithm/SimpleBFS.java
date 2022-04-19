package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Breadth-first search algorithm to find the minimum distance between two nodes ignoring tickets.
 */
public class SimpleBFS implements MinDistStrategy {
    private static final int INITIAL_DISTANCE_VAL = -1;
    private static final int POSITIVE_INFINITY = 1000000;

    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final int source;
    private final int destination;

    public SimpleBFS(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            int source,
            int destination
    ) {
        this.graph = graph;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public int getMinimumDistance() {
        Queue<Integer> pq = new ArrayDeque<>();
        pq.add(source);
        int[] distances = createDistancesArray();
        initialiseDistances(distances);
        while (!pq.isEmpty()) {
            int node = pq.poll();
            if (node == destination)
                // we know this is the minimum distance since each edge has weight 1
                return distances[node];
            for (int adjacent : getAdjacent(node)) {
                if (!visited(distances, adjacent)) {
                    pq.add(adjacent);
                    updateDistance(distances, node, adjacent);
                }
            }
        }
        return POSITIVE_INFINITY;
    }

    private int[] createDistancesArray() {
        // one more than the size since nodes are numbered from 1 not 0
        return new int[graph.nodes().size()+1];
    }

    private void initialiseDistances(int[] distances) {
        Arrays.fill(distances, INITIAL_DISTANCE_VAL);
        distances[0] = 0;
    }

    private Iterable<Integer> getAdjacent(int node) {
        return graph.adjacentNodes(node);
    }

    private boolean visited(int[] distances, int location) {
        return distances[location] != INITIAL_DISTANCE_VAL;
    }

    private void updateDistance(int[] distances, int source, int destination) {
        distances[destination] = distances[source] + 1;
    }
}
