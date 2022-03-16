package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface GameState {
    Move pickMove();

    class MrXMoveGameState implements GameState {
        private final Player mrX;
        private final Collection<Player> detectives;
        private final Move move;

        public MrXMoveGameState(Board board) {
            Objects.requireNonNull(board);
            mrX = createMrX(board);
            detectives = createDetectives();
            move = createBestMove();
        }

        private Player createMrX(final Board board) {
            return new Player(
                    createMrXPiece(),
                    createMrXTickets(board),
                    createMrXLocation(board)
            );
        }

        private Piece createMrXPiece() {
            return Piece.MrX.MRX;
        }

        private ImmutableMap<ScotlandYard.Ticket, Integer> createMrXTickets(final Board board) {
            Board.TicketBoard ticketBoard = board.getPlayerTickets(Piece.MrX.MRX).get();
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

        private int createMrXLocation(final Board board) {
            return board.getAvailableMoves().stream()
                    .map(Move::source)
                    .findAny().get();
        }

        private Collection<Player> createDetectives() {
            return null;
        }

        private Move createBestMove() {
            return null;
        }

        @Override
        public Move pickMove() {
            return move;
        }
    }

    class DetectiveMoveGameState implements GameState {
        @Override
        public Move pickMove() {
            return null;
        }
    }
}
