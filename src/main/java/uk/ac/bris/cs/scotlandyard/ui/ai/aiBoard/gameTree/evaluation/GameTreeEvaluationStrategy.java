package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;

public interface GameTreeEvaluationStrategy {
    Move evaluate(GameTree tree);
}
