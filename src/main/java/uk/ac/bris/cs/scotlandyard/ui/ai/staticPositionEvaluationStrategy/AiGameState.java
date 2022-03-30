package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;

/**
 * Adapter for the board.
 */
public interface AiGameState {
    Player getMrX();

    List<Player> getDetectives();
}
