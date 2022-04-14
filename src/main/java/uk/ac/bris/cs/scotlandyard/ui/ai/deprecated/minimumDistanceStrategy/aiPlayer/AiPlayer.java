package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiPlayer;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiMove.AiMove;

import java.util.List;
import java.util.Set;

/**
 * Adapter for {@link Player}
 */
public interface AiPlayer {
    int getLocation();

    /**
     * @deprecated Use {@link #getAvailableMoves()}
     */
    @Deprecated
    ImmutableSet<AiMove> getAvailableMoves();

    default Set<Move> getAvailableMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            List<Integer> detectiveLocations
    ) {
        throw new UnsupportedOperationException();
    }

    /**
     * @deprecated Use {@link #applyMove(Move)}
     */
    @Deprecated
    AiPlayer applyMove(AiMove move);

    default AiPlayer applyMove(Move move) {
        throw new UnsupportedOperationException();
    }

    Player asPlayer();
}
