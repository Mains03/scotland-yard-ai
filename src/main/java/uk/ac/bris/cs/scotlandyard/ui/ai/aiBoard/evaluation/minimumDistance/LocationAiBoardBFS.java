package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;

import java.util.*;

/**
 * {@link MinimumDistanceAlgorithm}
 */
public class LocationAiBoardBFS extends BFS implements MinimumDistanceAlgorithm {
    private static LocationAiBoardBFS instance;

    public static LocationAiBoardBFS getInstance() {
        if (instance == null)
            instance = new LocationAiBoardBFS();
        return instance;
    }

    private LocationAiBoardBFS() {
        super();
    }

    @Override
    public int minimumDistance(AiBoard board, Piece piece) {
        if (board instanceof LocationAiBoard locationAiBoard) {
            Optional<Integer> mLocation = board.getDetectiveLocation((Piece.Detective) piece);
            if (mLocation.isEmpty())
                throw new NoSuchElementException("Failed to find " + piece + " location");
            return minimumDistance(locationAiBoard, mLocation.get());
        } else
            throw new IllegalArgumentException("Expected LocationAiBoard");
    }

    private int minimumDistance(LocationAiBoard board, int detectiveLocation) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(detectiveLocation);
        int[] distances = createAndInitialiseDistancesArray(board, detectiveLocation);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == board.mrXLocation)
                // we know this is the minimum distance since each edge has weight 1
                return distances[node];
            for (int adjacent : board.getSetup().graph.adjacentNodes(node)) {
                if (distances[adjacent] == INITIAL_DISTANCE_VAL) {
                    queue.add(adjacent);
                    distances[adjacent] = distances[node] + 1;
                }
            }
        }
        // no path found
        return POSITIVE_INFINITY;
    }

    private int[] createAndInitialiseDistancesArray(Board board, int source) {
        var graph = board.getSetup().graph;
        int numNodes = graph.nodes().size();
        // one more than the size since nodes are numbered from 1 not 0
        int[] distances = new int[numNodes + 1];
        // initialise distances
        Arrays.fill(distances, INITIAL_DISTANCE_VAL);
        distances[source] = 0;
        return distances;
    }
}
