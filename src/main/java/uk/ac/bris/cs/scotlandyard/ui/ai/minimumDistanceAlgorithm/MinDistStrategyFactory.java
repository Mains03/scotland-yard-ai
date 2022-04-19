package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

/**
 * Simplifies {@link MinDistStrategy} instantiations.
 */
public class MinDistStrategyFactory {
    private final Board board;

    public MinDistStrategyFactory(Board board) {
        this.board = board;
    }

    public MinDistStrategy<Pair<Integer, Integer>> createSimpleBFSStrategy() {
        var graph = getGraph();
        return new SimpleBFS(graph);
    }

    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph() {
        return board.getSetup().graph;
    }
}
