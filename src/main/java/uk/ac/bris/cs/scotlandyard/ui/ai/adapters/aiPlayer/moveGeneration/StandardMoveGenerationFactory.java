package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.moveGeneration;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.MoveApplyFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StandardMoveGenerationFactory implements MoveGenerationFactory {
    private static StandardMoveGenerationFactory moveGenerationFactory;

    public static StandardMoveGenerationFactory getInstance() {
        if (moveGenerationFactory == null)
            moveGenerationFactory = new StandardMoveGenerationFactory();
        return moveGenerationFactory;
    }

    private StandardMoveGenerationFactory() {}

    @Override
    public Set<Move> generateMoves(MoveGenerationBoard board, Player player) {
        Set<Move> moves;
        // distinguish between MrX and detectives since their moves differ
        if (player.isMrX())
            moves = generateMrXMoves(board, player);
        else
            moves = generateSingleMoves(board, player);
        return moves;
    }

    private Set<Move> generateSingleMoves(MoveGenerationBoard board, Player player) {
        Set<Move> moves = new HashSet<>();
        // destination of moves must not conflict with a detective's location
        for (int destination : getUnoccupiedAdjacentNodes(board, player)) {
            Set<ScotlandYard.Transport> transports = getTransports(board, player, destination);
            for (ScotlandYard.Transport transport : transports) {
                if (player.has(transport.requiredTicket())) {
                    Move move = createSingleMove(player, transport.requiredTicket(), destination);
                    moves.add(move);
                }
            }
            if (player.has(ScotlandYard.Ticket.SECRET)) {
                Move move = createSingleMoveWithSecretTicket(player, destination);
                moves.add(move);
            }
        }
        return moves;
    }

    private Iterable<Integer> getUnoccupiedAdjacentNodes(MoveGenerationBoard board, Player player) {
        int location = player.location();
        var graph = board.getGraph();
        Set<Integer> adjacent = graph.adjacentNodes(location);
        Set<Integer> unoccupied = new HashSet<>();
        for (int adjacentLocation : adjacent) {
            if (!locationOccupiedByDetective(board, adjacentLocation))
                unoccupied.add(adjacentLocation);
        }
        return unoccupied;
    }

    private boolean locationOccupiedByDetective(MoveGenerationBoard board, int location) {
        return board.getDetectiveLocations().contains(location);
    }

    private Set<ScotlandYard.Transport> getTransports(MoveGenerationBoard board, Player player, int destination) {
        int location = player.location();
        var graph = board.getGraph();
        Optional<ImmutableSet<ScotlandYard.Transport>> mTransport = graph.edgeValue(location, destination);
        if (mTransport.isPresent())
            return mTransport.get();
        else
            return Set.of();
    }

    private Move.SingleMove createSingleMove(Player player, ScotlandYard.Ticket ticket, int destination) {
        return new Move.SingleMove(player.piece(), player.location(), ticket, destination);
    }

    private Move.SingleMove createSingleMoveWithSecretTicket(Player player, int destination) {
        return new Move.SingleMove(player.piece(), player.location(), ScotlandYard.Ticket.SECRET, destination);
    }

    private Set<Move> generateMrXMoves(MoveGenerationBoard board, Player player) {
        if (player.isDetective())
            throw new IllegalArgumentException();
        Set<Move> singleMoves = generateSingleMoves(board, player);
        Set<Move> doubleMoves = generateDoubleMoves(board, player);
        Set<Move> moves = singleMoves;
        moves.addAll(doubleMoves);
        return moves;
    }

    private Set<Move> generateDoubleMoves(MoveGenerationBoard board, Player player) {
        Set<Move> singleMoves = generateSingleMoves(board, player);
        Set<Move> moves = new HashSet<>();
        for (Move firstMove : singleMoves) {
            Set<Move> doubleMoves = createDoubleMoves(board, player, (Move.SingleMove) firstMove);
            moves.addAll(doubleMoves);
        }
        return moves;
    }


    private Set<Move> createDoubleMoves(MoveGenerationBoard board, Player player, Move.SingleMove firstMove) {
        Player newPlayer = applyMove(player, firstMove);
        Set<Move> secondMoves = generateSingleMoves(board, newPlayer);
        Set<Move> doubleMoves = new HashSet<>();
        for (Move secondMove : secondMoves) {
            Move.DoubleMove doubleMove = createDoubleMove(player, firstMove, (Move.SingleMove) secondMove);
            doubleMoves.add(doubleMove);
        }
        return doubleMoves;
    }

    private Player applyMove(Player player, Move move) {
        return MoveApplyFactory.getInstance().applyMove(player, move);
    }

    private Move.DoubleMove createDoubleMove(Player player, Move.SingleMove firstMove, Move.SingleMove secondMove) {
        return new Move.DoubleMove(
                player.piece(),
                player.location(),
                firstMove.ticket,
                firstMove.destination,
                secondMove.ticket,
                secondMove.destination
        );
    }
}
