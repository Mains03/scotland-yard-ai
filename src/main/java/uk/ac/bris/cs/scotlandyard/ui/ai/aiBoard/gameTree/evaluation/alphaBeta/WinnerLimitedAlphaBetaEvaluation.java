package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.alphaBeta;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class WinnerLimitedAlphaBetaEvaluation extends AbstractAlphaBetaNodeEvaluation {
    private static WinnerLimitedAlphaBetaEvaluation instance;

    public static WinnerLimitedAlphaBetaEvaluation getInstance() {
        if (instance == null)
            instance = new WinnerLimitedAlphaBetaEvaluation();
        return instance;
    }

    private WinnerLimitedAlphaBetaEvaluation() {
        super(true);
    }

    private WinnerLimitedAlphaBetaEvaluation(boolean maximise, int alpha, int beta) {
        super(maximise, alpha, beta);
    }

    @Override
    public int evaluate(AiBoard board) {
        return 1;
    }

    @Override
    public Pair<Optional<Move>, Integer> evaluateChildren(Set<Node> children) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Iterator<Node> iterator = children.iterator();
        boolean prune = false;
        while (iterator.hasNext() && (!prune)) {
            Node node = iterator.next();
            Node.Visitor<Pair<Optional<Move>, Integer>> visitor = new WinnerLimitedAlphaBetaEvaluation(!maximise, getAlpha(), getBeta());
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(visitor);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
            updateAlphaBeta(evaluation);
            prune = shouldPrune();
        }
        // add one to count the depth of the tree
        return new Pair<>(evaluation.left(), evaluation.right()+1);
    }
}
