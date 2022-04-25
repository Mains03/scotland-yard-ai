package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.alphaBeta;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class AlphaBetaStrategy implements BestMoveStrategy {
    private static final int POSITIVE_INFINITY =  10000000;
    private static final int NEGATIVE_INFINITY = -10000000;

    private final StaticPosEvalStrategy strategy;

    public AlphaBetaStrategy(StaticPosEvalStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public Move determineBestMove(StandardAiBoard board) {
        GameTree gameTree = new GameTree(board);
        Move bestMove = null;
        int bestMoveEval = NEGATIVE_INFINITY;
        int alpha = NEGATIVE_INFINITY;
        int beta = POSITIVE_INFINITY;
        for (GameTreeNode node : gameTree.getGameTreeNodes()) {
            int nodeEvaluation = evaluateNode(node, alpha, beta);
            alpha = Math.max(alpha, nodeEvaluation);
            if (nodeEvaluation > bestMoveEval) {
                bestMove = getMrXMove(node);
                bestMoveEval = nodeEvaluation;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves");
        return bestMove;
    }

    private int evaluateNode(GameTreeNode node, int alpha, int beta) {
        // MrX made move so detectives to move
        boolean maximise = false;
        GameTreeVisitor visitor = new AlphaBetaVisitor(maximise, alpha, beta, strategy);
        return node.accept(visitor);
    }

    private Move getMrXMove(GameTreeNode node) {
        Optional<Move> move = node.getMrXMove();
        if (move.isEmpty())
            throw new NoSuchElementException("Expected MrX move");
        return move.get();
    }
}
