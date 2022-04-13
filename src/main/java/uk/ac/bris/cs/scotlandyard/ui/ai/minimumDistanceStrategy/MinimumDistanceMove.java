package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

/**
 * Adapter for Move.
 */
public interface MinimumDistanceMove {
    int getDestination();

    Iterable<ScotlandYard.Ticket> tickets();

    Move asMove();
}
