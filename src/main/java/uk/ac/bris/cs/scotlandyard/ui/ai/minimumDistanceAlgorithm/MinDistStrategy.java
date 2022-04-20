package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;

public interface MinDistStrategy {
    int getMinimumDistance(AiBoard board, Piece a, Piece b);
}
