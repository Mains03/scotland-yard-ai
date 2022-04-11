package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeDataStructure;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class AlphaBetaGameTreeDataStructure extends GameTreeDataStructure {
    public AlphaBetaGameTreeDataStructure(GameTreeBoard board, int depth) {
        super(board, depth);
    }

    @Override
    protected GameTreeDataStructure createChild(GameTreeBoard board, int depth) {
        return new AlphaBetaGameTreeDataStructure(board, depth);
    }

    @Override
    public int evaluate(boolean maximise, StaticPositionEvaluationStrategy evaluationStrategy) {
        throw new UnsupportedOperationException();
    }

    public int evaluate(boolean maximise, int alpha, int beta, StaticPositionEvaluationStrategy evaluationStrategy) {
        if (children.isEmpty())
            return staticEvaluation(evaluationStrategy);
        else {
            Set<AlphaBetaGameTreeDataStructure> children = this.children.get().stream()
                    .map(child -> (AlphaBetaGameTreeDataStructure) child)
                    .collect(Collectors.toSet());
            Iterator<AlphaBetaGameTreeDataStructure> childrenIterator = children.iterator();
            int eval;
            if (maximise) {
                eval = NEGATIVE_INFINITY;
                while (childrenIterator.hasNext()) {
                    AlphaBetaGameTreeDataStructure child = childrenIterator.next();
                    int childEval = child.evaluate(false, alpha, beta, evaluationStrategy);
                    alpha = Math.max(alpha, childEval);
                    eval = Math.max(eval, childEval);
                }
            } else {
                eval = POSITIVE_INFINITY;
                boolean shouldPrune = false;
                while (childrenIterator.hasNext() && (!shouldPrune)) {
                    AlphaBetaGameTreeDataStructure child = childrenIterator.next();
                    int childEval = child.evaluate(true, alpha, beta, evaluationStrategy);
                    beta = Math.min(beta, childEval);
                    if (beta <= alpha)
                        shouldPrune = true;
                    else
                        eval = Math.min(eval, childEval);
                }
            }
            return eval;
        }
    }
}
