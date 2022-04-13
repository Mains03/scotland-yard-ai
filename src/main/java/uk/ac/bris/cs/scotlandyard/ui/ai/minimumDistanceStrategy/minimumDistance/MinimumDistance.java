package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance;

import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiPlayer.AiPlayer;

/**
 * Strategy pattern to find the minimum distance between two players.
 */
public interface MinimumDistance {
    int minimumDistance(AiPlayer a, AiPlayer b);
}
