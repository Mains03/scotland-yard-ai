package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinimumDistanceLookupTable;

import java.util.Objects;

/**
 * Stores the minimum distances found at nodes to player b for memoization. If player b's
 * location changes, the distances are cleared.
 */
public class MemoizedMinimumDistance extends MinimumDistanceLookupTable implements MinimumDistanceAlgorithmStrategy {
    private final MinimumDistanceAlgorithmStrategy algorithm;

    private int playerBLocation;

    public MemoizedMinimumDistance(int nodeCount, MinimumDistanceAlgorithmStrategy algorithm) {
        super(nodeCount);
        Objects.requireNonNull(algorithm);
        this.algorithm = algorithm;
        playerBLocation = -1;
    }

    @Override
    public int minimumDistance(Player a, Player b) {
        if (b.location() != playerBLocation) {
            clearDistances();
            playerBLocation = b.location();
        }
        if (foundMinimumDistance(a.location()))
            return getMinimumDistance(a.location());
        else {
            int minDist = algorithm.minimumDistance(a, b);
            setMinimumDistance(a.location(), minDist);
            return minDist;
        }
    }
}
