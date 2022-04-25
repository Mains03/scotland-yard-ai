package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PlayerFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PlayerMoveAdvance;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.*;

/**
 * Breadth-first search {@link MinDistAlgorithm}.
 */
public class BFS implements MinDistAlgorithm {
    private static final int INITIAL_DISTANCE_VAL = -1;
    private static final int POSITIVE_INFINITY = 1000000;

    /**
     * Considers tickets.
     */
    @Override
    public int minimumDistance(StandardAiBoard board, Piece detective) {
        if (detective.isMrX())
            throw new IllegalArgumentException("Expected detective");
        Queue<Pair<Player, Integer>> queue = new ArrayDeque<>();
        Player player = createPlayer(board, detective);
        queue.add(new Pair<>(player, 0));
        while (queue.size() > 0) {
            Pair<Player, Integer> node = queue.poll();
            if (node.left().location() == board.mrX.location())
                // we know this is the minimum distance since each edge has weight 1
                return node.right();
            for (Move move : getAvailableMoves(board, node.left())) {
                Player newPlayer = PlayerMoveAdvance.getInstance().applyMove(node.left(), move);
                int newDist = node.right()+1;
                queue.add(new Pair<>(newPlayer, newDist));
            }
        }
        // no path found
        return POSITIVE_INFINITY;
    }

    private Set<Move> getAvailableMoves(StandardAiBoard board, Player player) {
        var graph = board.getSetup().graph;
        return DetectiveMoveFactory.getInstance().generateMoves(graph, player);
    }

    private Player createPlayer(StandardAiBoard board, Piece detective) {
        return PlayerFactory.getInstance().createPlayer(board, detective);
    }

    @Override
    public int minimumDistance(LocationAiBoard board, int detectiveLocation) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(detectiveLocation);
        int[] distances = createAndInitialiseDistancesArray(board, detectiveLocation);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == board.mrXLocation)
                // we know this is the minimum distance since each edge has weight 1
                return distances[node];
            for (int adjacent : board.getSetup().graph.adjacentNodes(node)) {
                if ((adjacent == board.mrXLocation) || (distances[adjacent] == INITIAL_DISTANCE_VAL)) {
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
