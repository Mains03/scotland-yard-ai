package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Set;

public interface GameTreePlayer {
    Set<Move> getAvailableMoves();

    void markMoved();

    void unmarkMoved();

    boolean hasMoved();
}
