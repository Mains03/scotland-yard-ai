package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * An inner node has children.
 */
public class GameTreeInnerNode extends GameTreeNode {
    private final Set<GameTreeNode> children;

    public GameTreeInnerNode(AiBoardV2 board, int depth) {
        this.children = createChildren(board, depth);
    }

    private Set<GameTreeNode> createChildren(AiBoardV2 board, int depth) {
        if (depth < 0)
            throw new IllegalArgumentException();
        Set<GameTreeNode> children = new HashSet<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoardV2 newBoard = board.applyMove(move);
            GameTreeNode child = createChild(newBoard, depth);
            children.add(child);
        }
        return children;
    }

    private GameTreeNode createChild(AiBoardV2 board, int depth) {
        GameTreeNode child;
        if (depth == 0)
            child = new GameTreeLeafNode(board);
        else
            child = new GameTreeInnerNode(board, depth-1);
        return child;
    }

    @Override
    public int accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> mrXMoveMade() {
        // MrX didn't make a move to get here
        return Optional.empty();
    }

    public Set<GameTreeNode> getChildren() {
        return Set.copyOf(children);
    }
}
