package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

import java.util.Objects;

public class MemoizedSimpleBFS extends SimpleBFS {
    private int[][] memoizedDistances;

    public MemoizedSimpleBFS(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        memoizedDistances = createMemoizedDistances(Objects.requireNonNull(graph));
        initialiseDistances(memoizedDistances);
    }

    private int[][] createMemoizedDistances(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        int numNodes = graph.nodes().size();
        return new int[numNodes+1][numNodes+1];
    }

    private void initialiseDistances(int[][] distances) {
        for (int i=0; i<distances.length; ++i) {
            for (int j=0; j<distances.length; ++j)
                distances[i][j] = -1;
        }
    }

    @Override
    protected int minimumDistance(AiBoard board, int source, int destination) {
        int minimumDistance;
        if (memoizedDistances[source][destination] != -1)
            minimumDistance = memoizedDistances[source][destination];
        else {
            minimumDistance = super.minimumDistance(board, source, destination);
            memoizedDistances[source][destination] = minimumDistance;
            memoizedDistances[destination][source] = minimumDistance;
        }
        return minimumDistance;
    }
}
