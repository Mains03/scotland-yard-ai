package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinDistStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.SimpleBFS;
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
        MinDistStrategy strategy = new SimpleBFS(graph, source, destination);
        return strategy.getMinimumDistance();
    }
}
