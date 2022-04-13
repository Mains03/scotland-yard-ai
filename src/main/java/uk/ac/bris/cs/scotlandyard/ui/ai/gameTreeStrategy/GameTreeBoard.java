package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;

/**
 * Adapter for Board used in GameTreeStrategy and GameTreeDataStructure.
 */
public interface GameTreeBoard {
    ImmutableSet<Move> getAvailableMoves();

    /**
     * Applies the move and returns the new state.
     * @param move the move
     * @return the new state
     */
    GameTreeBoard advance(Move move);

    /**
     * Converts the instance to an AiGameState instance. Required to
     * statically evaluate a position with a StaticPositionEvaluationStrategy.
     * @return the AiGameState instance
     */
    AiGameState asAiGameState();
}
