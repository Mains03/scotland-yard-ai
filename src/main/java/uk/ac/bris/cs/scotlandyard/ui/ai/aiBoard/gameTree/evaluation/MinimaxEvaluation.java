package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.*;

import java.util.Objects;

public class MinimaxEvaluation implements GameTreeEvaluationStrategy {
    public static final int POSITIVE_INFINITY =  10000000;
    public static final int NEGATIVE_INFINITY = -10000000;

    private final EvaluationStrategy strategy;

    private final boolean maximise;

    public MinimaxEvaluation(EvaluationStrategy strategy, boolean maximise) {
        this.strategy = Objects.requireNonNull(strategy);
        this.maximise = maximise;
    }

    @Override
    public Integer visit(GameTree tree) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer visit(InnerNodeWithMove node) {
        return visit((InnerNode) node);
    }

    @Override
    public Integer visit(InnerNode node) {
        int evaluation = initialEvaluation();
        Node.Visitor<Integer> visitor = new MinimaxEvaluation(strategy, !maximise);
        for (Node childNode : node.getChildren()) {
            int childNodeEvaluation = childNode.accept(visitor);
            evaluation = updateEvaluation(evaluation, childNodeEvaluation);
        }
        return evaluation;
    }

    @Override
    public Integer visit(LeafNodeWithMove node) {
        return visit((LeafNode) node);
    }

    @Override
    public Integer visit(LeafNode node) {
        return node.board.accept(strategy);
    }

    private int initialEvaluation() {
        if (maximise)
            return NEGATIVE_INFINITY;
        else
            return POSITIVE_INFINITY;
    }

    private int updateEvaluation(int evaluation, int newEvaluation) {
        if (maximise)
            return Math.max(evaluation, newEvaluation);
        else
            return Math.min(evaluation, newEvaluation);
    }
}
