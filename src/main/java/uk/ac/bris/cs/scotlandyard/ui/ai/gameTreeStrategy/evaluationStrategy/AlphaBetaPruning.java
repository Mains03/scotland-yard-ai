package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeDataStructure;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class AlphaBetaPruning extends GameTreeStrategy implements BestMoveStrategy {
    public AlphaBetaPruning(Board board, StaticPositionEvaluationStrategy evaluationStrategy) {
        super(board, evaluationStrategy);
    }

    @Override
    protected GameTreeDataStructure createChild(GameTreeBoard gameTreeBoard, int depth) {
        return new AlphaBetaGameTreeDataStructure(gameTreeBoard, depth);
    }

    @Override
    public Move determineBestMove() {
        Move bestMove = null;
        int bestMoveEval = GameTreeDataStructure.NEGATIVE_INFINITY;
        int alpha = GameTreeDataStructure.NEGATIVE_INFINITY;
        int beta = GameTreeDataStructure.POSITIVE_INFINITY;
        for (Move childMove : children.keySet()) {
            AlphaBetaGameTreeDataStructure child = (AlphaBetaGameTreeDataStructure) children.get(childMove);
            int childEval = child.evaluate(false, alpha, beta, evaluationStrategy);
            alpha = Math.max(alpha, childEval);
            if (childEval > bestMoveEval) {
                bestMove = childMove;
                bestMoveEval = childEval;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }
}
