package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Move;

/**
 * Strategy pattern to find the best move.
 */
public interface BestMoveStrategy {
    Move determineBestMove();
}
