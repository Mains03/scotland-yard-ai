package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

/**
 * Adapter for Player
 */
public interface AiPlayer {
    int getLocation();

    ImmutableSet<Move> getAvailableMoves();

    AiPlayer applyMove(Move move);

    Player asPlayer();
}
