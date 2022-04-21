package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PlayerFactory {
    private static PlayerFactory instance;

    public static PlayerFactory getInstance() {
        if (instance == null)
            instance = new PlayerFactory();
        return instance;
    }

    private PlayerFactory() {}

    public Player createPlayer(Board board, Piece piece) {
        var tickets = createTickets(board, piece);
        int location = determineLocation(board, piece);
        return new Player(piece, tickets, location);
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> createTickets(Board board, Piece piece) {
        Board.TicketBoard ticketBoard = getTicketBoard(board, piece);
        Map<ScotlandYard.Ticket, Integer> tickets = createTicketMap(ticketBoard);
        return ImmutableMap.copyOf(tickets);
    }

    private Board.TicketBoard getTicketBoard(Board board, Piece piece) {
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isEmpty())
            throw new NoSuchElementException("Failed to find " + piece + " tickets");
        return ticketBoard.get();
    }

    private Map<ScotlandYard.Ticket, Integer> createTicketMap(Board.TicketBoard ticketBoard) {
        Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
        for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values())
            tickets.put(ticket, ticketBoard.getCount(ticket));
        return tickets;
    }

    private int determineLocation(Board board, Piece piece) {
        // distinguish between MrX and detectives since MrX location is not known by the board
        int location;
        if (piece.isMrX())
            location = getMrXLocation(board);
        else
            location = getDetectiveLocation(board, (Piece.Detective) piece);
        return location;
    }

    private int getMrXLocation(Board board) {
        Move move = getMrXMove(board);
        return move.source();
    }

    private Move getMrXMove(Board board) {
        // MrX is always next to move
        Optional<Move> mMove = board.getAvailableMoves().stream()
                .findAny().stream()
                .findAny();
        if (mMove.isEmpty())
            throw new NoSuchElementException("No moves found");
        return mMove.get();
    }

    private int getDetectiveLocation(Board board, Piece.Detective detective) {
        Optional<Integer> mLocation = board.getDetectiveLocation(detective);
        if (mLocation.isEmpty())
            throw new NoSuchElementException("Failed to find " + detective + " location");
        return mLocation.get();
    }
}
