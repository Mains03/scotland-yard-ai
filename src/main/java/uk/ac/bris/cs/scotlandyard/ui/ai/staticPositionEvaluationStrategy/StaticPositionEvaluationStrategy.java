package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

/**
 * Returns an integer evaluation of an AiGameState.
 */
public interface StaticPositionEvaluationStrategy {
    int evaluate(AiGameState gameState);
}
