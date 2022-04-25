package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.Optional;

public class LeafNodeWithMrXMove extends LeafNode {
    private Move move;

    public LeafNodeWithMrXMove(StandardAiBoard board, Move mrXMove) {
        super(board);
        move = mrXMove;
    }

    @Override
    public Optional<Move> getMrXMove() { return Optional.of(move); }
}
