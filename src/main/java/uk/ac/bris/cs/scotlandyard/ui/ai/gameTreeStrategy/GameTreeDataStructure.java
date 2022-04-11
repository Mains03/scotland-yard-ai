package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.*;

/**
 * A node in the game tree.
 */
public class GameTreeDataStructure {
    static int POSITIVE_INFINITY = 1000000;
    static int NEGATIVE_INFINITY = -1000000;

    private final GameTreeBoard board;
    private final Optional<Set<GameTreeDataStructure>> children;

    public GameTreeDataStructure(GameTreeBoard board, int depth) {
        this.board = board;
        children = generateChildren(board, depth);
    }

    private Optional<Set<GameTreeDataStructure>> generateChildren(GameTreeBoard board, int depth) {
        if (depth > 0) {
            Set<GameTreeDataStructure> children = new HashSet<>();
            for (Move availableMove : board.getAvailableMoves()) {
                GameTreeBoard childBoard = board.advance(availableMove);
                children.add(new GameTreeDataStructure(childBoard, depth - 1));
            }
            return Optional.of(children);
        } else
            return Optional.empty();
    }

    public int evaluate(boolean maximise, StaticPositionEvaluationStrategy evaluationStrategy) {
        if (children.isEmpty())
            return evaluationStrategy.evaluate(board.asAiGameState());
        else {
            // use minimax to evaluate the game tree
            Set<GameTreeDataStructure> children = this.children.get();
            int eval;
            if (maximise) {
                eval = NEGATIVE_INFINITY;
                for (GameTreeDataStructure child : children) {
                    int childEval = child.evaluate(false, evaluationStrategy);
                    eval = Math.max(eval, childEval);
                }
            } else {
                eval = POSITIVE_INFINITY;
                for (GameTreeDataStructure child : children) {
                    int childEval = child.evaluate(true, evaluationStrategy);
                    eval = Math.min(eval, childEval);
                }
            }
            return eval;
        }
    }
}
