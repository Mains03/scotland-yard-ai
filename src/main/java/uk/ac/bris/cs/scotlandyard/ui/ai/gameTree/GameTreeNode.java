package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.ArrayList;
import java.util.List;

final class GameTreeNode {
    private final Move move;

    private final ImmutableList<GameTreeNode> children;

    GameTreeNode(final Board board, final Move move, int depth) {
        this.move = move;
        if (depth > 0)
            children = createChildren(depth);
        else
            children = ImmutableList.of();
    }

    private GameTreeNode(final AvailableMove availableMove, int depth) {

    }

    private ImmutableList<GameTreeNode> createChildren(int depth) {
        List<GameTreeNode> children = new ArrayList<>();
        for (AvailableMove availableMove : getAvailableMoves())
            children.add(new GameTreeNode(availableMove, depth-1));
        return ImmutableList.copyOf(children);
    }

    private ImmutableList<AvailableMove> getAvailableMoves() {
        return null;
    }
}
