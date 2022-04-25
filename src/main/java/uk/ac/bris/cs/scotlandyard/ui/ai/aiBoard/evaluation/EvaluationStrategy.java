package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * {@link AiBoard} visitor returning an integer evaluation.
 */
public interface EvaluationStrategy extends AiBoard.Visitor<Integer> {
}
