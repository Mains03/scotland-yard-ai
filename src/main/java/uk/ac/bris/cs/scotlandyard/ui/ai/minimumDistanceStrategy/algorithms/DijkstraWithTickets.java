package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.algorithms;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinDistStrategy;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Performs a breadth first search to find the minimum distance where a
 * moves towards b. Considers the tickets available so is slower but more
 * accurate.
 */
public class DijkstraWithTickets implements MinDistStrategy {
    private static final int POSITIVE_INFINITY = 10000000;

    // used in dijkstra
    private class PQNode {
        private final Player player;
        private final int distance;

        private PQNode(Player player, int distance) {
            this.player = player;
            this.distance = distance;
        }
    }

    @Override
    public int getMinimumDistance(AiBoard board, Piece a, Piece b) {
        Queue<PQNode> queue = createPriorityQueue();
        queue.add(new PQNode(a, 0));
        while (!queue.isEmpty()) {
            PQNode node = queue.poll();
            if (node.player.getLocation() == b.getLocation())
                return node.distance;
            for (AiMove move : node.player.getAvailableMoves()) {
                queue.add(new PQNode(
                        node.player.applyMove(move),
                        node.distance + 1
                ));
            }
        }
        // If no route exists, distance is infinite
        return POSITIVE_INFINITY;
    }

    private PriorityQueue<PQNode> createPriorityQueue() {
        // compare nodes by distance
        Comparator<PQNode> comparator = Comparator.comparingInt(node -> node.distance);
        return new PriorityQueue<>(comparator);
    }
}
