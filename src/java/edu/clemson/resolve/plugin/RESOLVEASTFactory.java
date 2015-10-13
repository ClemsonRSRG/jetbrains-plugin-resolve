package edu.clemson.resolve.plugin;

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
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_precisModule), ResPrecisModule.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_facilityModule), ResFacilityModule.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_conceptModule), ResConceptModule.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_conceptBlock), ResBlockImpl.Factory.INSTANCE);

        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_usesItem), ResUsesItemImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_operationProcedureDecl), ResOperationProcedureDeclarationImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_type), ResTypeReferenceExpressionImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_record), ResRecordTypeImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_recordVariableDeclGroup), ResVarDeclGroupImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_variableDeclGroup), ResVarDeclGroupImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_variableDef), ResVarDefinitionImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_progVarExp), ResAbstractVarExpOptions.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_facilityDecl), ResFacilityDeclImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_qualifier), ResTypeReferenceExpressionImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_spec), ResUsesItemImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_spec), ResUsesItemImpl.Factory.INSTANCE);

        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_typeModelDecl), ResTypeModelDeclImpl.Factory.INSTANCE);

        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_progNamedExp), ResReferenceExpressionImpl.Factory.INSTANCE);

        //ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_qualifier), ResTypeReferenceExpressionImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_typeRepresentationDecl), ResTypeReprDeclImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_facilityBlock), ResBlockImpl.Factory.INSTANCE);
        ruleElementTypeToPsiFactory.put(RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_assignStmt), ResAssignStmtImpl.Factory.INSTANCE);

    }

    /** Create a FileElement for root or a parse tree CompositeElement (not
     *  PSI) for the token. This impl is more or less the default.
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
            t = new ResCompositeElementImpl(node);
        }
        return t;
    }
}
