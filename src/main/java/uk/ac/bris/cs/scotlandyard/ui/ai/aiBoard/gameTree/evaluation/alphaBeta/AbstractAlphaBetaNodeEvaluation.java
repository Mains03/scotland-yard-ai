package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.alphaBeta;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.AbstractNodeEvaluation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class AbstractAlphaBetaNodeEvaluation extends AbstractNodeEvaluation {
    private final static int POSITIVE_INFINITY = Integer.MAX_VALUE;
    private final static int NEGATIVE_INFINITY = Integer.MIN_VALUE;

    private int alpha;
    private int beta;

    protected AbstractAlphaBetaNodeEvaluation(boolean maximise) {
        super(maximise);
        alpha = NEGATIVE_INFINITY;
        beta = POSITIVE_INFINITY;
    }

    protected AbstractAlphaBetaNodeEvaluation(boolean maximise, int alpha, int beta) {
        super(maximise);
        this.alpha = alpha;
        this.beta = beta;
    }

    public Move evaluate(GameTree tree) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Iterator<Node> iterator = tree.getChildren().iterator();
        boolean prune = false;
        while (iterator.hasNext() && (!prune)) {
            Node node = iterator.next();
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(this);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
            updateAlphaBeta(evaluation);
            prune = shouldPrune();
        }
        if (evaluation.left().isEmpty())
            throw new NoSuchElementException("Expected move");
        return evaluation.left().get();
    }

    protected void updateAlphaBeta(Pair<Optional<Move>, Integer> evaluation) {
        if (maximise)
            alpha = Math.max(alpha, evaluation.right());
        else
            beta = Math.min(beta, evaluation.right());
    }

    protected boolean shouldPrune() {
        return beta <= alpha;
    }

    protected int getAlpha() {
        return alpha;
    }

    protected int getBeta() {
        return beta;
    }
}
