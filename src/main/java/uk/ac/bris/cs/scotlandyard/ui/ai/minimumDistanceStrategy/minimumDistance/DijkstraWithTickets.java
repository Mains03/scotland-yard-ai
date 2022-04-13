package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinimumDistanceMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinimumDistancePlayer;

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

    private class PQNode {
        private final MinimumDistancePlayer player;
        private final int distance;

        private PQNode(MinimumDistancePlayer player, int distance) {
            this.player = player;
            this.distance = distance;
        }
    }

    @Override
    public int minimumDistance(MinimumDistancePlayer a, MinimumDistancePlayer b) {
        Queue<PQNode> queue = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.distance)
        );
        queue.add(new PQNode(a, 0));
        while (!queue.isEmpty()) {
            PQNode node = queue.poll();
            if (node.player.getLocation() == b.getLocation())
                return node.distance;
            for (MinimumDistanceMove move : node.player.getAvailableMoves()) {
                queue.add(new PQNode(
                        node.player.applyMove(move),
                        node.distance + 1
                ));
            }
        }
        return POSITIVE_INFINITY;
    }
}
