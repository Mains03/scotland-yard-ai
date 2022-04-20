package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class MinimaxAlgorithm implements BestMoveStrategy {
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
            int nodeEvaluation = evaluateNode(node);
            if (nodeEvaluation > bestMoveEval) {
                bestMove = getMrXMove(node);
                bestMoveEval = nodeEvaluation;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }

    private Move getMrXMove(GameTreeNode node) {
        Optional<Move> move = node.mrXMoveMade();
        if (move.isEmpty())
            throw new NoSuchElementException("Expected MrX move");
        return move.get();
    }

    private int evaluateNode(GameTreeNode node) {
        // MrX made move so detectives to move
        boolean maximise = false;
        GameTreeVisitor visitor = new MinimaxVisitor(maximise, strategy);
        return node.accept(visitor);
    }
}
