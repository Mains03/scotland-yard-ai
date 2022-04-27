package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited.WinnerLimitedGameTree;

/**
 * {@link GameTreeEvaluationStrategy} using minimax algorithm.
 */
public class MinimaxEvaluation extends GameTreeEvaluationStrategy implements GameTree.Visitor<Move> {
    private static MinimaxEvaluation instance;

    public static MinimaxEvaluation getInstance() {
        if (instance == null)
            instance = new MinimaxEvaluation();
        return instance;
    }

    protected MinimaxEvaluation() {
    }

    @Override
    public Move evaluate(GameTree tree) {
        return tree.accept(this);
    }

    @Override
    public Move visit(DepthLimitedGameTree tree) {
        return MinimaxDepthLimitedEvaluation.getInstance().evaluate(tree);
    }

    @Override
    public Move visit(WinnerLimitedGameTree tree) {
        return null;
    }
}
