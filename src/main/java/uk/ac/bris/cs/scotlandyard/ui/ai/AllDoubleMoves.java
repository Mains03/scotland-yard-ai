package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;
import java.util.stream.Collectors;

public class AllDoubleMoves {
    private static AllDoubleMoves allDoubleMoves;

    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        if (allDoubleMoves == null) {
            AllSingleMoves.createInstance(graph);
            allDoubleMoves = new AllDoubleMoves(graph);
        }
    }

    public static AllDoubleMoves getInstance() {
        Objects.requireNonNull(allDoubleMoves);
        return allDoubleMoves;
    }

    private final ImmutableMap<Integer, ImmutableSet<Move.DoubleMove>> possibleMoves;

    private AllDoubleMoves(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        possibleMoves = createPossibleMoves(graph);
    }

    private ImmutableMap<Integer, ImmutableSet<Move.DoubleMove>> createPossibleMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        Map<Integer, ImmutableSet<Move.DoubleMove>> possibleMoves = new HashMap<>();
        for (int location : graph.nodes())
            possibleMoves.put(location, generateMoves(graph, location));
        return ImmutableMap.copyOf(possibleMoves);
    }

    private ImmutableSet<Move.DoubleMove> generateMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            int location
    ) {
        return ImmutableSet.copyOf(
                AllSingleMoves.getInstance().getSingleMoves(location).stream()
                    .map(this::generateDoubleMoves)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet())
        );
    }

    private Collection<Move.DoubleMove> generateDoubleMoves(Move.SingleMove singleMove) {
        return AllSingleMoves.getInstance().getSingleMoves(singleMove.destination).stream()
                .map(move2 -> new Move.DoubleMove(
                        Piece.MrX.MRX, singleMove.source(), singleMove.ticket, singleMove.destination, move2.ticket, move2.destination
                )).collect(Collectors.toList());
    }

    public ImmutableSet<Move.DoubleMove> getDoubleMoves(int location) {
        return possibleMoves.get(location);
    }
}
