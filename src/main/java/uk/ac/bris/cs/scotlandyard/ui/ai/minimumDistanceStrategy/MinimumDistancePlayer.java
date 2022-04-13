package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Player;

/**
 * Adapter for Player
 */
public interface MinimumDistancePlayer {
    int getLocation();

    ImmutableSet<MinimumDistanceMove> getAvailableMoves();

    MinimumDistancePlayer applyMove(MinimumDistanceMove move);

    Player asPlayer();
}
