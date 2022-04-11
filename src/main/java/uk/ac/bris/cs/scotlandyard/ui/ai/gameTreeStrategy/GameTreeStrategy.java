package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Creates a game tree and evaluates it to find the best move. This is a
 * BestMoveStrategy.
 */
public class GameTreeStrategy implements BestMoveStrategy {
    private static final int MAX_DEPTH = 3;

    private final Map<Move, GameTreeDataStructure> children;
    private final StaticPositionEvaluationStrategy evaluationStrategy;

    /**
     * GameTreeStrategy constructor.
     * @param board the board
     * */
    public GameTreeStrategy(
            Board board,
            StaticPositionEvaluationStrategy evaluationStrategy
    ) {
        children = createChildren(Objects.requireNonNull(board));
        this.evaluationStrategy = Objects.requireNonNull(evaluationStrategy);
    }

    private Map<Move, GameTreeDataStructure> createChildren(Board board) {
        Map<Move, GameTreeDataStructure> children = new HashMap<>();
        GameTreeBoard gameTreeBoard = new GameTreeBoardAdapter(board);
        for (Move availableMove : gameTreeBoard.getAvailableMoves()) {
            GameTreeBoard childGameTreeBoard = gameTreeBoard.advance(availableMove);
            GameTreeDataStructure child = new GameTreeDataStructure(childGameTreeBoard, MAX_DEPTH-1);
            children.put(availableMove, child);
        }
        return children;
    }

    @Override
    public Move determineBestMove() {
        Move bestMove = null;
        int bestMoveEval = GameTreeDataStructure.NEGATIVE_INFINITY;
        for (Move childMove : children.keySet()) {
            GameTreeDataStructure child = children.get(childMove);
            int childEval = child.evaluate(false, evaluationStrategy);
            if (childEval > bestMoveEval) {
                bestMove = childMove;
                bestMoveEval = childEval;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }
}
