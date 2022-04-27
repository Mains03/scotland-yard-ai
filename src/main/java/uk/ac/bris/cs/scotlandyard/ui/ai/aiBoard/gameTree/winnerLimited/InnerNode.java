package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.AbstractInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.HashSet;
import java.util.Set;

public class InnerNode extends AbstractInnerNode {
    public final ImmutableSet<Node> children;

    public InnerNode(AiBoard board) {
        Set<Node> children = new HashSet<>();
        if (board.getWinner().isEmpty()) {
            for (Move move : board.getAvailableMoves()) {
                AiBoard newBoard = (AiBoard) board.advance(move);
                Node child;
                if (newBoard.getWinner().isEmpty())
                    child = new InnerNode(newBoard);
                else
                    child = new LeafNode(newBoard);
                children.add(child);
            }
        }
        this.children = ImmutableSet.copyOf(children);
    }

    @Override
    public Set<Node> getChildren() {
        return children;
    }
}
