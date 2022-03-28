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
        Player mrX = new Player(
                Piece.MrX.MRX,
                createPlayerTickets(board, Piece.MrX.MRX),
                board.getAvailableMoves().stream()
                        .findAny().get().source()
        );
        List<Player> detectives = board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> createDetective(board, (Piece.Detective) piece))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        children = new ArrayList<>();
        for (Move move : board.getAvailableMoves()) {
            GameState gameState = new GameState(
                    board.getSetup().graph,
                    mrX,
                    detectives,
                    false,
                    List.of(move)
            );
            children.add(new GameTreeNode(gameState, MAX_DEPTH));
        }
    }

    /**
     * Create player tickets from the given information in the board.
     * @param board the board
     * @param piece the piece to create the tickets for
     * @return the tickets
     */
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

    /**
     * Create a detective from the given information in the board.
     * @param board the board
     * @param piece the detective to create
     * @return the player
     */
    private Optional<Player> createDetective(final Board board, Piece.Detective piece) {
        if (piece.isMrX()) throw new IllegalArgumentException();
        Optional<Integer> location = board.getDetectiveLocation(piece);
        if (location.isEmpty()) return Optional.empty();
        return Optional.of(new Player(
                piece,
                createPlayerTickets(board, piece),
                location.get()
        ));
    }

    /**
     * Determine the best move by evaluating the game tree using minimax.
     * @return the best move
     */
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

