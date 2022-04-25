package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton helper class to create Player instances from a {@link Board} instance.
 */
public class PlayerFactory {
    private static PlayerFactory instance;

    public static PlayerFactory getInstance() {
        if (instance == null)
            instance = new PlayerFactory();
        return instance;
    }

    private PlayerFactory() {}

    public Player createMrX(Board board) {
        return createPlayer(board, Piece.MrX.MRX);
    }

    public ImmutableList<Player> createDetectives(Board board) {
        List<Piece> detectivePieces = getDetectivePieces(board);
        List<Player> detectives = detectivePieces.stream()
                .map(piece -> createPlayer(board, piece))
                .collect(Collectors.toList());
        return ImmutableList.copyOf(detectives);
    }

    private List<Piece> getDetectivePieces(Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .collect(Collectors.toList());
    }

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

    /**
     * Helper function to retrieve a Player's ticket board.
     */
    private Board.TicketBoard getTicketBoard(Board board, Piece piece) throws NoSuchElementException {
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isEmpty())
            throw new NoSuchElementException("Failed to find " + piece + " tickets");
        return ticketBoard.get();
    }

    /**
     * Helper function.
     */
    private Map<ScotlandYard.Ticket, Integer> createTicketMap(Board.TicketBoard ticketBoard) {
        Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
        for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values())
            tickets.put(ticket, ticketBoard.getCount(ticket));
        return tickets;
    }

    /**
     * Helper function.
     */
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

    /**
     * Used to determine MrX's location at the start.
     */
    private Move getMrXMove(Board board) throws NoSuchElementException {
        // MrX is always next to move so use any move
        Optional<Move> mMove = board.getAvailableMoves().stream()
                .filter(move -> move.commencedBy().isMrX())
                .findAny();
        if (mMove.isEmpty())
            throw new NoSuchElementException("No MrX moves found");
        return mMove.get();
    }

    /**
     * Helper function.
     */
    private int getDetectiveLocation(Board board, Piece.Detective detective) throws NoSuchElementException {
        Optional<Integer> mLocation = board.getDetectiveLocation(detective);
        if (mLocation.isEmpty())
            throw new NoSuchElementException("Failed to find " + detective + " location");
        return mLocation.get();
    }
}
