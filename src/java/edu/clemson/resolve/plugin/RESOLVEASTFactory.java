package edu.clemson.resolve.plugin;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTFactory;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.psi.impl.*;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RESOLVEASTFactory extends ASTFactory {

    private static final Map<IElementType, PsiElementFactory> ruleElementTypeToPsiFactory = new HashMap<IElementType, PsiElementFactory>();
    static {
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_precisModule), PrecisModule.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_conceptModule), ConceptModule.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_conceptImplModule), ConceptImplModule.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_facilityModule), FacilityModule.Factory.INSTANCE);
    }

    /**
     * Create a FileElement for root or a parse tree CompositeElement (not
     * PSI) for the token. This impl is more or less the default.
     */
    @Override public CompositeElement createComposite(IElementType type) {
        if (type instanceof IFileElementType) {
            return new FileElement(type, null);
        }
        return new CompositeElement(type);
    }

    @Override public LeafElement createLeaf(
                        @NotNull IElementType type, CharSequence text) {
        LeafElement t;
       /*if ( type == ANTLRv4TokenTypes.TOKEN_ELEMENT_TYPES.get(ANTLRv4Lexer.RULE_REF) ) {
            t = new ParserRuleRefNode(type, text);
        }
        else if ( type == ANTLRv4TokenTypes.TOKEN_ELEMENT_TYPES.get(ANTLRv4Lexer.TOKEN_REF) ) {
            t = new LexerRuleRefNode(type, text);
        }
        else {*/
            t = new LeafPsiElement(type, text);
       // }
//		System.out.println("createLeaf "+t+" from "+type+" "+text);
        return t;
    }

    public static PsiElement createInternalParseTreeNode(ASTNode node) {
        PsiElement t;
        IElementType tokenType = node.getElementType();
        PsiElementFactory factory = ruleElementTypeToPsiFactory.get(tokenType);

        if (factory != null) {
            t = factory.createElement(node);
        }
        else {
            t = new ASTWrapperPsiElement(node);
        }
        return t;
    }
}
