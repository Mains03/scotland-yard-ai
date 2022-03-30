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
                // apply singleMove to the player
                AiPlayerAdapter newPlayer = (AiPlayerAdapter) applyMove(singleMove);
                // consider all single moves of the new player
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
        Player newPlayer = move.accept(new Move.Visitor<Player>() {
            @Override
            public Player visit(Move.SingleMove move) {
                return player.use(move.ticket).at(move.destination);
            }

            @Override
            public Player visit(Move.DoubleMove move) {
                return player.use(ScotlandYard.Ticket.DOUBLE).use(move.tickets()).at(move.destination2);
            }
        });
        return new AiPlayerAdapter(graph, player);
    }
}
