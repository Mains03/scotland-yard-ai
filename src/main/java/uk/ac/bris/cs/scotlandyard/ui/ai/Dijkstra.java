package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

public class Dijkstra {
    private final ImmutableValueGraph<Integer, ImmutableList<ScotlandYard.Transport>> graph;
    private final VisitedLocations visitedLocations;

    public Dijkstra(final ImmutableValueGraph<Integer, ImmutableList<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        this.graph = graph;
        visitedLocations = new VisitedLocations();
    }

    public Optional<Integer> minimumRouteLength(Player player, int destination) {
        return shortestPath(player, destination).map(List::size);
    }

    public Optional<List<Integer>> shortestPath(Player player, int destination) {
        PriorityQueue<PriorityQueueNode> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new PriorityQueueNode(player.location()));
        while (!priorityQueue.isEmpty()) {
            PriorityQueueNode priorityQueueNode = priorityQueue.poll();
            if (!visitedLocations.haveVisited(priorityQueueNode.getLocation())) {
                if (priorityQueueNode.getLocation() == destination) {
                    return Optional.of(priorityQueueNode.getPath());
                }
                visitedLocations.markVisited(priorityQueueNode.getLocation());
                for (int adjacent : graph.adjacentNodes(priorityQueueNode.getLocation())) {
                    Optional<ImmutableList<ScotlandYard.Transport>> allTransport =
                            graph.edgeValue(priorityQueueNode.getLocation(), adjacent);
                    if (allTransport.isPresent()) {
                        for (ScotlandYard.Transport transport : allTransport.get()) {
                            PriorityQueueNode newPriorityQueueNode = priorityQueueNode.move(adjacent, transport.requiredTicket());
                            if (playerHasRequiredTickets(player, newPriorityQueueNode.getRequiredTickets())) {
                                priorityQueue.add(priorityQueueNode.move(adjacent, transport.requiredTicket()));
                            }
                        }
                    }
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
    private final List<Integer> path;
    private final Map<ScotlandYard.Ticket, Integer> requiredTickets;

    public PriorityQueueNode(int location) {
        this.location = location;
        path = new ArrayList<>();
        path.add(location);
        requiredTickets = new HashMap<>();
    }

    private PriorityQueueNode(PriorityQueueNode previous, int destination, ScotlandYard.Ticket ticket) {
        location = destination;
        path = new ArrayList<>();
        path.add(destination);
        requiredTickets = new HashMap<>();
        for (ScotlandYard.Ticket requiredTicket : previous.requiredTickets.keySet()) {
            requireTickets(ticket, previous.requiredTickets.get(ticket));
        }
        requireTicket(ticket);
    }

    private void requireTicket(ScotlandYard.Ticket ticket) {
        requireTickets(ticket, 1);
    }

    private void requireTickets(ScotlandYard.Ticket ticket, int amount) {
        if (requiredTickets.containsKey(ticket)) {
            requiredTickets.put(ticket, requiredTickets.get(ticket)+amount);
        } else {
            requiredTickets.put(ticket, amount);
        }
    }

    public int getLocation() { return location; }
    public ImmutableList<Integer> getPath() { return ImmutableList.copyOf(path); }

    public ImmutableMap<ScotlandYard.Ticket, Integer> getRequiredTickets() {
        return ImmutableMap.copyOf(requiredTickets);
    }

    public PriorityQueueNode move(int destination, ScotlandYard.Ticket ticket) {
        return new PriorityQueueNode(this, destination, ticket);
    }
}

class VisitedLocations {
    private Set<Integer> visited;

    public VisitedLocations() {
        visited = new HashSet<>();
    }

    public void markVisited(int location) { visited.add(location); }
    public boolean haveVisited(int location) { return visited.contains(location); }
}
