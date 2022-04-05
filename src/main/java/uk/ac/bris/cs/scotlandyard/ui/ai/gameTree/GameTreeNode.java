package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A node in the game tree representing a state of the game.
 * Each state is either MrX to move or all detectives to move.
 */
public class GameTreeNode {
    static final int NEGATIVE_INFINITY = -10000000;
    static final int POSITIVE_INFINITY = 10000000;

    private final GameState gameState;
    private final Collection<GameTreeNode> children;

    /**
     * GameTreeNode constructor
     * @param gameState state of the game for this node
     * @param depth remaining depth of the tree
     */
    public GameTreeNode(final GameState gameState, int depth) {
        System.out.println("depth: " + depth);
        Objects.requireNonNull(gameState);
        this.gameState = gameState;
        if (depth > 0) {
            // create the children
            children = new ArrayList<>();
            Collection<GameState> gameStateChildren = gameState.nextGameStates();
            for (GameState nextGameState : gameStateChildren)
                children.add(new GameTreeNode(nextGameState, depth-1));
        } else
            children = new ArrayList<>();
    }

    /**
     * Generate an integer evaluation of this state.
     * @param maximise whether the score should be maximised
     * @return integer evaluation
     */
    int evaluate(boolean maximise) {
        if (children.size() == 0)
            return gameState.staticEvaluation();
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

    /**
     * Delegates to the game state instance.
     * @return the moves made
     */
    List<Move> getMove() {
        return gameState.getMoves();
    }
}
