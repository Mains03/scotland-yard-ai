package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * A node in the game tree representing a state of the game.
 * Each state is either MrX to move or all detectives to move.
 */
final class GameTreeNode {
    static final int NEGATIVE_INFINITY = -10000000;
    static final int POSITIVE_INFINITY = 10000000;

    private final GameState gameState;
    private final Collection<GameTreeNode> children;

    /**
     * GameTreeNode constructor
     * @param gameState state of the game for this node
     * @param depth remaining depth of the tree
     */
    GameTreeNode(final GameState gameState, int depth) {
        Objects.requireNonNull(gameState);
        this.gameState = gameState;
        if (depth > 0)
            children = createChildren(gameState, depth);
        else
            children = new ArrayList<>();
    }

    private Collection<GameTreeNode> createChildren(final GameState gameState, int depth) {
        Collection<GameTreeNode> children = new ArrayList<>();
        Collection<GameState> gameStateChildren = GameStateFactory.getInstance().nextGameStates(gameState);
        for (GameState nextGameState : gameStateChildren)
            children.add(new GameTreeNode(nextGameState, depth-1));
        return children;
    }

    /**
     * Generate an integer evaluation of this state.
     * @param maximise whether the score should be maximised
     * @return integer evaluation
     */
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
