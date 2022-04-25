package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

public interface AiBoard extends Board.GameState {
    /**
     * Accepts a {@link StaticPosEvalStrategy} visitor returning an integer evaluation.
     */
    int score(StaticPosEvalStrategy strategy);
}
