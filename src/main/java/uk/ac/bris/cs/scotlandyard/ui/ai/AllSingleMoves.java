package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

public class AllSingleMoves {
    private static AllSingleMoves allSingleMoves;

    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        if (allSingleMoves == null)
            allSingleMoves = new AllSingleMoves(graph);
    }

    public static AllSingleMoves getInstance() {
        Objects.requireNonNull(allSingleMoves);
        return allSingleMoves;
    }

    private final ImmutableMap<Integer, ImmutableSet<Move.SingleMove>> possibleMoves;

    private AllSingleMoves(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        possibleMoves = createPossibleMoves(graph);
    }

    private ImmutableMap<Integer, ImmutableSet<Move.SingleMove>> createPossibleMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        Map<Integer, ImmutableSet<Move.SingleMove>> possibleMoves = new HashMap<>();
        for (int location : graph.nodes())
            possibleMoves.put(location, generateMoves(graph, location));
        return ImmutableMap.copyOf(possibleMoves);
    }

    private ImmutableSet<Move.SingleMove> generateMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            int location
    ) {
        Set<Move.SingleMove> moves = new HashSet<>();
        for (int destination : graph.adjacentNodes(location)) {
            graph.edgeValue(location, destination).ifPresent(allTransport -> {
                for (ScotlandYard.Transport transport : allTransport)
                    moves.add(new Move.SingleMove(Piece.MrX.MRX, location, transport.requiredTicket(), destination));
                moves.add(new Move.SingleMove(Piece.MrX.MRX, location, ScotlandYard.Ticket.SECRET, destination));
            });
        }
        return ImmutableSet.copyOf(moves);
    }

    public ImmutableSet<Move.SingleMove> getSingleMoves(int location) {
        return possibleMoves.get(location);
    }
}
