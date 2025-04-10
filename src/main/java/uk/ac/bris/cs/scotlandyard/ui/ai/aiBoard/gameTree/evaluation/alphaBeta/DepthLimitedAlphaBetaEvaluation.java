package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.alphaBeta;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class DepthLimitedAlphaBetaEvaluation extends AbstractAlphaBetaNodeEvaluation {
    private static DepthLimitedAlphaBetaEvaluation instance;

    public static DepthLimitedAlphaBetaEvaluation getInstance() {
        if (instance == null)
            instance = new DepthLimitedAlphaBetaEvaluation();
        return instance;
    }

    private DepthLimitedAlphaBetaEvaluation() {
        super(true);
    }

    private DepthLimitedAlphaBetaEvaluation(boolean maximise, int alpha, int beta) {
        super(maximise, alpha, beta);
    }

    @Override
    public int evaluate(AiBoard board) {
        return EvaluationStrategy.getInstance().evaluate(board);
    }

    @Override
    public Pair<Optional<Move>, Integer> evaluateChildren(Set<Node> children) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Iterator<Node> iterator = children.iterator();
        boolean prune = false;
        while (iterator.hasNext() && (!prune)) {
            Node node = iterator.next();
            Node.Visitor<Pair<Optional<Move>, Integer>> visitor = new DepthLimitedAlphaBetaEvaluation(!maximise, getAlpha(), getBeta());
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(visitor);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
            updateAlphaBeta(evaluation);
            prune = shouldPrune();
        }
        return evaluation;
    }
}
