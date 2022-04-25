package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * Strategy to find the best move in a board.
 */
public interface BestMoveStrategy {
    Move bestMove(AiBoard board);
}
