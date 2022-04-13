package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.minimax;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeLeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;

import java.util.Objects;
import java.util.Optional;

public class MinimaxVisitor<T> extends GameTreeVisitor<T> {
    private static final int POSITIVE_INFINITY =  10000000;
    private static final int NEGATIVE_INFINITY = -10000000;

    private final boolean maximise;
    private final MinimaxStaticEvalStrategy<T> evalStrategy;

    private Optional<Move> bestMove;
    private int intEvaluation;

    public MinimaxVisitor(
            boolean maximise,
            MinimaxStaticEvalStrategy<T> evalStrategy
    ) {
        this.maximise = maximise;
        this.evalStrategy = Objects.requireNonNull(evalStrategy);
        bestMove = Optional.empty();
    }

    @Override
    public void visit(GameTreeInnerNode innerNode) {
        if (maximise) {
            intEvaluation = NEGATIVE_INFINITY;
            for (GameTree<T> child : innerNode.getChildren()) {
                MinimaxVisitor<T> childVisitor = new MinimaxVisitor<>(false, evalStrategy);
                child.accept(childVisitor);
                if (childVisitor.intEvaluation > intEvaluation) {
                    bestMove = childVisitor.bestMove;
                    intEvaluation = childVisitor.intEvaluation;
                }
            }
        } else {
            intEvaluation = POSITIVE_INFINITY;
            for (GameTree<T> child : innerNode.getChildren()) {
                MinimaxVisitor<T> childVisitor = new MinimaxVisitor<>(true, evalStrategy);
                child.accept(childVisitor);
                if (childVisitor.intEvaluation < intEvaluation) {
                    bestMove = childVisitor.bestMove;
                    intEvaluation = childVisitor.intEvaluation;
                }
            }
        }
    }

    @Override
    public void visit(GameTreeLeafNode<T> leafNode) {
        intEvaluation = evalStrategy.staticEvaluation(leafNode.getData());
    }
}
