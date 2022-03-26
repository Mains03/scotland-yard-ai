package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Game tree to determine the best move.
 */
public class GameTree {
    private static final int MAX_DEPTH = 1;

    private final List<GameTreeNode> children;

    public GameTree(final Board board) {
        children = createChildren(board);
    }

    private List<GameTreeNode> createChildren(final Board board) {
        List<GameTreeNode> children = new ArrayList<>();
        for (Move move : board.getAvailableMoves()) {
            children.add(new GameTreeNode(
                    GameStateFactory.getInstance().createGameState(board, move),
                    MAX_DEPTH
            ));
        }
        return children;
    }

    public Move determineBestMove() {
        Move bestMove = null;
        int bestMoveEval = GameTreeNode.NEGATIVE_INFINITY;
        for (GameTreeNode child : children) {
            int childEval = child.evaluate(false);
            if (childEval > bestMoveEval) {
                bestMoveEval = childEval;
                bestMove = child.getMove();
            }
        }
        return bestMove;
    }
}

