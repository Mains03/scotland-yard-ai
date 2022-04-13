package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

public interface GameTreeEvaluationStrategy {
    Move determineBestMove(GameTree gameTree);
}
