package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategyV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.Objects;

public class MinimaxAlgorithm implements BestMoveStrategyV2 {
    private static final int NEGATIVE_INFINITY = -10000000;

    private final StaticPosEvalStrategy strategy;

    public MinimaxAlgorithm(StaticPosEvalStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public Move determineBestMove(Board board) {
        GameTree gameTree = new GameTree(board);
        Move bestMove = null;
        int bestMoveEval = NEGATIVE_INFINITY;
        for (GameTreeNode node : gameTree.getGameTreeNodes()) {

        }
        return null;
    }

    private int evaluateNode(GameTreeNode node) {
        // MrX made move so detectives to move
        boolean maximise = false;
        GameTreeVisitor visitor = new MinimaxVisitor()
    }
}
