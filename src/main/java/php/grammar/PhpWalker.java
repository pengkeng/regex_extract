package php.grammar;

import org.antlr.v4.runtime.tree.*;

public class PhpWalker extends ParseTreeWalker {

    @Override
    public void walk(ParseTreeListener baselLstener, ParseTree t) {
        PhpParserBaseListener listener = (PhpParserBaseListener) baselLstener;
        if (t instanceof PhpParser.FunctionCallContext) {
            PhpParser.FunctionCallContext callContext = (PhpParser.FunctionCallContext) t;
            listener.visitFunctionCallContext(callContext);
        } else if (t instanceof PhpParser.AssignmentExpressionContext) {
            PhpParser.AssignmentExpressionContext context = (PhpParser.AssignmentExpressionContext) t;
            listener.visitAssignmentExpressionContext(context);
        }
        if (t instanceof ErrorNode) {
            listener.visitErrorNode((ErrorNode) t);
        } else if (t instanceof TerminalNode) {
            listener.visitTerminal((TerminalNode) t);
        } else {
            RuleNode r = (RuleNode) t;
            this.enterRule(listener, r);
            int n = r.getChildCount();

            for (int i = 0; i < n; ++i) {
                this.walk(listener, r.getChild(i));
            }
            this.exitRule(listener, r);
        }
    }
}
