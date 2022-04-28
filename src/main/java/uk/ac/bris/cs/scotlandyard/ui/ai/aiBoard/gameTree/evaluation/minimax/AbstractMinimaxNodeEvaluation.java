package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.minimax;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.AbstractNodeEvaluation;

import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class AbstractMinimaxNodeEvaluation extends AbstractNodeEvaluation {
    public AbstractMinimaxNodeEvaluation(boolean maximise) {
        super(maximise);
    }

    public Move evaluate(GameTree tree) {
        Pair<Optional<Move>, Integer> evaluation = initialEvaluation();
        for (Node node : tree.getChildren()) {
            Pair<Optional<Move>, Integer> nodeEvaluation = node.accept(this);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        if (evaluation.left().isEmpty())
            throw new NoSuchElementException("Expected move");
        return evaluation.left().get();
    }
}
