package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;

import java.util.List;
import java.util.Optional;

public class MemoizedMinimumDistanceStrategyProxy extends MinimumDistanceStrategy {
    // memoized minimum distances
    private final int minimumDistances[];

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
        return super.minimumDistanceBetweenMrXAndDetectives(mrX, detectives, strategy);
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
