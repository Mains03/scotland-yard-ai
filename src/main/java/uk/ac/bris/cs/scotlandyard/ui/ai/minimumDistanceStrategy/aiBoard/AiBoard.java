package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;

import javax.naming.OperationNotSupportedException;
import java.util.Set;

/**
 * Adapter for Board. Provides functionality for generating the MrX player.
 */
public interface AiBoard {
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph();

    /**
     * @deprecated Deprecated since {@link AiMove} is not useful, use {@link #getAvailableMovesNormal()}
     */
    @Deprecated
    ImmutableSet<AiMove> getAvailableMoves();

    Set<Move> getAvailableMovesNormal();

    AiPlayer getMrX();

    ImmutableList<AiPlayer> getDetectives();

    AiBoard applyMove(Move move);
}
