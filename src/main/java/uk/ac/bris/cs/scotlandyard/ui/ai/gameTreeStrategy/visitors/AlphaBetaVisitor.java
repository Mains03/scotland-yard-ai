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
    private final GameTreeLeafNode.StaticEvalStrategy<T> evalStrategy;

    private int alpha;
    private int beta;

    private int intEvaluation;

    public AlphaBetaVisitor(
            GameTreeLeafNode.StaticEvalStrategy<T> evalStrategy
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
            GameTreeLeafNode.StaticEvalStrategy<T> evalStrategy
    ) {
        this.maximise = maximise;
        this.alpha = alpha;
        this.beta = beta;
        this.evalStrategy = evalStrategy;
    }

    @Override
    public Optional<Move> visit(GameTreeInnerNode<T> innerNode) {
        Optional<Move> bestMove = Optional.empty();
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
                        bestMove = child.mrXMoveMade();
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
                        bestMove = child.mrXMoveMade();
                        intEvaluation = visitor.intEvaluation;
                    }
                }
            }
        }
        return bestMove;
    }

    @Override
    public Optional<Move> visit(GameTreeLeafNode<T> leafNode) {
        intEvaluation = evalStrategy.staticEvaluation(leafNode.getData());
        return leafNode.mrXMoveMade();
    }
}
