package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.aiPlayerData;

import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiPlayer.AiPlayer;

public interface AiPlayerWithMoveFlag extends AiPlayer {
    void markMoved();

    void unmarkMoved();

    boolean hasMoved();
}
