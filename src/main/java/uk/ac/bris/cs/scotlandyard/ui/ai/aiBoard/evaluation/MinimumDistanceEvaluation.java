package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance.BFS;

/**
 * {@link EvaluationStrategy} where the minimum distance between MrX
 * and the detectives is the evaluation of a board.
 */
public class MinimumDistanceEvaluation extends EvaluationStrategy {
    private static MinimumDistanceEvaluation instance;

    public static MinimumDistanceEvaluation getInstance() {
        if (instance == null)
            instance = new MinimumDistanceEvaluation();
        return instance;
    }

    private static final int POSITIVE_INFINITY = Integer.MAX_VALUE;

    private MinimumDistanceEvaluation() {}

    @Override
    public int evaluate(AiBoard board) {
        int minDist = POSITIVE_INFINITY;
        for (Piece piece : board.getPlayers()) {
            if (piece.isDetective()) {
                int dist = BFS.getInstance().minimumDistance(board, piece);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }
}
