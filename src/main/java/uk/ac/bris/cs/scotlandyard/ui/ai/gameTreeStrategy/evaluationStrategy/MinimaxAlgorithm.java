package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeDataStructure;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeEvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Minimax algorithm.
 */
public class MinimaxAlgorithm implements GameTreeEvaluationStrategy {
    private static final int POSITIVE_INFINITY = 1000000;
    private static final int NEGATIVE_INFINITY = -1000000;

    private final StaticPositionEvaluationStrategy strategy;

    public MinimaxAlgorithm(StaticPositionEvaluationStrategy strategy) {
        Objects.requireNonNull(strategy);
        this.strategy = strategy;
    }

    @Override
    public Move evaluateGameTree(GameTreeDataStructure gameTree) {
        Move bestMove = null;
        int bestMoveEvaluation = NEGATIVE_INFINITY;
        for (GameTreeDataStructure child : gameTree.getChildren()) {
            int eval = evaluateChild(child, false);
            if (eval > bestMoveEvaluation) {
                bestMove = child.getMove();
                bestMoveEvaluation = eval;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }

    private int evaluateChild(GameTreeDataStructure node, boolean maximise) {
        int nodeEval = initialEvaluation(maximise);
        if (hasChildren(node)) {
            for (GameTreeDataStructure child : node.getChildren()) {
                int childEval = evaluateChild(child, !maximise);
                if (childEvalBetter(nodeEval, childEval, maximise))
                    nodeEval = childEval;
            }
        } else
            nodeEval = staticEvaluation(node);
        return nodeEval;
    }

    private int initialEvaluation(boolean maximise) {
        if (maximise)
            return NEGATIVE_INFINITY;
        else
            return POSITIVE_INFINITY;
    }

    private boolean childEvalBetter(int currentEval, int childEval, boolean maximise) {
        if (maximise)
            return childEval > currentEval;
        else
            return childEval < currentEval;
    }

    private boolean hasChildren(GameTreeDataStructure node) {
        return node.getChildren().size() > 0;
    }

    private int staticEvaluation(GameTreeDataStructure node) {
        AiGameState gameState = createAiGameState(node);
        return strategy.evaluate(gameState);
    }

    private AiGameState createAiGameState(GameTreeDataStructure node) {
        return new AiGameStateAdapter(
                node.getMrX(),
                node.getDetectives()
        );
    }
}
