package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.ArrayList;
import java.util.Collection;

final class GameTreeNode {
    static final int NEGATIVE_INFINITY = -10000000;
    static final int POSITIVE_INFINITY = 10000000;

    private final GameState gameState;
    private final Collection<GameTreeNode> children;

    GameTreeNode(final GameState gameState, int depth) {
        this.gameState = gameState;
        if (depth > 0)
            children = createChildren(gameState, depth);
        else
            children = new ArrayList<>();
    }

    private Collection<GameTreeNode> createChildren(final GameState gameState, int depth) {
        Collection<GameTreeNode> children = new ArrayList<>();
        for (GameState nextGameState : gameState.nextGameStates())
            children.add(new GameTreeNode(nextGameState, depth-1));
        return children;
    }

    int evaluate(boolean maximise) {
        if (children.size() == 0)
            return staticEvaluation();
        int eval;
        if (maximise) {
            eval = NEGATIVE_INFINITY;
            for (GameTreeNode child : children)
                eval = Math.max(eval, child.evaluate(false));
        } else {
            eval = POSITIVE_INFINITY;
            for (GameTreeNode child : children)
                eval = Math.min(eval, child.evaluate(true));
        }
        return eval;
    }

    Move getMove() {
        return gameState.getMove();
    }

    private int staticEvaluation() {
        return gameState.minDistanceBetweenDetectivesAndMrX();
    }
}
