package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.AbstractInnerNodeWithMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.Set;

/**
 * {@link InnerNode} which stores the move made.
 */
public class InnerNodeWithMove extends AbstractInnerNodeWithMove {
    private final InnerNode node;

    private final Move move;

    public InnerNodeWithMove(AiBoard board, Move move) {
        node = new InnerNode(board);
        this.move = move;
    }

    @Override
    public Set<Node> getChildren() {
        return node.getChildren();
    }

    @Override
    public Move getMove() {
        return move;
    }
}
