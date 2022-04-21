package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

public interface MinDistAlgorithm {
    int getMinimumDistance(AiBoard board, Piece a, Piece b);
}
