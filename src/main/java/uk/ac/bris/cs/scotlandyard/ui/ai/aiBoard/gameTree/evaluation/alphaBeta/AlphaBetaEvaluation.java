package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.alphaBeta;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited.DepthLimitedGameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.GameTreeEvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited.WinnerLimitedGameTree;

/**
 * Not finished.
 */
public class AlphaBetaEvaluation extends GameTreeEvaluationStrategy implements GameTree.Visitor<Move> {
    private static AlphaBetaEvaluation instance;

    public static AlphaBetaEvaluation getInstance() {
        if (instance == null)
            instance = new AlphaBetaEvaluation();
        return instance;
    }

    private AlphaBetaEvaluation() {}

    @Override
    public Move evaluate(GameTree tree) {
        return tree.accept(this);
    }

    @Override
    public Move visit(DepthLimitedGameTree tree) {
        return null;
    }

    @Override
    public Move visit(WinnerLimitedGameTree tree) {
        return null;
    }
}
