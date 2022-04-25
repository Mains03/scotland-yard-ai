package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;

/**
 * {@link EvaluationStrategy} where the minimum distance between MrX
 * and the detectives is the evaluation of a board.
 */
public class MinimumDistanceEvaluation implements EvaluationStrategy {
    private static MinimumDistanceEvaluation instance;

    public static MinimumDistanceEvaluation getInstance() {
        if (instance == null)
            instance = new MinimumDistanceEvaluation();
        return instance;
    }

    private static final int POSITIVE_INFINITY = 1000000;

    private MinimumDistanceEvaluation() {}

    @Override
    public Integer visit(StandardAiBoard board) {
        int minDist = POSITIVE_INFINITY;
        for (Piece piece : board.getPlayers()) {
            if (piece.isDetective()) {
                int dist = BFS.getInstance().minimumDistance(board, piece);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }

    @Override
    public Integer visit(LocationAiBoard board) {
        int minDist = POSITIVE_INFINITY;
        for (int detectiveLocation : board.detectiveLocations) {
            int dist = BFS.getInstance().minimumDistance(board, detectiveLocation);
            minDist = Math.min(minDist, dist);
        }
        return minDist;
    }
}
