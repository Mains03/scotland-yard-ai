package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

public interface BestMoveStrategy {
    Move determineBestMove(StandardAiBoard board);

    /**
     * Required to run code since game tree strategy has not been updated.
     * TODO: update game tree.
     */
    default Move determineBestMove(LocationAiBoard board) {
        return null;
    }
}
