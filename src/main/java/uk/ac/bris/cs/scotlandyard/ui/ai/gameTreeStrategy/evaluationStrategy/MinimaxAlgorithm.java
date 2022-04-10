package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeDataStructure;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeEvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Minimax algorithm.
 */
public class MinimaxAlgorithm implements GameTreeEvaluationStrategy {
    // evaluation strategy to use at leaf nodes
    private final StaticPositionEvaluationStrategy strategy;

    public MinimaxAlgorithm(StaticPositionEvaluationStrategy strategy) {
        Objects.requireNonNull(strategy);
        this.strategy = strategy;
    }

    @Override
    public Move evaluateGameTree(GameTreeDataStructure gameTree) {
        MinimaxTreeEvaluation treeEvaluation = new MinimaxTreeEvaluation(true);
        for (GameTreeDataStructure child : gameTree.getChildren()) {
            int eval = evaluateChild(child, false);
            treeEvaluation = treeEvaluation.updateEvaluation(child.getMove(), eval);
        }
        if (treeEvaluation.getBestMove() == null)
            throw new NoSuchElementException("No moves");
        return treeEvaluation.getBestMove();
    }

    private int evaluateChild(GameTreeDataStructure node, boolean maximise) {
        MinimaxNodeEvaluation evaluation = new MinimaxNodeEvaluation(maximise);
        if (hasChildren(node)) {
            for (GameTreeDataStructure child : node.getChildren()) {
                int childEval = evaluateChild(child, !maximise);
                evaluation = evaluation.updateEvaluation(childEval);
            }
            return evaluation.getEvaluation();
        } else
            return staticEvaluation(node);
    }

    protected boolean hasChildren(GameTreeDataStructure node) {
        return node.getChildren().size() > 0;
    }

    protected int staticEvaluation(GameTreeDataStructure node) {
        AiGameState gameState = createAiGameState(node);
        return strategy.evaluate(gameState);
    }

    private AiGameState createAiGameState(GameTreeDataStructure node) {
        return new AiGameStateAdapter(
                node.getMrX(),
                node.getDetectives()
        );
    }
}
