package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class MinimaxDepthLimitedEvaluation extends MinimaxEvaluation implements Node.Visitor<Pair<Optional<Move>, Integer>> {
    private static MinimaxDepthLimitedEvaluation instance;

    public static MinimaxDepthLimitedEvaluation getInstance() {
        if (instance == null)
            instance = new MinimaxDepthLimitedEvaluation();
        return instance;
    }

    private static final int POSITIVE_INFINITY = Integer.MAX_VALUE;
    private static final int NEGATIVE_INFINITY = Integer.MIN_VALUE;

    private final boolean maximise;

    private MinimaxDepthLimitedEvaluation() {
        maximise = true;
    }

    private MinimaxDepthLimitedEvaluation(boolean maximise) {
        this.maximise = maximise;
    }

    public Move evaluate(DepthLimitedGameTree tree) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        for (Node node : tree.children) {
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(this);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        if (evaluation.left().isEmpty())
            throw new NoSuchElementException("No moves");
        return evaluation.left().get();
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(InnerNodeWithMove node) {
        int moveEvaluation = evaluateChildren(node.getChildren()).right();
        return new Pair<>(Optional.of(node.move), moveEvaluation);
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(InnerNode node) {
        return evaluateChildren(node.getChildren());
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(LeafNodeWithMove node) {
        int evaluation = strategy.evaluate(node.board);
        return new Pair<>(Optional.of(node.move), evaluation);
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(LeafNode node) {
        int evaluation = strategy.evaluate(node.board);
        return new Pair<>(Optional.empty(), evaluation);
    }

    private Pair<Optional<Move>, Integer> initialEvaluation() {
        Optional<Move> mMove = Optional.empty();
        int evaluation;
        if (maximise)
            evaluation = NEGATIVE_INFINITY;
        else
            evaluation = POSITIVE_INFINITY;
        return new Pair<>(mMove, evaluation);
    }

    private Pair<Optional<Move>, Integer> evaluateChildren(Set<Node> children) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Node.Visitor<Pair<Optional<Move>, Integer>> visitor = new MinimaxDepthLimitedEvaluation(!maximise);
        for (Node node : children) {
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(visitor);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        return evaluation;
    }

    private Pair<Optional<Move>, Integer> updateEvaluation(Pair<Optional<Move>, Integer> evaluation, Pair<Optional<Move>, Integer> newEvaluation) {
        Pair<Optional<Move>, Integer> updated;
        if (maximise) {
            if (newEvaluation.right() > evaluation.right())
                updated = newEvaluation;
            else
                updated = evaluation;
        } else {
            if (newEvaluation.right() < evaluation.right())
                updated = newEvaluation;
            else
                updated = evaluation;
        }
        return updated;
    }
}
