package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * Returns an integer evaluation of an AiGameState.
 */
public interface StaticPosEvalStrategy {
    int evaluate(AiBoard board);
}
