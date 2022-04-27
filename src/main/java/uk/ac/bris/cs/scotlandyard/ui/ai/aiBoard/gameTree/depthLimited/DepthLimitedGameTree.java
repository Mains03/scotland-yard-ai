package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link GameTree} which looks ahead until a maximum depth is reached.
 */
public class DepthLimitedGameTree implements GameTree {
    public final ImmutableSet<Node> children;

    public DepthLimitedGameTree(AiBoard board) {
        Set<Node> children = new HashSet<>();
        for (Move move : board.getAvailableMoves()) {
            children.add(new LeafNodeWithMove(board, move));
        }
        this.children = ImmutableSet.copyOf(children);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
