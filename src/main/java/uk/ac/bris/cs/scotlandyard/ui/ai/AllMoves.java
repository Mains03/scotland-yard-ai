package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import javax.annotation.concurrent.Immutable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllMoves {
    private static AllMoves allMoves;

    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        if (allMoves != null)
            allMoves = new AllMoves(graph);
    }

    public static AllMoves getInstance() {
        Objects.requireNonNull(allMoves);
        return allMoves;
    }

    private final ImmutableMap<
            Piece,
            ImmutableList<ImmutableSet<Move>>
            > availableMoves;

    private AllMoves(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        availableMoves = createAvailableMoves(graph);
    }

    private ImmutableMap<Piece, ImmutableList<ImmutableSet<Move>>> createAvailableMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        Map<Piece, ImmutableList<ImmutableSet<Move>>> moves = new HashMap<>();
        moves.put(Piece.MrX.MRX, createMrXMoves(graph));
        moves.putAll(createDetectiveMoves(graph));
        return ImmutableMap.copyOf(moves);
    }

    private ImmutableList<ImmutableSet<Move>> createMrXMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        return ImmutableList.copyOf(
                graph.nodes().stream()
                        .map(location -> createMrXMovesAtLocation(graph, location))
                        .collect(Collectors.toList())
        );
    }

    private ImmutableSet<Move> createMrXMovesAtLocation(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            int location
    ) {
        return ImmutableSet.copyOf(
                Stream.concat(
                    createSingleMovesAtLocation(graph, location, Piece.MrX.MRX),
                    createDoubleMovesAtLocation(graph, location)
                ).collect(Collectors.toList())
        );
    }

    private Map<Piece.Detective, ImmutableList<ImmutableSet<Move>>> createDetectiveMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        Map<Piece.Detective, ImmutableList<ImmutableSet<Move>>> moves = new HashMap<>();
        for (Piece.Detective detective : Piece.Detective.values())
            moves.put(detective, createDetectiveMoves(graph, detective));
        return moves;
    }

    private ImmutableList<ImmutableSet<Move>> createDetectiveMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            Piece.Detective detective
    ) {
        return ImmutableList.copyOf(
                graph.nodes().stream()
                    .map(location -> ImmutableSet.copyOf(
                            createSingleMovesAtLocation(graph, location, detective)
                                    .map(move -> (Move) move)
                                    .collect(Collectors.toList()))
                    ).collect(Collectors.toList())
        );
    }

    private Stream<Move.SingleMove> createSingleMovesAtLocation(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            int location,
            final Piece piece
    ) {
        return graph.adjacentNodes(location).stream()
                .flatMap(destination -> {
                    Stream.Builder<Move.SingleMove> builder = Stream.builder();
                    graph.edgeValue(location, destination).ifPresent(allTransport -> {
                        for (ScotlandYard.Transport transport : allTransport)
                            builder.add(new Move.SingleMove(
                                    piece, location, transport.requiredTicket(), destination
                            ));
                        if (piece.isMrX())
                            builder.add(new Move.SingleMove(
                                    piece, location, ScotlandYard.Ticket.SECRET, destination
                            ));
                    });
                    return builder.build();
                });
    }

    private Stream<Move.DoubleMove> createDoubleMovesAtLocation(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            int location
    ) {
        return createSingleMovesAtLocation(graph, location, Piece.MrX.MRX)
                .flatMap(move1 -> createSingleMovesAtLocation(graph, move1.destination, Piece.MrX.MRX)
                        .map(move2 -> new Move.DoubleMove(
                                Piece.MrX.MRX, move1.source(),
                                move1.ticket,
                                move2.source(),
                                move2.ticket,
                                move2.destination)
                        )
                );
    }

    public ImmutableSet<Move> getAvailableMoves(final Piece piece, int location) {
        return availableMoves.get(piece).get(location-1);
    }
}
