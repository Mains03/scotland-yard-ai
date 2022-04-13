package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeLeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;

import java.util.Objects;
import java.util.Optional;

public class MinimaxVisitor<T> extends GameTreeVisitor<T> {
    public static final int POSITIVE_INFINITY =  10000000;
    public static final int NEGATIVE_INFINITY = -10000000;

    private final boolean maximise;
    private final StaticEvalStrategy<T> evalStrategy;

    private Optional<Move> bestMove;
    private int intEvaluation;

    public MinimaxVisitor(
            StaticEvalStrategy<T> evalStrategy
    ) {
        this(true, evalStrategy);
    }

    private MinimaxVisitor(
            boolean maximise,
            StaticEvalStrategy<T> evalStrategy
    ) {
        this.maximise = maximise;
        this.evalStrategy = Objects.requireNonNull(evalStrategy);
        bestMove = Optional.empty();
    }

    @Override
    public void visit(GameTreeInnerNode<T> innerNode) {
        if (maximise) {
            intEvaluation = NEGATIVE_INFINITY;
            for (GameTree<T> child : innerNode.getChildren()) {
                MinimaxVisitor<T> visitor = new MinimaxVisitor<>(false, evalStrategy);
                child.accept(visitor);
                if (visitor.intEvaluation > intEvaluation) {
                    bestMove = visitor.bestMove;
                    intEvaluation = visitor.intEvaluation;
                }
            }
        } else {
            intEvaluation = POSITIVE_INFINITY;
            for (GameTree<T> child : innerNode.getChildren()) {
                MinimaxVisitor<T> visitor = new MinimaxVisitor<>(true, evalStrategy);
                child.accept(visitor);
                if (visitor.intEvaluation < intEvaluation) {
                    bestMove = visitor.bestMove;
                    intEvaluation = visitor.intEvaluation;
                }
            }
        }
    }

    @Override
    public void visit(GameTreeLeafNode<T> leafNode) {
        intEvaluation = evalStrategy.staticEvaluation(leafNode.getData());
    }
}
