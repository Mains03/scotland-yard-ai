package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.*;
import java.util.stream.Collectors;

public final class GameTree {
    private static final int MAX_DEPTH = 3;
    private static final int NEGATIVE_INFINITY = -10000000;
    private static final int POSITIVE_INFINITY = 10000000;

    private final Board board;
    private final Board.GameState gameState;
    private final int mrXLocation;

    public GameTree(final Board board, final Board.GameState gameState, int mrXLocation) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(gameState);
        this.board = board;
        this.gameState = gameState;
        this.mrXLocation = mrXLocation;
    }

    private int depth;
    private boolean maximise;

    private GameTree(final Board board, final Board.GameState gameState, final int mrXLocation, final int depth, boolean maximise) {
        this.board = board;
        this.gameState = gameState;
        this.mrXLocation = mrXLocation;
        this.depth = depth;
        this.maximise = maximise;
    }

    public Move determineBestMove() {
        Move bestMove = null;
        int bestMoveEvaluation = NEGATIVE_INFINITY;
        for (Move move : gameState.getAvailableMoves()) {
            int newMrXLocation = getMoveDestination(move);
            GameTree child = new GameTree(board, gameState.advance(move), newMrXLocation, MAX_DEPTH-1, false);
            int childEvaluation = child.getEvaluation();
            // maximise the evaluation
            if (childEvaluation > bestMoveEvaluation) {
                bestMove = move;
                bestMoveEvaluation = childEvaluation;
            }
        }
        return bestMove;
    }

    private int getMoveDestination(final Move move) {
        return move.accept(new Move.Visitor<Integer>() {
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

    private int getEvaluation() {
        if (depth <= 0) return staticEvaluation();
        else {
            int evaluation = maximise ? NEGATIVE_INFINITY : POSITIVE_INFINITY;

            for (Move move : gameState.getAvailableMoves()) {
                int newMrXLocation = mrXLocation;
                if (move.commencedBy().isMrX()) newMrXLocation = getMoveDestination(move);
                GameTree child = new GameTree(board, gameState.advance(move), newMrXLocation, MAX_DEPTH-1, !maximise);
                int childEvaluation = child.getEvaluation();

                boolean newBestMove = maximise ? childEvaluation > evaluation : childEvaluation < evaluation;

                if (newBestMove) evaluation = childEvaluation;
            }
            return evaluation;
        }
    }

    private int staticEvaluation() {
        Dijkstra dijkstra = new Dijkstra(board.getSetup().graph);
        Optional<Integer> minDistance = createDetectives(board).stream()
                .map(detective -> dijkstra.minimumRouteLength(detective, mrXLocation))
                .flatMap(Optional::stream)
                .min(Integer::compareTo);
        return 0;
    }

    private Collection<Player> createDetectives(final Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> createDetective(board, piece))
                .collect(Collectors.toList());
    }

    private Player createDetective(final Board board, Piece piece) {
        return new Player(
                piece,
                getPlayerTickets(board, piece),
                getPlayerLocation(board, piece)
        );
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> getPlayerTickets(final Board board, Piece piece) {
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isPresent()) {
            Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
            for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values()) {
                tickets.put(ticket, ticketBoard.get().getCount(ticket));
            }
            return ImmutableMap.copyOf(tickets);
        } else {
            return ImmutableMap.of();
        }
    }

    private int getPlayerLocation(final Board board, Piece piece) {
        if (piece.isMrX()) throw new IllegalArgumentException();
        Optional<Integer> location = board.getDetectiveLocation((Piece.Detective) piece);
        if (location.isPresent()) {
            return location.get();
        } else {
            return -1;
        }
    }
}
