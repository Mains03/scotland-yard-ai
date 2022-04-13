package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiPlayer;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiMove.AiMove;

/**
 * Adapter for Player
 */
public interface AiPlayer {
    int getLocation();

    ImmutableSet<AiMove> getAvailableMoves();

    AiPlayer applyMove(AiMove move);

    Player asPlayer();
}
