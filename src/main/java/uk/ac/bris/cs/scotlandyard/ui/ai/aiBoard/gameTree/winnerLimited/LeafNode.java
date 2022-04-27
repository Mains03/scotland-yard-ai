package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.AbstractLeafNode;

import java.util.Objects;

public class LeafNode extends AbstractLeafNode {
    private final AiBoard board;

    public LeafNode(AiBoard board) {
        this.board = Objects.requireNonNull(board);
    }

    @Override
    public AiBoard getBoard() {
        return board;
    }
}
