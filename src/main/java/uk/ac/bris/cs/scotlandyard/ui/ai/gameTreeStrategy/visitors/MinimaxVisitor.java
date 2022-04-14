package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeLeafNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.Objects;
import java.util.Optional;

public class MinimaxVisitor extends GameTreeVisitor {
    public static final int POSITIVE_INFINITY =  10000000;
    public static final int NEGATIVE_INFINITY = -10000000;

    private final boolean maximise;
    private final StaticPositionEvaluationStrategy evalStrategy;

    private Optional<Move> bestMove;
    private int intEvaluation;

    public MinimaxVisitor(
            StaticPositionEvaluationStrategy evalStrategy
    ) {
        this(true, evalStrategy);
    }

    private MinimaxVisitor(
            boolean maximise,
            StaticPositionEvaluationStrategy evalStrategy
    ) {
        this.maximise = maximise;
        this.evalStrategy = Objects.requireNonNull(evalStrategy);
        bestMove = Optional.empty();
    }

    @Override
    public Optional<Move> visit(GameTreeInnerNode innerNode) {
        if (maximise) {
            intEvaluation = NEGATIVE_INFINITY;
            for (GameTree child : innerNode.getChildren()) {
                MinimaxVisitor visitor = new MinimaxVisitor(false, evalStrategy);
                child.accept(visitor);
                if (visitor.intEvaluation > intEvaluation) {
                    bestMove = visitor.bestMove;
                    intEvaluation = visitor.intEvaluation;
                }
            }
        } else {
            intEvaluation = POSITIVE_INFINITY;
            for (GameTree child : innerNode.getChildren()) {
                MinimaxVisitor visitor = new MinimaxVisitor(true, evalStrategy);
                child.accept(visitor);
                if (visitor.intEvaluation < intEvaluation) {
                    bestMove = visitor.bestMove;
                    intEvaluation = visitor.intEvaluation;
                }
            }
        }
        return bestMove;
    }

    @Override
    public Optional<Move> visit(GameTreeLeafNode leafNode) {
        AiGameState aiGameState = new AiGameStateAiBoardAdapter(leafNode.getBoard());
        intEvaluation = evalStrategy.evaluate(aiGameState);
        return leafNode.mrXMoveMade();
    }
}
