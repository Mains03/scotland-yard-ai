package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MrXMove {
    private final Move bestMove;

    public MrXMove(final Board board) {
        bestMove = createBestMove(board);
    }

    private Move createBestMove(final Board board) {
        return new GameTree(board, createGameState(board), determineMrXLocation(board))
                .determineBestMove();
    }

    private Board.GameState createGameState(final Board board) {
        return new MyGameStateFactory().build(
                board.getSetup(),
                createMrXPlayer(board),
                createDetectives(board)
        );
    }

    private Player createMrXPlayer(final Board board) {
        return new Player(
                Piece.MrX.MRX,
                createPlayerTickets(board, Piece.MrX.MRX),
                determineMrXLocation(board)
        );
    }

    private int determineMrXLocation(final Board board) {
        return board.getAvailableMoves().stream()
                .findAny()
                .get()
                .source();
    }



    private ImmutableList<Player> createDetectives(final Board board) {
        Collection<Piece> detectives = board.getPlayers().stream()
                .filter(Piece::isDetective)
                .collect(Collectors.toList());
        return ImmutableList.copyOf(
                detectives.stream()
                        .map(piece -> new Player(
                                piece,
                                createPlayerTickets(board, piece),
                                board.getDetectiveLocation((Piece.Detective) piece).get()
                                )
                        ).collect(Collectors.toList())
        );
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> createPlayerTickets(final Board board, final Piece piece) {
        Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isPresent()) {
            for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values())
                tickets.put(ticket, ticketBoard.get().getCount(ticket));
        }
        return ImmutableMap.copyOf(tickets);
    }

    public Move pickMove() {
        return bestMove;
    }
}
