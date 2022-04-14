package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceAlgorithm;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiPlayer.AiPlayerAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.minimumDistance.BreadthFirstSearch;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.minimumDistance.MinimumDistance;

/**
 * @deprecated Deprecated since uses {@link AiPlayerAdapter} which is deprecated.
 */
@Deprecated
public class BreadthFirstSearchMinimumDistance implements MinimumDistanceAlgorithmStrategy {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final MinimumDistance algorithm;

    public BreadthFirstSearchMinimumDistance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        this.graph = graph;
        // use BreadthFirstSearch
        algorithm = new BreadthFirstSearch(graph);
    }

    @Override
    public int minimumDistance(Player a, Player b) {
        return algorithm.minimumDistance(
                new AiPlayerAdapter(graph, a),
                new AiPlayerAdapter(graph, b)
        );
    }
}
