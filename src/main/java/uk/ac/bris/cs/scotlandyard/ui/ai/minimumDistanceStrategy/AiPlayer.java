package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;

/**
 * Adapter for Player
 */
public interface AiPlayer {
    int getLocation();

    ImmutableSet<AiMove> getAvailableMoves();

    AiPlayer applyMove(AiMove move);
}
