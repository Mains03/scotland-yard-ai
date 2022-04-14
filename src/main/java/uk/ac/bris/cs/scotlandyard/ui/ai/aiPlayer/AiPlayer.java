package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;

import java.util.Set;

/**
 * Adapter for Player
 */
public interface AiPlayer {
    int getLocation();

    /**
     * @deprecated Use {@link #getAvailableMovesNormal()}
     */
    @Deprecated
    ImmutableSet<AiMove> getAvailableMoves();

    default Set<Move> getAvailableMoves(Board board) {
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
