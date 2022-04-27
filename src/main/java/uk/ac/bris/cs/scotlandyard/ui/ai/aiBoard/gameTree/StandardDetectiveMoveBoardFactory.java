package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Generates all permutations by moving the detectives.
 */
public class StandardDetectiveMoveBoardFactory {
    private static StandardDetectiveMoveBoardFactory instance;

    public static StandardDetectiveMoveBoardFactory getInstance() {
        if (instance == null)
            instance = new StandardDetectiveMoveBoardFactory();
        return instance;
    }

    private StandardDetectiveMoveBoardFactory() {}

    public Set<AiBoard> generate(AiBoard board) {
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
