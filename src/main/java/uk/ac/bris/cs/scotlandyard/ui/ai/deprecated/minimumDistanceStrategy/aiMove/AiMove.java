package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

/**
 * Adapter for Move.
 *
 * @deprecated Deprecated since not useful, use {@link Move}
 */
@Deprecated
public interface AiMove {
    int getDestination();

    Iterable<ScotlandYard.Ticket> tickets();

    Move asMove();
}
