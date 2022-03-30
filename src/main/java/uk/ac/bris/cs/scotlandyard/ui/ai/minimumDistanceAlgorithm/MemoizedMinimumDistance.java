package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinimumDistanceLookupTable;

import java.util.Objects;

public class MemoizedMinimumDistance extends MinimumDistanceLookupTable implements MinimumDistanceAlgorithmStrategy {
    private final MinimumDistanceAlgorithmStrategy algorithm;

    public MemoizedMinimumDistance(int nodeCount, MinimumDistanceAlgorithmStrategy algorithm) {
        super(nodeCount);
        Objects.requireNonNull(algorithm);
        this.algorithm = algorithm;
    }

    @Override
    public int minimumDistance(Player a, Player b) {
        if (foundMinimumDistance(a.location()))
            return getMinimumDistance(a.location());
        else {
            int minDist = algorithm.minimumDistance(a, b);
            setMinimumDistance(a.location(), minDist);
            return minDist;
        }
    }
}
