package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeDataStructure;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeEvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class AlphaBetaPruning extends MinimaxAlgorithm implements GameTreeEvaluationStrategy   {
    private static final int POSITIVE_INFINITY = 1000000;
    private static final int NEGATIVE_INFINITY = -1000000;

    public AlphaBetaPruning(StaticPositionEvaluationStrategy strategy) {
        super(strategy);
    }

    @Override
    public Move evaluateGameTree(GameTreeDataStructure gameTree) {
        Move bestMove = null;
        AlphaBetaTreeEvaluation treeEvaluation = new AlphaBetaTreeEvaluation(true);
        Iterator<GameTreeDataStructure> childIterator = gameTree.getChildren().iterator();
        boolean prune = false;
        while (childIterator.hasNext() && !prune) {
            GameTreeDataStructure child = childIterator.next();
            int eval = evaluateChild(
                    child, treeEvaluation
            );
            treeEvaluation = treeEvaluation.updateEvaluation(child.getMove(), eval);
            prune = treeEvaluation.shouldPrune();
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }

    private int evaluateChild(
            GameTreeDataStructure node,
            AlphaBetaNodeEvaluation nodeEvaluation
    ) {
        if (hasChildren(node)) {
            Iterator<GameTreeDataStructure> childIterator = node.getChildren().iterator();
            boolean prune = false;
            while (childIterator.hasNext() && !prune) {
                GameTreeDataStructure child = childIterator.next();
                int childEval = evaluateChild(child, nodeEvaluation);
                nodeEvaluation = nodeEvaluation.updateEvaluation()
            }
        } else
            return staticEvaluation(node);
    }
}
