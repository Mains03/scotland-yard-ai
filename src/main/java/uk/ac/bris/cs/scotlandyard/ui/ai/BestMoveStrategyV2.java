package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

/**
 * Replacement for {@link BestMoveStrategy}.
 */
public interface BestMoveStrategyV2 {
    Move determineBestMove(Board board);
}
