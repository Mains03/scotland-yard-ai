package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Game tree to determine the best move.
 */
public class GameTree {
    private static final int MAX_DEPTH = 1;

    private final List<GameTreeNode> children;

    public GameTree(final Board board) {
        children = createChildren(board);
    }

    private List<GameTreeNode> createChildren(final Board board) {
        List<GameTreeNode> children = new ArrayList<>();
        for (Move move : board.getAvailableMoves())
            children.add(createGameTreeNode(board, move));
        return children;
    }

    private GameTreeNode createGameTreeNode(final Board board, final Move move) {
        Player mrX = new Player(Piece.MrX.MRX, createPlayerTickets(board, Piece.MrX.MRX), move.source());
        List<Player> detectives = board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> createDetective(board, (Piece.Detective) piece))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        return new GameTreeNode(
                new GameState(board.getSetup().graph, mrX, detectives, List.of(move)),
                MAX_DEPTH
        );
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> createPlayerTickets(final Board board, final Piece piece) {
        Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isPresent()) {
            for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values()) {
                int count = ticketBoard.get().getCount(ticket);

                tickets.put(ticket, count);
            }
        }
        return ImmutableMap.copyOf(tickets);
    }

    private Optional<Player> createDetective(final Board board, Piece.Detective piece) {
        Optional<Integer> location = board.getDetectiveLocation(piece);
        if (location.isEmpty()) return Optional.empty();
        return Optional.of(new Player(
                piece,
                createPlayerTickets(board, piece),
                location.get()
        ));
    }

    public Move determineBestMove() {
        Move bestMove = null;
        int bestMoveEval = GameTreeNode.NEGATIVE_INFINITY;
        for (GameTreeNode child : children) {
            int childEval = child.evaluate(false);
            if (childEval > bestMoveEval) {
                bestMoveEval = childEval;
                bestMove = child.getMove().get(0);
            }
        }
        return bestMove;
    }
}

