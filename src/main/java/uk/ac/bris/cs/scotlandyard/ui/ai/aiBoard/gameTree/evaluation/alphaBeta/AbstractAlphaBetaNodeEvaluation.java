package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.alphaBeta;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.AbstractInnerNodeWithMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Not finished.
 */
public abstract class AbstractAlphaBetaNodeEvaluation implements Node.Visitor<AlphaBetaData> {
    private final static int POSITIVE_INFINITY = Integer.MAX_VALUE;
    private final static int NEGATIVE_INFINITY = Integer.MIN_VALUE;

    private final boolean maximise;

    protected int alpha;
    protected int beta;

    public AbstractAlphaBetaNodeEvaluation(boolean maximise) {
        this.maximise = maximise;
        alpha = NEGATIVE_INFINITY;
        beta = POSITIVE_INFINITY;
    }

    public Move evaluate(GameTree tree) {
        AlphaBetaData evaluation = initialEvaluation();
        for (Node node : tree.getChildren()) {
            AlphaBetaData nodeEvaluation = node.accept(this);
            evaluation = updateEvaluation(evaluation, nodeEvaluation);
        }
        if (evaluation.getMove().isEmpty())
            throw new NoSuchElementException("No moves");
        return evaluation.getMove().get();
    }

    @Override
    public AlphaBetaData visit(AbstractInnerNodeWithMove node) {
        return null;
    }

    private AlphaBetaData initialEvaluation() {
        if (maximise)
            return new AlphaBetaData(Optional.empty(), NEGATIVE_INFINITY, NEGATIVE_INFINITY, POSITIVE_INFINITY);
        else
            return new AlphaBetaData(Optional.empty(), POSITIVE_INFINITY, NEGATIVE_INFINITY, POSITIVE_INFINITY);
    }

    private AlphaBetaData updateEvaluation(AlphaBetaData data, AlphaBetaData newData) {
        AlphaBetaData updated;
        if (maximise) {
            if (newData.getEvaluation() > data.getEvaluation())
                updated = new AlphaBetaData(newData);
            else
                updated = new AlphaBetaData(data);
        } else {
            if (newData.getEvaluation() < data.getEvaluation())
                updated = new AlphaBetaData(newData);
            else
                updated = new AlphaBetaData(data);
        }
        return updated;
    }
}
