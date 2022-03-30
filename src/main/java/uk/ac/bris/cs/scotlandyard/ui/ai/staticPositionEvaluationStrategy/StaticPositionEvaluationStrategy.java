package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;

public interface StaticPositionEvaluationStrategy {
    int evaluate(AiGameState gameState);
}
