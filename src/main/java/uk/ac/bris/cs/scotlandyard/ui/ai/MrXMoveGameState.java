package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.Comparator;
import java.util.Optional;

public final class MrXMoveGameState extends MoveGameState {
    private final Player mrX;
    private final Move bestMove;

    public MrXMoveGameState(final Board board) {
        super(board);
        mrX = createMrX(board);
        bestMove = createBestMove(board);
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

    private Move createBestMove(final Board board) {
        // choose the move which maximises the distance between MrX and the detectives
        Comparator<Move> moveComparator = Comparator.comparingInt(
                move -> findMinDistanceToDetectives(board, moveDestination(move))
        );
        return board.getAvailableMoves().stream()
                .max(moveComparator)
                .get();
    }

    private int findMinDistanceToDetectives(final Board board, int mrXLocation) {
        Optional<Integer> minDist = detectives.stream()
                .map(detective -> minimumDistanceToDestination(detective, mrXLocation))
                .flatMap(Optional::stream)
                .min(Integer::compareTo);
        if (minDist.isPresent()) {
            return minDist.get();
        } else {
            return 1000000;
        }
    }

    @Override
    public Move pickMove() {
        return bestMove;
    }
}
