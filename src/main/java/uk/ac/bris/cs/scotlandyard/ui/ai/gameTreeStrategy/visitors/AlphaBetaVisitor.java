package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeLeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.Iterator;
import java.util.Optional;

public class AlphaBetaVisitor extends GameTreeVisitor {
    private final boolean maximise;
    private final StaticPositionEvaluationStrategy evalStrategy;

    private int alpha;
    private int beta;

    private int intEvaluation;

    public AlphaBetaVisitor(
            StaticPositionEvaluationStrategy evalStrategy
    ) {
        this(
                true,
                MinimaxVisitor.NEGATIVE_INFINITY,
                MinimaxVisitor.POSITIVE_INFINITY,
                evalStrategy
        );
    }

    private AlphaBetaVisitor(
            boolean maximise,
            int alpha,
            int beta,
            StaticPositionEvaluationStrategy evalStrategy
    ) {
        this.maximise = maximise;
        this.alpha = alpha;
        this.beta = beta;
        this.evalStrategy = evalStrategy;
    }

    @Override
    public Optional<Move> visit(GameTreeInnerNode innerNode) {
        Optional<Move> bestMove = Optional.empty();
        if (maximise) {
            intEvaluation = MinimaxVisitor.NEGATIVE_INFINITY;
            Iterator<GameTree> childIterator = innerNode.getChildren().iterator();
            boolean prune = false;
            while (childIterator.hasNext() && (!prune)) {
                GameTree child = childIterator.next();
                AlphaBetaVisitor visitor = new AlphaBetaVisitor(
                        false, alpha, beta, evalStrategy
                );
                child.accept(visitor);
                alpha = Math.max(alpha, visitor.alpha);
                if (beta <= alpha)
                    prune = true;
                else {
                    if (visitor.intEvaluation > intEvaluation) {
                        bestMove = child.mrXMoveMade();
                        intEvaluation = visitor.intEvaluation;
                    }
                }
            }
        } else {
            intEvaluation = MinimaxVisitor.POSITIVE_INFINITY;
            Iterator<GameTree> childIterator = innerNode.getChildren().iterator();
            boolean prune = false;
            while (childIterator.hasNext() && (!prune)) {
                GameTree child = childIterator.next();
                AlphaBetaVisitor visitor = new AlphaBetaVisitor(
                        true, alpha, beta, evalStrategy
                );
                child.accept(visitor);
                beta = Math.min(beta, visitor.beta);
                if (alpha <= beta)
                    prune = true;
                else {
                    if (visitor.intEvaluation < intEvaluation) {
                        bestMove = child.mrXMoveMade();
                        intEvaluation = visitor.intEvaluation;
                    }
                }
            }
        }
        return bestMove;
    }

    @Override
    public Optional<Move> visit(GameTreeLeafNode leafNode) {
        AiBoardV2 board = leafNode.getBoard();
        AiGameState aiGameState = new AiGameStateAdapter(board);
        intEvaluation = evalStrategy.evaluate(aiGameState);
        return leafNode.mrXMoveMade();
    }
}
