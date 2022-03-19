package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

public class Dijkstra {
    private static Dijkstra dijkstra;

    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        dijkstra = new Dijkstra(graph);
    }

    public static Dijkstra getInstance() {
        Objects.requireNonNull(dijkstra);
        return dijkstra;
    }

    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;

    private Dijkstra(final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        this.graph = graph;
    }

    public Optional<Integer> minimumRouteLength(Player player, int destination) {
        Objects.requireNonNull(player);
        PriorityQueue<PriorityQueueNode> pq = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.getPathLength())
        );
        pq.add(new PriorityQueueNode(player.location()));
        Set<Integer> visited = new HashSet<>();
        while (!pq.isEmpty()) {
            PriorityQueueNode node = pq.poll();
            if (!visited.contains(node.getLocation())) {
                if (node.getLocation() == destination)
                    return Optional.of(node.getPathLength());
                visited.add(node.getLocation());
                for (int adjacent : graph.adjacentNodes(node.getLocation())) {
                    graph.edgeValue(node.getLocation(), adjacent).ifPresent(allTransport -> {
                        for (ScotlandYard.Transport transport : allTransport) {
                            PriorityQueueNode newNode = node.move(adjacent, transport.requiredTicket());
                            if (playerHasRequiredTickets(player, newNode.getRequiredTickets())) {
                                pq.add(newNode);
                            }
                        }
                    });
                }
            }
        }
        return Optional.empty();
    }

    private boolean playerHasRequiredTickets(Player player, Map<ScotlandYard.Ticket, Integer> requiredTickets) {
        for (ScotlandYard.Ticket ticket : requiredTickets.keySet()) {
            if (!player.hasAtLeast(ticket, requiredTickets.get(ticket))) return false;
        }
        return true;
    }
}

class PriorityQueueNode {
    private final int location;
    private final int pathLength;
    private final Map<ScotlandYard.Ticket, Integer> requiredTickets;

    public PriorityQueueNode(int location) {
        this.location = location;
        pathLength = 0;
        requiredTickets = new HashMap<>();
    }

    private PriorityQueueNode(PriorityQueueNode previous, int destination, ScotlandYard.Ticket ticket) {
        location = destination;
        pathLength = previous.pathLength + 1;
        requiredTickets = new HashMap<>();
        for (ScotlandYard.Ticket requiredTicket : previous.requiredTickets.keySet()) {
            requireTickets(requiredTicket, previous.requiredTickets.get(requiredTicket));
        }
        requireTicket(ticket);
    }

    private void requireTicket(ScotlandYard.Ticket ticket) {
        requireTickets(ticket, 1);
    }

    private void requireTickets(ScotlandYard.Ticket ticket, int count) {
        if (requiredTickets.containsKey(ticket)) {
            requiredTickets.put(ticket, requiredTickets.get(ticket) + count);
        } else {
            requiredTickets.put(ticket, count);
        }
    }

    public int getLocation() { return location; }

    public int getPathLength() {
        return pathLength;
    }

    public ImmutableMap<ScotlandYard.Ticket, Integer> getRequiredTickets() {
        return ImmutableMap.copyOf(requiredTickets);
    }

    public PriorityQueueNode move(int destination, ScotlandYard.Ticket ticket) {
        return new PriorityQueueNode(this, destination, ticket);
    }
}
