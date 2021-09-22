package csharp.grammar;

import org.antlr.v4.runtime.tree.*;

public class CSharpWalker extends ParseTreeWalker {

    @Override
    public void walk(ParseTreeListener baseListener, ParseTree t) {
        CSharpParserBaseListener listener = (CSharpParserBaseListener) baseListener;
//        if(t instanceof CSharpParser.ObjectCreationExpressionContext){
//            CSharpParser.ObjectCreationExpressionContext context = (CSharpParser.ObjectCreationExpressionContext) t;
//            if(t.getText().startsWith("newRegex")||t.getText().startsWith("newRegexCompilationInfo")){
//                System.out.println(context.start.getLine() +" " + context.getText() + " " + context.getClass());
//            }
//        }
//        if(t instanceof CSharpParser.Primary_expressionContext){
//            CSharpParser.Primary_expressionContext context = (CSharpParser.Primary_expressionContext) t;
//            if(t.getText().startsWith("Regex.Match") || context.getText().startsWith("Regex.IsMatch") || context.getText().startsWith("Regex.Matches")){
//                System.out.println(context.start.getLine() +" " + context.getText() + " " + context.getClass());
//            }
//        }
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
