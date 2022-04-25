package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.StaticPosEvalStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms.MinDistAlgorithm;

import java.util.Objects;

/**
 * {@link StaticPosEvalStrategy} which returns the minimum distance between MrX
 * and the detectives as the evaluation of a board.
 */
public class MinDistStaticPosEval implements StaticPosEvalStrategy {
    private static final int POSITIVE_INFINITY = 1000000;

    private final MinDistAlgorithm strategy;

    public MinDistStaticPosEval(MinDistAlgorithm strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public int evaluate(StandardAiBoard board) {
        int minDist = POSITIVE_INFINITY;
        for (Piece piece : board.getPlayers()) {
            if (piece.isDetective()) {
                int dist = strategy.minimumDistance(board, piece);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }

    @Override
    public int evaluate(LocationAiBoard board) {
        int minDist = POSITIVE_INFINITY;
        for (int detectiveLocation : board.detectiveLocations) {
            int dist = strategy.minimumDistance(board, detectiveLocation);
            minDist = Math.min(minDist, dist);
        }
        return minDist;
    }
}
