package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;

public interface GameTreeEvaluationStrategy {
    int evaluate(GameTree tree);
}
