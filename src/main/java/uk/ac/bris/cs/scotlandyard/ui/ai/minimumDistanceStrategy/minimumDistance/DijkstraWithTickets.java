package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Performs a breadth first search to find the minimum distance where a
 * moves towards b. Considers the tickets available so is slower but more
 * accurate.
 */
public class DijkstraWithTickets implements MinimumDistance {
    private static final int POSITIVE_INFINITY = 10000000;

    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;

    public DijkstraWithTickets(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        this.graph = graph;
    }

    // Node used in dijkstra, contains an AiPlayer and its distance to source
    private class PQNode {
        private final AiPlayer player;
        private final int distance;

        private PQNode(AiPlayer player, int distance) {
            this.player = player;
            this.distance = distance;
        }
    }

    @Override
    public int minimumDistance(AiPlayer a, AiPlayer b) {
        // Creates a priority queue with nodes ordered by distance
        Queue<PQNode> queue = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.distance)
        );
        // Source is first node
        queue.add(new PQNode(a, 0));
        /*
        While there are available moves, if current location is destination, return that distance.
        If not, look at all available moves from this location, if these locations have not been found,
        add them.
         */
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
}
