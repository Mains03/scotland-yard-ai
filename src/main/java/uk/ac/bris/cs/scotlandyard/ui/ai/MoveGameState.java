package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class MoveGameState {
    protected final ImmutableList<Player> detectives;

    public MoveGameState(final Board board) {
        Objects.requireNonNull(board);
        detectives = createDetectives(board);
    }

    private ImmutableList<Player> createDetectives(final Board board) {
        Collection<Piece> detectivePieces = board.getPlayers().stream()
                .filter(Piece::isDetective)
                .collect(Collectors.toList());
        Collection<Player> detectives = detectivePieces.stream()
                .map(piece -> new Player(
                        piece,
                        createPlayerTickets(board, piece),
                        getDetectiveLocation(board, piece)
                )).collect(Collectors.toList());
        return ImmutableList.copyOf(detectives);
    }

    private int getDetectiveLocation(final Board board, final Piece piece) {
        if (!piece.isDetective()) throw new IllegalArgumentException();
        Piece.Detective detectivePiece = (Piece.Detective) piece;
        return board.getDetectiveLocation(detectivePiece).get();
    }

    protected ImmutableMap<ScotlandYard.Ticket, Integer> createPlayerTickets(final Board board, final Piece piece) {
        Board.TicketBoard ticketBoard = board.getPlayerTickets(piece).get();
        Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
        ScotlandYard.Ticket ticketsEnumeration[] = new ScotlandYard.Ticket[] {
                ScotlandYard.Ticket.BUS,
                ScotlandYard.Ticket.DOUBLE,
                ScotlandYard.Ticket.SECRET,
                ScotlandYard.Ticket.TAXI,
                ScotlandYard.Ticket.UNDERGROUND
        };
        for (ScotlandYard.Ticket ticket : ticketsEnumeration) {
            tickets.put(ticket, ticketBoard.getCount(ticket));
        }
        return ImmutableMap.copyOf(tickets);
    }

    public abstract Move pickMove();
}
