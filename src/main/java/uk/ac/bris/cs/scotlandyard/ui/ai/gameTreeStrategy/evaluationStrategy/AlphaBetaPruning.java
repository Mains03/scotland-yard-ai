package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeDataStructure;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeEvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.NoSuchElementException;
import java.util.Objects;

public class AlphaBetaPruning implements GameTreeEvaluationStrategy {
    private static final int POSITIVE_INFINITY = 1000000;
    private static final int NEGATIVE_INFINITY = -1000000;

    private final StaticPositionEvaluationStrategy strategy;

    public AlphaBetaPruning(StaticPositionEvaluationStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public Move evaluateGameTree(GameTreeDataStructure gameTree) {
        Move bestMove = null;
        int bestMoveEvaluation = NEGATIVE_INFINITY;
        int alpha = NEGATIVE_INFINITY;
        int beta = POSITIVE_INFINITY;
        for (GameTreeDataStructure child : gameTree.getChildren()) {
            int eval = evaluateChild(child, alpha, beta, false);
            if (eval >= beta)
                break;
            alpha = Math.max(alpha, eval);
            if (eval > bestMoveEvaluation) {
                bestMove = child.getMove();
                bestMoveEvaluation = eval;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }

    private int evaluateChild(
            GameTreeDataStructure node,
            int alpha,
            int beta,
            boolean maximise
    ) {

    }
}
