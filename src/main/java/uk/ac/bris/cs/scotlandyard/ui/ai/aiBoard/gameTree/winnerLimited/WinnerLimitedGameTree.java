package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;

/**
 * {@link GameTree} which looks ahead until a winner is found.
 */
public class WinnerLimitedGameTree extends Node implements GameTree {
    public WinnerLimitedGameTree(AiBoard board) {
        super(board);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
