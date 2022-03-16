package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

public class Dijkstra {
    private final Board board;
    private final VisitedLocations visitedLocations;

    public Dijkstra(final Board board) {
        Objects.requireNonNull(board);
        this.board = board;
        visitedLocations = new VisitedLocations();
    }

    public Optional<Integer> minimumRouteLength(Player player, int destination) {
        Optional<List<Integer>> path = shortestPath(player, destination);
        return path.map(List::size);
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
                for (int adjacent : board.getSetup().graph.adjacentNodes(priorityQueueNode.getLocation())) {
                    Optional<ImmutableSet<ScotlandYard.Transport>> allTransport =
                            board.getSetup().graph.edgeValue(priorityQueueNode.getLocation(), adjacent);
                    if (allTransport.isPresent()) {
                        for (ScotlandYard.Transport transport : allTransport.get()) {
                            priorityQueue.add(priorityQueueNode.move(adjacent, transport.requiredTicket()));
                        }
                    }
                }
            }
        }
        return Optional.empty();
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
