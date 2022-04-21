package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.AiPlayer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Doesn't consider all variations of detective moves but is fast.
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
        List<AiPlayer> detectives = board.getAiDetectives();
        Set<AiBoard> variations = new HashSet<>();
        generateCombinations(detectives, 0, board, variations);
        return variations;
    }

    private void generateCombinations(
            List<AiPlayer> detectives,
            int depth,
            AiBoard board,
            Set<AiBoard> variations
    ) {
        if (depth == detectives.size())
            variations.add(board);
        else {
            AiPlayer player = detectives.get(depth);
            Set<Move> moves = getMoves(board, player);
            Set<AiBoard> newBoards = applyMoves(board, moves);
            for (AiBoard newBoard : newBoards)
                generateCombinations(detectives, depth+1, newBoard, variations);
        }
    }

    private Set<Move> getMoves(AiBoard board, AiPlayer player) {
        List<Integer> detectiveLocations = board.getDetectiveLocations();
        return player.getAvailableMoves(detectiveLocations);
    }

    private Set<AiBoard> applyMoves(AiBoard board, Set<Move> moves) {
        Set<AiBoard> newBoards = new HashSet<>();
        for (Move move : moves) {
            AiBoard newBoard = board.applyMove(move);
            newBoards.add(newBoard);
        }
        return newBoards;
    }
}
