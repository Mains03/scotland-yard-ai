package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link GameTree} which looks ahead until a winner is found.
 */
public class WinnerLimitedGameTree implements GameTree {
    private final Set<Node> children;

    public WinnerLimitedGameTree(AiBoard board) {
        Set<Node> children = new HashSet<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = (AiBoard) board.advance(move);
            Node child;
            if (newBoard.getWinner().isEmpty())
                child = new InnerNodeWithMove(newBoard, move);
            else
                child = new LeafNodeWithMove(newBoard, move);
            children.add(child);
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
