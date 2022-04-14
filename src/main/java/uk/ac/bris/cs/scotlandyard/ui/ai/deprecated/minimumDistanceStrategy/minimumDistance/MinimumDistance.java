package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.minimumDistance;

import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiPlayer.AiPlayer;

/**
 * Strategy pattern to find the minimum distance between two players.
 */
public interface MinimumDistance {
    int minimumDistance(AiPlayer a, AiPlayer b);
}
