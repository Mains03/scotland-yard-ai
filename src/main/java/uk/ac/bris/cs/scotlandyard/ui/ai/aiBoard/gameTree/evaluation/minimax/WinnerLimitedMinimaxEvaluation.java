package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.minimax;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.*;

import java.util.Optional;
import java.util.Set;

public class WinnerLimitedMinimaxEvaluation extends AbstractMinimaxNodeEvaluation implements Node.Visitor<Pair<Optional<Move>, Integer>> {
    private static WinnerLimitedMinimaxEvaluation instance;

    public static WinnerLimitedMinimaxEvaluation getInstance() {
        if (instance == null)
            instance = new WinnerLimitedMinimaxEvaluation();
        return instance;
    }

    private WinnerLimitedMinimaxEvaluation() {
        super(true);
    }

    private WinnerLimitedMinimaxEvaluation(boolean maximise) {
        super(maximise);
    }

    @Override
    public int evaluate(AiBoard board) {
        return 1;
    }

    @Override
    public Pair<Optional<Move>, Integer> evaluateChildren(Set<Node> children) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        Node.Visitor<Pair<Optional<Move>, Integer>> visitor = new WinnerLimitedMinimaxEvaluation(!maximise);
        for (Node node : children) {
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(visitor);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        // add one to count the height of the tree
        return new Pair<>(evaluation.left(), evaluation.right()+1);
    }
}
