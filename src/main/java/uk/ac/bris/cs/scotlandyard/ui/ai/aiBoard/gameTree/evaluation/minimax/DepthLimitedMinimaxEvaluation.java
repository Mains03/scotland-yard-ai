package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.minimax;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.*;

import java.util.Optional;
import java.util.Set;

public class DepthLimitedMinimaxEvaluation extends AbstractMinimaxNodeEvaluation{
    private static DepthLimitedMinimaxEvaluation instance;

    public static DepthLimitedMinimaxEvaluation getInstance() {
        if (instance == null)
            instance = new DepthLimitedMinimaxEvaluation();
        return instance;
    }

    private DepthLimitedMinimaxEvaluation() {
        super(true);
    }

    private DepthLimitedMinimaxEvaluation(boolean maximise) {
        super(maximise);
    }

    @Override
    public int evaluate(AiBoard board) {
        return EvaluationStrategy.getInstance().evaluate(board);
    }

    @Override
    public Pair<Optional<Move>, Integer> evaluateChildren(Set<Node> children) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Node.Visitor<Pair<Optional<Move>, Integer>> visitor = new DepthLimitedMinimaxEvaluation(!maximise);
        for (Node node : children) {
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(visitor);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        return evaluation;
    }
}
