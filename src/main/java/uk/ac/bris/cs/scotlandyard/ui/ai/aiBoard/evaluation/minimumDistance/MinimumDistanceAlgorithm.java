package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * Minimum distance between MrX and the specified detective.
 */
public interface MinimumDistanceAlgorithm {
    int minimumDistance(AiBoard board, Piece piece);
}
