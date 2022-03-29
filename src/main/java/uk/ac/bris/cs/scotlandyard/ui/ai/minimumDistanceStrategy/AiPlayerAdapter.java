package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AiPlayerAdapter implements AiPlayer {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final Player player;

    public AiPlayerAdapter(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, Player player) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(player);
        this.graph = graph;
        this.player = player;
    }

    @Override
    public int getLocation() {
        return player.location();
    }

    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        if (player.isDetective())
            return ImmutableSet.copyOf(getAvailableSingleMoves().collect(Collectors.toList()));
        else {
            return ImmutableSet.copyOf(
                    Stream.concat(
                            getAvailableSingleMoves(),
                            getAvailableDoubleMoves()
                    ).collect(Collectors.toList())
            );
        }
    }

    private Stream<Move.SingleMove> getAvailableSingleMoves() {
        return graph.adjacentNodes(player.location()).stream()
                .flatMap(destination -> {
                    List<Move.SingleMove> moves = new ArrayList<>();
                    graph.edgeValue(player.location(), destination).ifPresent(allTransport -> {
                        for (ScotlandYard.Transport transport : allTransport) {
                            if (player.has(transport.requiredTicket())) {
                                moves.add(new Move.SingleMove(
                                        player.piece(),
                                        player.location(),
                                        transport.requiredTicket(),
                                        destination
                                ));
                            }
                            if (player.has(ScotlandYard.Ticket.SECRET)) {
                                moves.add(new Move.SingleMove(
                                        player.piece(),
                                        player.location(),
                                        ScotlandYard.Ticket.SECRET,
                                        destination
                                ));
                            }
                        }
                    });
                    return moves.stream();
                });
    }

    private Stream<Move.DoubleMove> getAvailableDoubleMoves() {
        if (player.isDetective() || (!player.has(ScotlandYard.Ticket.DOUBLE)))
            return Stream.empty();
        else {
            return getAvailableSingleMoves().flatMap(singleMove -> {
                AiPlayerAdapter newPlayer = (AiPlayerAdapter) applyMove(singleMove);
                return newPlayer.getAvailableSingleMoves().map(secondSingleMove -> new Move.DoubleMove(
                        player.piece(),
                        player.location(),
                        singleMove.ticket,
                        singleMove.destination,
                        secondSingleMove.ticket,
                        secondSingleMove.destination
                ));
            });
        }
    }

    @Override
    public AiPlayer applyMove(Move move) {
        return new AiPlayerAdapter(graph, player.use(move.tickets()).at(moveDestination(move)));
    }

    private int moveDestination(Move move) {
        return move.accept(new Move.Visitor<>() {
            @Override
            public Integer visit(Move.SingleMove move) {
                return move.destination;
            }

            @Override
            public Integer visit(Move.DoubleMove move) {
                return move.destination2;
            }
        });
    }
}
