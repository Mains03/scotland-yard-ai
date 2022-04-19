package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;

/**
 * Adapter for Board. Provides functionality for generating the MrX player.
 *
 * @deprecated since {@link AiPlayer} is deprecated, use {@link AiBoardV2}.
 */
@Deprecated
public interface AiBoard {
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph();

    ImmutableSet<AiMove> getAvailableMoves();

    AiPlayer getMrX();

    ImmutableList<AiPlayer> getDetectives();

    AiBoard applyMove(Move move);
}
