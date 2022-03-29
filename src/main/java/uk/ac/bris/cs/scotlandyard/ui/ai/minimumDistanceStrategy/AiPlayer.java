package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

public interface AiPlayer {
    int getLocation();

    AiPlayer applyMove(Move move);
}
