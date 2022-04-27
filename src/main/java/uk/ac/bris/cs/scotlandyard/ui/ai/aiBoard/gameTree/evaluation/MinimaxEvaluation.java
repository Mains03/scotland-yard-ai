package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.MinimumDistanceEvaluation;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.*;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * {@link GameTreeEvaluationStrategy} using minimax algorithm.
 */
public class MinimaxEvaluation implements GameTreeEvaluationStrategy, Node.Visitor<Pair<Optional<Move>,Integer>> {
    private static MinimaxEvaluation instance;

    public static MinimaxEvaluation getInstance() {
        if (instance == null)
            instance = new MinimaxEvaluation();
        return instance;
    }

    private static final int POSITIVE_INFINITY = Integer.MAX_VALUE;
    private static final int NEGATIVE_INFINITY = Integer.MIN_VALUE;

    private final EvaluationStrategy strategy = MinimumDistanceEvaluation.getInstance();

    private final boolean maximise;

    protected MinimaxEvaluation() {
        maximise = true;
    }

    private MinimaxEvaluation(boolean maximise) {
        this.maximise = maximise;
    }

    @Override
    public Move evaluate(GameTree tree) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Node.Visitor<Pair<Optional<Move>, Integer>> visitor = new MinimaxEvaluation(!maximise);
        for (Node node : tree.children) {
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(visitor);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        if (evaluation.left().isEmpty())
            throw new NoSuchElementException("No move");
        return evaluation.left().get();
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(InnerNodeWithMove node) {
        int moveEvaluation = visit((InnerNode) node).right();
        return new Pair<>(Optional.of(node.move), moveEvaluation);
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(InnerNode node) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Node.Visitor<Pair<Optional<Move>, Integer>> visitor = new MinimaxEvaluation(!maximise);
        for (Node childNode : node.getChildren()) {
            Pair<Optional<Move>, Integer> childNodeEvaluation = childNode.accept(visitor);
            evaluation = updateEvaluation(evaluation, childNodeEvaluation);
        }
        return evaluation;
    }

    @Override
    public Pair<Optional<Move>, Integer> visit(LeafNodeWithMove node) {
        int evaluation = visit((LeafNode) node).right();
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

    private Pair<Optional<Move>, Integer> updateEvaluation(Pair<Optional<Move>, Integer> evaluation, Pair<Optional<Move>, Integer> newEvaluation) {
        Optional<Move> mMove = evaluation.left();
        int moveEvaluation = evaluation.right();
        if (maximise) {
            if (newEvaluation.right() > moveEvaluation) {
                mMove = newEvaluation.left();
                moveEvaluation = newEvaluation.right();
            }
        } else {
            if (newEvaluation.right() < moveEvaluation) {
                mMove = newEvaluation.left();
                moveEvaluation = newEvaluation.right();
            }
        }
        return new Pair<>(mMove, moveEvaluation);
    }
}
