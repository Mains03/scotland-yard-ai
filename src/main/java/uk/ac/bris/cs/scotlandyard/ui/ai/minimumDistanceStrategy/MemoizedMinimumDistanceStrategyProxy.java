package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;

import java.util.List;

/**
 * Proxy for MinimumDistanceStrategy which uses memoization for MrX locations. If the minimum distance
 * between the detectives and Mrx has already been calculated at a specific location, the distance is the
 * same even if Mrx used different moves to get to the location.
 */
public class MemoizedMinimumDistanceStrategyProxy extends MinimumDistanceStrategy {
    // memoized minimum distances
    private int minimumDistances[] = null;

    public MemoizedMinimumDistanceStrategyProxy(Board board) {
        super(board);
        minimumDistances = new int[board.getSetup().graph.nodes().size()+1];
        for (int i=0; i<minimumDistances.length; ++i)
            minimumDistances[i] = -1;
    }

    @Override
    protected int minimumDistanceBetweenMrXAndDetectives(
            AiPlayer mrX,
            List<AiPlayer> detectives,
            MinimumDistance strategy
    ) {
        // use memoization
        if (foundMinimumDistance(mrX.getLocation()))
            return getMinimumDistance(mrX.getLocation());
        else {
            int dist = super.minimumDistanceBetweenMrXAndDetectives(mrX, detectives, strategy);
            setMinimumDistance(mrX.getLocation(), dist);
            return dist;
        }
    }

    private boolean foundMinimumDistance(int node) {
        return minimumDistances[node] >= 0;
    }

    private int getMinimumDistance(int node) {
        return minimumDistances[node];
    }

    private void setMinimumDistance(int node, int distance) {
        minimumDistances[node] = distance;
    }
}
