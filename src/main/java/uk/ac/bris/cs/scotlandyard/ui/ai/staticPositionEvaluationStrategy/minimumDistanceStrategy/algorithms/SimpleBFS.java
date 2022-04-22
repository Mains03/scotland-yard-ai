package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy.MinDistAlgorithm;

import java.util.*;

/**
 * Ignores tickets for efficiency.
 */
public class SimpleBFS implements MinDistAlgorithm {
    private static final int INITIAL_DISTANCE_VAL = -1;
    private static final int POSITIVE_INFINITY = 1000000;

    @Override
    public int getMinimumDistance(AiBoard board, Piece a, Piece b) {
        int source = getPieceLocation(board, a);
        int destination = getPieceLocation(board, b);
        return minimumDistance(board, source, destination);
    }

    protected int minimumDistance(AiBoard board, int source, int destination) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        int[] distances = createDistancesArray(board);
        initialiseDistances(distances, source);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == destination)
                // we know this is the minimum distance since each edge has weight 1
                return distances[node];
            for (int adjacent : getAdjacent(board, node)) {
                if (!visited(distances, adjacent)) {
                    queue.add(adjacent);
                    updateDistance(distances, node, adjacent);
                }
            }
        }
        return POSITIVE_INFINITY;
    }

    private int getPieceLocation(AiBoard board, Piece piece) {
        int location;
        if (piece.isMrX())
            location = board.mrX.location();
        else
            location = getDetectiveLocation(board, (Piece.Detective) piece);
        return location;
    }

    private int getDetectiveLocation(AiBoard board, Piece.Detective piece) {
        Optional<Integer> location = board.getDetectiveLocation(piece);
        if (location.isEmpty())
            throw new NoSuchElementException("Could not find " + piece + " location");
        return location.get();
    }

    private int[] createDistancesArray(AiBoard board) {
        var graph = board.getSetup().graph;
        int numNodes = graph.nodes().size();
        // one more than the size since nodes are numbered from 1 not 0
        return new int[numNodes + 1];
    }

    private void initialiseDistances(int[] distances, int source) {
        Arrays.fill(distances, INITIAL_DISTANCE_VAL);
        distances[source] = 0;
    }

    private Iterable<Integer> getAdjacent(AiBoard board, int node) {
        return board.getSetup().graph.adjacentNodes(node);
    }

    private boolean visited(int[] distances, int location) {
        return distances[location] != INITIAL_DISTANCE_VAL;
    }

    private void updateDistance(int[] distances, int source, int destination) {
        // each edge has weight 1
        distances[destination] = distances[source] + 1;
    }
}
