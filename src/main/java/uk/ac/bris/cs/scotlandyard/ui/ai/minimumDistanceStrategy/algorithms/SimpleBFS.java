package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.algorithms;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinDistStrategy;

import java.util.*;

/**
 * Ignores tickets for efficiency.
 */
public class SimpleBFS implements MinDistStrategy {
    private static final int INITIAL_DISTANCE_VAL = -1;
    private static final int POSITIVE_INFINITY = 1000000;

    @Override
    public int getMinimumDistance(AiBoard board, Piece a, Piece b) {
        int source = getPieceLocation(board, a);
        int destination = getPieceLocation(board, b);
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
            location = getMrXLocation(board);
        else
            location = getDetectiveLocation(board, piece);
        return location;
    }

    private int getMrXLocation(AiBoard board) {
        AiPlayer player = board.getMrX();
        return playerLocation(player);
    }

    private int getDetectiveLocation(AiBoard board, Piece piece) {
        int location = -1;
        List<AiPlayer> detectives = board.getDetectives();
        for (AiPlayer detective : detectives) {
            if (isPiece(detective, piece))
                location = playerLocation(detective);
        }
        if (location == -1)
            throw new NoSuchElementException("Piece " + piece + " not found");
        return location;
    }

    private boolean isPiece(AiPlayer aiPlayer, Piece piece) {
        Player player = aiPlayer.asPlayer();
        Piece playerPiece = player.piece();
        return playerPiece.equals(piece);
    }

    private int playerLocation(AiPlayer aiPlayer) {
        Player player = aiPlayer.asPlayer();
        return player.location();
    }

    private int[] createDistancesArray(AiBoard board) {
        var graph = board.getGraph();
        int numNodes = graph.nodes().size();
        // one more than the size since nodes are numbered from 1 not 0
        return new int[numNodes + 1];
    }

    private void initialiseDistances(int[] distances, int source) {
        Arrays.fill(distances, INITIAL_DISTANCE_VAL);
        distances[source] = 0;
    }

    private Iterable<Integer> getAdjacent(AiBoard board, int node) {
        return board.getGraph().adjacentNodes(node);
    }

    private boolean visited(int[] distances, int location) {
        return distances[location] != INITIAL_DISTANCE_VAL;
    }

    private void updateDistance(int[] distances, int source, int destination) {
        // each edge has weight 1
        distances[destination] = distances[source] + 1;
    }
}
