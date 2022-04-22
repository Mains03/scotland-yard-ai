package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.minimax;

import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.InnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.LeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.Objects;

public class MinimaxVisitor implements GameTreeVisitor {
    private static final int POSITIVE_INFINITY =  10000000;
    private static final int NEGATIVE_INFINITY = -10000000;

    private final boolean maximise;

    private final StaticPosEvalStrategy evalStrategy;

    public MinimaxVisitor(boolean maximise, StaticPosEvalStrategy evalStrategy) {
        this.maximise = maximise;
        this.evalStrategy = Objects.requireNonNull(evalStrategy);
    }

    @Override
    public int visit(InnerNode node) {
        int evaluation;
        if (maximise)
            evaluation = maximiseEvaluation(node);
        else
            evaluation = minimiseEvaluation(node);
        return evaluation;
    }

    private int maximiseEvaluation(InnerNode node) {
        int evaluation = NEGATIVE_INFINITY;
        for (GameTreeNode child : node.getChildren()) {
            // this node maximises so child minimises
            boolean maximise = false;
            MinimaxVisitor visitor = new MinimaxVisitor(maximise, evalStrategy);
            int childEvaluation = child.accept(visitor);
            evaluation = Math.max(evaluation, childEvaluation);
        }
        return evaluation;
    }

    private int minimiseEvaluation(InnerNode node) {
        int evaluation = POSITIVE_INFINITY;
        for (GameTreeNode child : node.getChildren()) {
            // this node minimises so child maximises
            boolean maximise = true;
            MinimaxVisitor visitor = new MinimaxVisitor(maximise, evalStrategy);
            int childEvaluation = child.accept(visitor);
            evaluation = Math.min(evaluation, childEvaluation);
        }
        return evaluation;
    }

    @Override
    public int visit(LeafNode node) {
        AiBoard board = node.getBoard();
        return evalStrategy.evaluate(board);
    }
}
