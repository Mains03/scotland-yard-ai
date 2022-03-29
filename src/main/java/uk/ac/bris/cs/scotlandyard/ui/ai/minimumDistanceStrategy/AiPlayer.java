package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;

public interface AiPlayer {
    int getLocation();

    ImmutableSet<Move> getAvailableMoves();

    AiPlayer applyMove(Move move);
}
