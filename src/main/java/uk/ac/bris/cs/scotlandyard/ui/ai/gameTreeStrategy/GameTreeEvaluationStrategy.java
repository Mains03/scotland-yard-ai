package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

/**
 * Strategy to determine the best move in a game tree.
 */
public interface GameTreeEvaluationStrategy {
    Move evaluateGameTree(GameTreeDataStructure gameTree);
}
