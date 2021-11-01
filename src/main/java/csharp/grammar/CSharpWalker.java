package csharp.grammar;

import org.antlr.v4.runtime.tree.*;

public class CSharpWalker extends ParseTreeWalker {

    @Override
    public void walk(ParseTreeListener baseListener, ParseTree t) {
        CSharpParserBaseListener listener = (CSharpParserBaseListener) baseListener;
        if (t instanceof ErrorNode) {
            listener.visitErrorNode((ErrorNode) t);
        } else if (t instanceof TerminalNode) {
            listener.visitTerminal((TerminalNode) t);
        } else {
            RuleNode r = (RuleNode) t;
            this.enterRule(listener, r);
            int n = r.getChildCount();

//            System.out.println(r.getRuleContext().getText() + " " + r.getRuleContext().getClass());
            for (int i = 0; i < n; ++i) {
                this.walk(listener, r.getChild(i));
            }

            this.exitRule(listener, r);
        }
    }
}
