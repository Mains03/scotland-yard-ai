package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.Optional;

public class LeafNodeWithMrXMove extends LeafNode {
    private Move move;

    public LeafNodeWithMrXMove(AiBoard board, Move mrXMove) {
        super(board);
        move = mrXMove;
    }

    @Override
    public Optional<Move> getMrXMove() { return Optional.of(move); }
}
