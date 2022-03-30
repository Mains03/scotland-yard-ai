package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.MinimumDistance;

import java.util.List;

/**
 * Proxy for MinimumDistanceStrategy which uses memoization for MrX locations. If the minimum distance
 * between the detectives and Mrx has already been calculated at a specific location, the distance is the
 * same even if Mrx used different moves to get to the location.
 */
public class MemoizedMinimumDistanceStrategyProxy extends MinimumDistanceStrategy {
    // memoized minimum distances
    private final MinimumDistanceLookupTable minimumDistances;

    public MemoizedMinimumDistanceStrategyProxy(Board board) {
        super(board);
        minimumDistances = new MinimumDistanceLookupTable(board.getSetup().graph.nodes().size());
    }

    @Override
    protected int minimumDistanceBetweenMrXAndDetectives(
            AiPlayer mrX,
            List<AiPlayer> detectives,
            MinimumDistance strategy
    ) {
        // use memoization
        if (minimumDistances.foundMinimumDistance(mrX.getLocation()))
            return minimumDistances.getMinimumDistance(mrX.getLocation());
        else {
            int dist = super.minimumDistanceBetweenMrXAndDetectives(mrX, detectives, strategy);
            minimumDistances.setMinimumDistance(mrX.getLocation(), dist);
            return dist;
        }
    }
}
