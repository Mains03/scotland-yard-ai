package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link GameTree} which looks ahead until a maximum depth is reached.
 */
public class DepthLimitedGameTree implements GameTree {
    private final Set<Node> children;

    public DepthLimitedGameTree(AiBoard board) {
        Set<Node> children = new HashSet<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = (AiBoard) board.advance(move);
            children.add(new LeafNodeWithMove(newBoard, move));
        }
        this.children = children;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Set<Node> getChildren() {
        return Set.copyOf(children);
    }
}
