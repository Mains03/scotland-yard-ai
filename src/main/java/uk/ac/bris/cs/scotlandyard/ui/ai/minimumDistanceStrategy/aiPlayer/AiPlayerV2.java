package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;
import java.util.Set;

/**
 * Adapter for {@link Player} providing more functionality.
 */
public interface AiPlayerV2 {
    Set<Move> getAvailableMoves(List<Integer> detectiveLocations);

    AiPlayerV2 applyMove(Move move);

    Player asPlayer();
}
