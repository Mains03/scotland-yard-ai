package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

/**
 * Adapter for Move.
 */
public interface AiMove {
    int getDestination();

    Iterable<ScotlandYard.Ticket> tickets();

    Move asMove();
}
