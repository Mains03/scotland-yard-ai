package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

/**
 * {@link AiBoard} visitor returning an integer evaluation.
 */
public interface StaticPosEvalStrategy {
    int evaluate(StandardAiBoard board);

    int evaluate(LocationAiBoard board);
}
