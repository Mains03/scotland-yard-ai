package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayerMovesFactory {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;

    PlayerMovesFactory(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        this.graph = graph;
    }

    public PlayerMoves createPlayerMoves(final Piece piece) {
        if (piece.isMrX())
            return createMrXPlayerMoves();
        else
            return createDetectivePlayerMoves(piece);
    }

    private PlayerMoves createMrXPlayerMoves() {
        return new PlayerMoves(Piece.MrX.MRX) {
            @Override
            List<Move> createMoves(Piece piece, int location) {
                return Stream.concat(
                        createSingleMoves(location),
                        createDoubleMoves(location)
                ).collect(Collectors.toList());
            }

            private Stream<Move.SingleMove> createSingleMoves(int source) {
                return graph.adjacentNodes(source).stream()
                        .flatMap(destination -> createSingleMoves(source, destination));
            }

            private Stream<Move.SingleMove> createSingleMoves(int source, int destination) {
                var allTransport = graph.edgeValue(source, destination);
                if (allTransport.isEmpty()) return Stream.empty();
                return allTransport.get().stream()
                        .flatMap(transport -> createSingleMoves(source, destination, transport));
            }

            private Stream<Move.SingleMove> createSingleMoves(int source, int destination, ScotlandYard.Transport transport) {
                Stream.Builder<Move.SingleMove> builder = Stream.builder();
                builder.add(new Move.SingleMove(Piece.MrX.MRX, source, transport.requiredTicket(), destination));
                builder.add(new Move.SingleMove(Piece.MrX.MRX, source, ScotlandYard.Ticket.SECRET, destination));
                return builder.build();
            }

            private Stream<Move> createDoubleMoves(int source) {
                return createSingleMoves(source)
                        .flatMap(move -> createDoubleMoves(source, move));
            }

            private Stream<Move> createDoubleMoves(int source, Move.SingleMove move) {
                return createSingleMoves(move.destination)
                        .map(move2 -> new Move.DoubleMove(
                                Piece.MrX.MRX, source, move.ticket, move.destination, move2.ticket, move2.destination
                        ));
            }
        };
    }

    private PlayerMoves createDetectivePlayerMoves(final Piece piece) {
        return new PlayerMoves(piece) {
            @Override
            List<Move> createMoves(Piece piece, int location) {
                return graph.adjacentNodes(location).stream()
                        .flatMap(destination -> createSingleMoves(piece, location, destination))
                        .collect(Collectors.toList());
            }

            private Stream<Move.SingleMove> createSingleMoves(final Piece piece, int source, int destination) {
                var allTransport = graph.edgeValue(source, destination);
                if (allTransport.isEmpty()) return Stream.empty();
                return allTransport.get().stream()
                        .map(transport -> new Move.SingleMove(piece, source, transport.requiredTicket(), destination));
            }
        };
    }
}
