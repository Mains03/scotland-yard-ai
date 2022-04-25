package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

/**
 * Returns the minimum distance between MrX and a detective.
 */
public interface MinDistAlgorithm {
    int minimumDistance(StandardAiBoard board, Piece detective);

    int minimumDistance(LocationAiBoard board, int detectiveLocation);
}
