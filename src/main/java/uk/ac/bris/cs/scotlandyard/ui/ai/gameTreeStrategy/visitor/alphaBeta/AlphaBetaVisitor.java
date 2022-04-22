package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.alphaBeta;

import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.InnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.LeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.Iterator;
import java.util.Objects;

public class AlphaBetaVisitor implements GameTreeVisitor {
    private static final int POSITIVE_INFINITY =  10000000;
    private static final int NEGATIVE_INFINITY = -10000000;

    private final boolean maximise;

    private final int alpha;
    private final int beta;

    private final StaticPosEvalStrategy evalStrategy;

    public AlphaBetaVisitor(boolean maximise, int alpha, int beta, StaticPosEvalStrategy evalStrategy) {
        this.maximise = maximise;
        this.alpha = alpha;
        this.beta = beta;
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
        int alpha = this.alpha;
        Iterator<GameTreeNode> iterator = node.getChildren().iterator();
        boolean prune = false;
        while (iterator.hasNext() && (!prune)) {
            GameTreeNode child = iterator.next();
            // this node maximises so child minimises
            boolean maximise = false;
            AlphaBetaVisitor visitor = new AlphaBetaVisitor(maximise, alpha, beta, evalStrategy);
            int childEvaluation = child.accept(visitor);
            alpha = Math.max(alpha, childEvaluation);
            if (alpha >= beta)
                prune = true;
            else
                evaluation = Math.max(evaluation, childEvaluation);
        }
        return evaluation;
    }

    private int minimiseEvaluation(InnerNode node) {
        int evaluation = POSITIVE_INFINITY;
        int beta = this.beta;
        Iterator<GameTreeNode> iterator = node.getChildren().iterator();
        boolean prune = false;
        while (iterator.hasNext() && (!prune)) {
            GameTreeNode child = iterator.next();
            // this node minimises so child maximises
            boolean maximise = true;
            AlphaBetaVisitor visitor = new AlphaBetaVisitor(maximise, alpha, beta, evalStrategy);
            int childEvaluation = child.accept(visitor);
            beta = Math.min(beta, childEvaluation);
            if (beta <= alpha)
                prune = true;
            else
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
