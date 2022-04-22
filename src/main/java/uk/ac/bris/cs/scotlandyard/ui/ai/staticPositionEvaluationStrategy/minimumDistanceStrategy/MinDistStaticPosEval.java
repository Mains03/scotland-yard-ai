package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.Objects;

/**
 * Evaluation is minimum distance between MrX and the detectives.
 */
public class MinDistStaticPosEval implements StaticPosEvalStrategy {
    private static final int POSITIVE_INFINITY = 1000000;

    private final MinDistAlgorithm strategy;

    public MinDistStaticPosEval(MinDistAlgorithm strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public int evaluate(AiBoard board) {
        int minDist = POSITIVE_INFINITY;
        for (Piece piece : board.getPlayers()) {
            if (piece.isDetective()) {
                int dist = minDistToMrX(board, piece);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }

    // minimum distance between MrX and piece
    private int minDistToMrX(AiBoard board, Piece piece) {
        Piece mrX = Piece.MrX.MRX;
        return strategy.getMinimumDistance(board, mrX, piece);
    }
}
