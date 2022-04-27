package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.MinimumDistanceEvaluation;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;

public abstract class GameTreeEvaluationStrategy {
    protected EvaluationStrategy strategy = MinimumDistanceEvaluation.getInstance();

    public abstract Move evaluate(GameTree tree);
}
