package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.AbstractLeafNodeWithMove;

import java.util.Objects;

public class LeafNodeWithMove extends AbstractLeafNodeWithMove {
    private final LeafNode node;

    private final Move move;

    public LeafNodeWithMove(AiBoard board, Move move) {
        node = new LeafNode(board);
        this.move = Objects.requireNonNull(move);
    }

    @Override
    public AiBoard getBoard() {
        return node.getBoard();
    }

    @Override
    public Move getMove() {
        return move;
    }
}
