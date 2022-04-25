package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.bestMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.MinimaxEvaluation;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * {@link GameTreeBestMoveStrategy} using minimax algorithm.
 */
public class MinimaxBestMove implements GameTreeBestMoveStrategy {
    private final EvaluationStrategy strategy;

    private final boolean maximise;

    public MinimaxBestMove(EvaluationStrategy strategy, boolean maximise) {
        this.strategy = Objects.requireNonNull(strategy);
        this.maximise = maximise;
    }

    @Override
    public Move visit(GameTree tree) {
        Move bestMove = null;
        int bestMoveEvaluation = initialEvaluation();
        Node.Visitor<Integer> visitor = new MinimaxEvaluation(strategy, !maximise);
        for (Node node : tree.getChildren()) {
            int nodeEvaluation = node.accept(visitor);
            if (newEvaluationBetter(bestMoveEvaluation, nodeEvaluation)) {
                bestMove = node.accept(this);
                bestMoveEvaluation = nodeEvaluation;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }

    @Override
    public Move visit(InnerNodeWithMove node) {
        return node.move;
    }

    @Override
    public Move visit(InnerNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Move visit(LeafNodeWithMove node) {
        return node.move;
    }

    @Override
    public Move visit(LeafNode node) {
        throw new UnsupportedOperationException();
    }

    private int initialEvaluation() {
        if (maximise)
            return MinimaxEvaluation.NEGATIVE_INFINITY;
        else
            return MinimaxEvaluation.POSITIVE_INFINITY;
    }

    private boolean newEvaluationBetter(int evaluation, int newEvaluation) {
        if (maximise)
            return newEvaluation > evaluation;
        else
            return newEvaluation < evaluation;
    }
}
