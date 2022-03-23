package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.ArrayList;
import java.util.List;

public final class GameTree {
    private final ImmutableList<GameTreeNode> children;

    public GameTree(final Board board, int depth) {
        if (depth <= 0) throw new IllegalArgumentException();
        List<GameTreeNode> children = new ArrayList<>();
        for (Move move : board.getAvailableMoves())
            children.add(new GameTreeNode(board, move, depth-1));
        this.children = ImmutableList.copyOf(children);
    }
}

