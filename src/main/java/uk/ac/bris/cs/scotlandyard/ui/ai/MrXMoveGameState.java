package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

public final class MrXMoveGameState extends MoveGameState {
    private final Player mrX;
    private final Move bestMove;

    public MrXMoveGameState(final Board board) {
        super(board);
        mrX = createMrX(board);
        bestMove = createBestMove();
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
        return createPlayerTickets(board, Piece.MrX.MRX);
    }

    private int createMrXLocation(final Board board) {
        return board.getAvailableMoves().stream()
                .map(Move::source)
                .findAny().get();
    }

    private Move createBestMove() {
        return null;
    }

    @Override
    public Move pickMove() {
        return bestMove;
    }
}
