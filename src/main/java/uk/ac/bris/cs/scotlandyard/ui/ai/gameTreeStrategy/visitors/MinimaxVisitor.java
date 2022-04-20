package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors;

import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeLeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.Objects;

public class MinimaxVisitor extends GameTreeVisitor {
    private static final int POSITIVE_INFINITY =  10000000;
    private static final int NEGATIVE_INFINITY = -10000000;

    private final boolean maximise;
    private final StaticPosEvalStrategy evalStrategy;

    private MinimaxVisitor(boolean maximise, StaticPosEvalStrategy evalStrategy) {
        this.maximise = maximise;
        this.evalStrategy = Objects.requireNonNull(evalStrategy);
    }

    @Override
    public int visit(GameTreeInnerNode innerNode) {
        int evaluation;
        if (maximise)
            evaluation = maximiseEvaluation(innerNode);
        else
            evaluation = minimiseEvaluation(innerNode);
        return evaluation;
    }

    private int maximiseEvaluation(GameTreeInnerNode node) {
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

    private int minimiseEvaluation(GameTreeInnerNode node) {
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
    public int visit(GameTreeLeafNode leafNode) {
        AiBoardV2 board = leafNode.getBoard();
        AiGameState aiGameState = new AiGameStateAdapter(board);
        return evalStrategy.evaluate(aiGameState);
    }
}
