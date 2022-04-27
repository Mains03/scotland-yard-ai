package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractMinimaxNodeEvaluation implements Node.Visitor<Pair<Optional<Move>, Integer>> {
    private final static int POSITIVE_INFINITY = Integer.MAX_VALUE;
    private final static int NEGATIVE_INFINITY = Integer.MIN_VALUE;

    protected final boolean maximise;

    public AbstractMinimaxNodeEvaluation(boolean maximise) {
        this.maximise = maximise;
    }

    public Move evaluate(GameTree tree) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        for (Node node : tree.getChildren()) {
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(this);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        if (evaluation.left().isEmpty())
            throw new NoSuchElementException("No moves");
        return evaluation.left().get();
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(AbstractInnerNodeWithMove node) {
        int moveEvaluation = evaluateChildren(node.getChildren()).right();
        return new Pair<>(Optional.of(node.getMove()), moveEvaluation);
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(AbstractInnerNode node) {
        return evaluateChildren(node.getChildren());
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(AbstractLeafNodeWithMove node) {
        int evaluation = evaluate(node.getBoard());
        return new Pair<>(Optional.of(node.getMove()), evaluation);
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(AbstractLeafNode node) {
        int evaluation = evaluate(node.getBoard());
        return new Pair<>(Optional.empty(), evaluation);
    }

    public abstract int evaluate(AiBoard board);

    protected Pair<Optional<Move>, Integer> initialEvaluation() {
        if (maximise)
            return new Pair<>(Optional.empty(), NEGATIVE_INFINITY);
        else
            return new Pair<>(Optional.empty(), POSITIVE_INFINITY);
    }

    public abstract Pair<Optional<Move>, Integer> evaluateChildren(Set<Node> children);

    protected Pair<Optional<Move>, Integer> updateEvaluation(
            Pair<Optional<Move>, Integer> evaluation, Pair<Optional<Move>, Integer> newEvaluation
    ) {
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
