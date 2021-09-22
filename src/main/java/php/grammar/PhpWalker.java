package php.grammar;

import csharp.grammar.CSharpParser;
import org.antlr.v4.runtime.tree.*;

import utils.bean.regexps;

import java.util.LinkedList;

public class PhpWalker extends ParseTreeWalker {

    public LinkedList<regexps> regexs;

    public PhpWalker() {
        regexs = new LinkedList<>();
    }


    @Override
    public void walk(ParseTreeListener baselListener, ParseTree t) {
        PhpParserBaseListener listener = (PhpParserBaseListener) baselListener;
        if (t instanceof PhpParser.FunctionCallContext) {
            PhpParser.FunctionCallContext callContext = (PhpParser.FunctionCallContext) t;
            try {
                listener.visitFunctionCallContext(callContext, regexs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (t instanceof PhpParser.AssignmentExpressionContext) {
            try {
                PhpParser.AssignmentExpressionContext context = (PhpParser.AssignmentExpressionContext) t;
                listener.visitAssignmentExpressionContext(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
