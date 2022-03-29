package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Performs a breadth first search to find the minimum distance where a
 * moves towards b. Considers the tickets available so is slower but more
 * accurate.
 */
public class BreadthFirstSearchWithTicket extends BreadthFirstSearch {
    public BreadthFirstSearchWithTicket(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        super(graph);
    }

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
        Queue<PQNode> queue = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.distance)
        );
        queue.add(new PQNode(a, 0));
        while (!queue.isEmpty()) {
            PQNode node = queue.poll();
            if (node.player.getLocation() == b.getLocation())
                return node.distance;
            for (Move move : node.player.getAvailableMoves()) {
                queue.add(new PQNode(
                        node.player.applyMove(move),
                        node.distance + 1
                ));
            }
        }
        return POSITIVE_INFINITY;
    }
}
