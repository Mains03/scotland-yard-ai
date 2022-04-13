package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeLeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;

import java.util.Iterator;
import java.util.Optional;

public class AlphaBetaVisitor<T> extends GameTreeVisitor<T> {
    private final boolean maximise;
    private final StaticEvalStrategy<T> evalStrategy;

    private int alpha;
    private int beta;

    private Optional<Move> bestMove;
    private int intEvaluation;

    public AlphaBetaVisitor(
            StaticEvalStrategy<T> evalStrategy
    ) {
        this(
                true,
                MinimaxVisitor.NEGATIVE_INFINITY,
                MinimaxVisitor.POSITIVE_INFINITY,
                evalStrategy
        );
    }

    private AlphaBetaVisitor(
            boolean maximise,
            int alpha,
            int beta,
            StaticEvalStrategy<T> evalStrategy
    ) {
        this.maximise = maximise;
        this.alpha = alpha;
        this.beta = beta;
        this.evalStrategy = evalStrategy;
        bestMove = Optional.empty();
    }

    @Override
    public void visit(GameTreeInnerNode<T> innerNode) {
        if (maximise) {
            intEvaluation = MinimaxVisitor.NEGATIVE_INFINITY;
            Iterator<GameTree<T>> childIterator = innerNode.getChildren().iterator();
            boolean prune = false;
            while (childIterator.hasNext() && (!prune)) {
                GameTree<T> child = childIterator.next();
                AlphaBetaVisitor<T> visitor = new AlphaBetaVisitor<>(
                        false, alpha, beta, evalStrategy
                );
                child.accept(visitor);
                alpha = Math.max(alpha, visitor.alpha);
                if (beta <= alpha)
                    prune = true;
                else {
                    if (visitor.intEvaluation > intEvaluation) {
                        bestMove = visitor.bestMove;
                        intEvaluation = visitor.intEvaluation;
                    }
                }
            }
        } else {
            intEvaluation = MinimaxVisitor.POSITIVE_INFINITY;
            Iterator<GameTree<T>> childIterator = innerNode.getChildren().iterator();
            boolean prune = false;
            while (childIterator.hasNext() && (!prune)) {
                GameTree<T> child = childIterator.next();
                AlphaBetaVisitor<T> visitor = new AlphaBetaVisitor<>(
                        true, alpha, beta, evalStrategy
                );
                child.accept(visitor);
                beta = Math.min(beta, visitor.beta);
                if (alpha <= beta)
                    prune = true;
                else {
                    if (visitor.intEvaluation < intEvaluation) {
                        bestMove = visitor.bestMove;
                        intEvaluation = visitor.intEvaluation;
                    }
                }
            }
        }
    }

    @Override
    public void visit(GameTreeLeafNode<T> leafNode) {
        intEvaluation = evalStrategy.staticEvaluation(leafNode.getData());
    }
}
