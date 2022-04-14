package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;
import java.util.Set;

/**
 * Singleton class to generate moves for a player.
 */
public class MoveGenerationFactory {
    private static MoveGenerationFactory moveGenerationFactory;

    public static MoveGenerationFactory getInstance() {
        if (moveGenerationFactory == null)
            moveGenerationFactory = new MoveGenerationFactory();
        return moveGenerationFactory;
    }

    private MoveGenerationFactory() {}

    public Set<Move> generateMoves(List<Integer> playerLocations, Player player) {
        return null;
    }
}
