package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiPlayer.AiPlayer;

/**
 * Adapter for Board. Provides functionality for generating the MrX player.
 *
 * @deprecated Deprecated since not useful, use {@link AiPlayer} for all functionality
 */
@Deprecated
public interface AiBoard {
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph();

    ImmutableSet<AiMove> getAvailableMoves();

    AiPlayer getMrX();

    ImmutableList<AiPlayer> getDetectives();
}
