package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.PiecePlayerFactoryAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMoveAdapter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @deprecated Generates illegal moves, use {@link AiPlayerAdapterV2}
 */
@Deprecated
public class AiPlayerAdapter implements AiPlayer {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final Player player;

    public AiPlayerAdapter(Board board, Player player) {
        this(board.getSetup().graph, player);
    }

    public AiPlayerAdapter(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, Player player) {
        this.graph = Objects.requireNonNull(graph);
        this.player = Objects.requireNonNull(player);
    }

    public AiPlayerAdapter(Board board, Piece piece) {
        this.graph = Objects.requireNonNull(board.getSetup().graph);
        Optional<Player> mPlayer = new PiecePlayerFactoryAdapter(board).createPlayer(piece);
        if (mPlayer.isEmpty())
            throw new RuntimeException("Failed to create piece " + piece.webColour());
        player = mPlayer.get();
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
    public ImmutableSet<AiMove> getAvailableMoves() {
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

    private Stream<AiMove> getAvailableSingleMoves() {
        return graph.adjacentNodes(player.location()).stream()
                .flatMap(destination -> {
                    List<AiMove> moves = new ArrayList<>();
                    graph.edgeValue(player.location(), destination).ifPresent(allTransport -> {
                        for (ScotlandYard.Transport transport : allTransport) {
                            if (player.has(transport.requiredTicket())) {
                                moves.add(new AiMoveAdapter(new Move.SingleMove(
                                        player.piece(),
                                        player.location(),
                                        transport.requiredTicket(),
                                        destination
                                )));
                            }
                            if (player.has(ScotlandYard.Ticket.SECRET)) {
                                moves.add(new AiMoveAdapter(new Move.SingleMove(
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

    private Stream<AiMove> getAvailableDoubleMoves() {
        if (player.isDetective() || (!player.has(ScotlandYard.Ticket.DOUBLE)))
            return Stream.empty();
        else {
            return getAvailableSingleMoves().flatMap(move1 -> {
                // apply singleMove to the player
                AiPlayerAdapter newPlayer = (AiPlayerAdapter) applyMove(move1);
                // consider all single moves of the new player
                return newPlayer.getAvailableSingleMoves().map(move2 -> {
                    Move.SingleMove singleMove1 = (Move.SingleMove) move1.asMove();
                    Move.SingleMove singleMove2 = (Move.SingleMove) move2.asMove();
                    return new AiMoveAdapter(new Move.DoubleMove(
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
    public AiPlayer applyMove(AiMove move) {
        return new AiPlayerAdapter(graph, player.use(move.tickets()).at(move.getDestination()));
    }

    @Override
    public Player asPlayer() {
        return player;
    }
}
