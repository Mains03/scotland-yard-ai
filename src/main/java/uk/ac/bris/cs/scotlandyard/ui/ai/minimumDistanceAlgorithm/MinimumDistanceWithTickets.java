package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiPlayer.AiPlayerAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.DijkstraWithTickets;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.MinimumDistance;

public class MinimumDistanceWithTickets implements MinimumDistanceAlgorithmStrategy {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final MinimumDistance algorithm;

    public MinimumDistanceWithTickets(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        this.graph = graph;
        algorithm = new DijkstraWithTickets(graph);
    }

    @Override
    public int minimumDistance(Player a, Player b) {
        return algorithm.minimumDistance(
                new AiPlayerAdapter(graph, a),
                new AiPlayerAdapter(graph, b)
        );
    }
}
