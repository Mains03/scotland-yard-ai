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

public class MinimumDistancePlayerAdapter implements MinimumDistancePlayer {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final Player player;

    public MinimumDistancePlayerAdapter(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, Player player) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(player);
        this.graph = graph;
        this.player = player;
    }

    @Override
    public int getLocation() {
        return player.location();
    }

    /**
     * Doesn't consider whether a location is occupied by another detective.
     * @return available moves
     */
    @Override
    public ImmutableSet<MinimumDistanceMove> getAvailableMoves() {
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

    private Stream<MinimumDistanceMove> getAvailableSingleMoves() {
        return graph.adjacentNodes(player.location()).stream()
                .flatMap(destination -> {
                    List<MinimumDistanceMove> moves = new ArrayList<>();
                    graph.edgeValue(player.location(), destination).ifPresent(allTransport -> {
                        for (ScotlandYard.Transport transport : allTransport) {
                            if (player.has(transport.requiredTicket())) {
                                moves.add(new MinimumDistanceMoveAdapter(new Move.SingleMove(
                                        player.piece(),
                                        player.location(),
                                        transport.requiredTicket(),
                                        destination
                                )));
                            }
                            if (player.has(ScotlandYard.Ticket.SECRET)) {
                                moves.add(new MinimumDistanceMoveAdapter(new Move.SingleMove(
                                        player.piece(),
                                        player.location(),
                                        ScotlandYard.Ticket.SECRET,
                                        destination
                                )));
                            }
                        }
                    });
                    return moves.stream();
                });
    }

    private Stream<MinimumDistanceMove> getAvailableDoubleMoves() {
        if (player.isDetective() || (!player.has(ScotlandYard.Ticket.DOUBLE)))
            return Stream.empty();
        else {
            return getAvailableSingleMoves().flatMap(move1 -> {
                // apply singleMove to the player
                MinimumDistancePlayerAdapter newPlayer = (MinimumDistancePlayerAdapter) applyMove(move1);
                // consider all single moves of the new player
                return newPlayer.getAvailableSingleMoves().map(move2 -> {
                    Move.SingleMove singleMove1 = (Move.SingleMove) move1.asMove();
                    Move.SingleMove singleMove2 = (Move.SingleMove) move2.asMove();
                    return new MinimumDistanceMoveAdapter(new Move.DoubleMove(
                            player.piece(),
                            player.location(),
                            singleMove1.ticket,
                            singleMove1.destination,
                            singleMove2.ticket,
                            singleMove2.destination
                    ));
                });
            });
        }
    }

    @Override
    public MinimumDistancePlayer applyMove(MinimumDistanceMove move) {
        return new MinimumDistancePlayerAdapter(graph, player.use(move.tickets()).at(move.getDestination()));
    }

    @Override
    public Player asPlayer() {
        return player;
    }
}
