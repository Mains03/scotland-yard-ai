package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;

/**
 * Provides functionality for manipulating a player.
 *
 * @deprecated since generating available moves requires more information,
 * use {@link AiPlayerV2}.
 */
@Deprecated
public interface AiPlayer {
    int getLocation();

    ImmutableSet<AiMove> getAvailableMoves();

    AiPlayer applyMove(AiMove move);

    Player asPlayer();
}
