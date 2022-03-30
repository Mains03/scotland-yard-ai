package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

/**
 * Adapter for Board.
 */
public interface AiBoard {
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph();

    ImmutableSet<AiMove> getAvailableMoves();

    AiPlayer getMrX();

    ImmutableList<AiPlayer> getDetectives();
}
