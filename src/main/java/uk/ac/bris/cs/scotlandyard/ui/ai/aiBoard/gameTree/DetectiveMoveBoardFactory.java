package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PotentialDetectiveLocationsAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DetectiveMoveBoardFactory implements AiBoard.Visitor<Set<AiBoard>> {
    private static DetectiveMoveBoardFactory instance;

    public static DetectiveMoveBoardFactory getInstance() {
        if (instance == null)
            instance = new DetectiveMoveBoardFactory();
        return instance;
    }

    protected DetectiveMoveBoardFactory() {}

    @Override
    public Set<AiBoard> visit(StandardAiBoard board) {
        return visit((AiBoard) board);
    }

    @Override
    public Set<AiBoard> visit(LocationAiBoard board) {
        return visit((AiBoard) board);
    }

    @Override
    public Set<AiBoard> visit(PotentialDetectiveLocationsAiBoard board) {
        // random values since detectives can't move
        Move move = new Move.SingleMove(Piece.Detective.BLUE, 1, ScotlandYard.Ticket.TAXI, 2);
        return Set.of((PotentialDetectiveLocationsAiBoard) board.advance(move));
    }

    private Set<AiBoard> visit(AiBoard board) {
        Set<AiBoard> currentBoards = new HashSet<>();
        currentBoards.add(board);
        Set<AiBoard> result = new HashSet<>();
        permutations(currentBoards, result);
        return result;
    }

    private void permutations(Set<AiBoard> currentBoards, Set<AiBoard> result) {
        Set<AiBoard> newCurrentBoards = new HashSet<>();
        boolean finished = true;
        for (AiBoard board : currentBoards) {
            if (detectiveMovesLeft(board)) {
                finished = false;
                for (Move move : board.getAvailableMoves()) {
                    AiBoard newBoard = (AiBoard) board.advance(move);
                    newCurrentBoards.add(newBoard);
                }
            } else
                newCurrentBoards.add(board);
        }
        if (finished)
            result.addAll(newCurrentBoards);
        else
            permutations(newCurrentBoards, result);
    }

    private boolean detectiveMovesLeft(AiBoard board) {
        Optional<Move> mMove = board.getAvailableMoves().stream().findAny();
        if (mMove.isEmpty())
            return false;
        else {
            Move move = mMove.get();
            return move.commencedBy().isDetective();
        }
    }
}
