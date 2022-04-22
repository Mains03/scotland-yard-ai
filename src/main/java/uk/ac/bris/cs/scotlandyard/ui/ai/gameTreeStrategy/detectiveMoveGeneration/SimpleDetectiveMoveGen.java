package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Doesn't consider all variations of detective moves for efficiency.
 */
public class SimpleDetectiveMoveGen implements DetectiveMoveGeneration {
    private static SimpleDetectiveMoveGen instance;

    public static SimpleDetectiveMoveGen getInstance() {
        if (instance == null)
            instance = new SimpleDetectiveMoveGen();
        return instance;
    }

    protected SimpleDetectiveMoveGen() {}

    @Override
    public Set<AiBoard> moveDetectives(AiBoard board) {
        List<Piece> detectives = getDetectivePieces(board);
        Set<AiBoard> boards = new HashSet<>();
        boards.add(board);
        for (Piece piece : detectives) {
            for (AiBoard b : boards) {
                Set<Move> moves = getDetectiveMoves(b, piece);
                Set<AiBoard> newBoards = new HashSet<>();
                for (Move move : moves) {
                    AiBoard b2 = (AiBoard) b.advance(move);
                    newBoards.add(b2);
                }
                boards = newBoards;
            }
        }
        return moveRemainingDetectives(boards);
    }

    private List<Piece> getDetectivePieces(AiBoard board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .collect(Collectors.toList());
    }

    private Set<Move> getDetectiveMoves(AiBoard board, Piece piece) {
        return board.getAvailableMoves().stream()
                .filter(move -> move.commencedBy() == piece)
                .collect(Collectors.toSet());
    }

    private Set<AiBoard> moveRemainingDetectives(Set<AiBoard> boards) {
        boolean remaining = true;
        while (remaining) {
            remaining = false;
            Set<AiBoard> newBoards = new HashSet<>();
            for (AiBoard board : boards) {
                if (detectiveMoveRemaining(board)) {
                    remaining = true;
                    Move move = anyMove(board);
                    AiBoard newBoard = (AiBoard) board.advance(move);
                    newBoards.add(newBoard);
                } else
                    newBoards.add(board);
            }
            boards = newBoards;
        }
        return boards;
    }

    private boolean detectiveMoveRemaining(AiBoard board) {
        Set<Move> moves = board.getAvailableMoves();
        if (moves.size() == 0)
            return false;
        else {
            Move move = moves.stream().findAny().get();
            return move.commencedBy().isDetective();
        }
    }

    private Move anyMove(AiBoard board) {
        return board.getAvailableMoves().stream().findAny().get();
    }
}
