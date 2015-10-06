package edu.clemson.resolve.plugin.editor;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.FileNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 *
 * @author esmek
 */
public class RESOLVEFoldingBuilder
        extends
            FoldingBuilderEx implements DumbAware {

    @NotNull @Override public FoldingDescriptor[] buildFoldRegions(
            @NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof FileNode)) return FoldingDescriptor.EMPTY;
        FileNode file = (FileNode)root;
        if (!file.isContentsLoaded()) return FoldingDescriptor.EMPTY;
        final List<FoldingDescriptor> result = ContainerUtil.newArrayList();
/*
        for (GoFunctionLit function : PsiTreeUtil.findChildrenOfType(file, GoFunctionLit.class)) {
            foldBlock(result, function.getBlock());
        }
        for (GoTypeSpec type : file.getTypes()) {
            foldTypes(type.getType(), result);
        }
*/
        if (!quick) {
            final Set<PsiElement> processedComments = ContainerUtil.newHashSet();
            PsiTreeUtil.processElements(file, new PsiElementProcessor() {
                @Override
                public boolean execute(@NotNull PsiElement element) {
                    IElementType type = element.getNode().getElementType();

                    //Folding for Operation procedures in a somewhat hacky way
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.PROCEDURE)) {
                        //Procedure...;
                        addTypeBlock(element, element.getNextSibling(), element.getParent().getLastChild(), result);
                    }
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.OPERATION)) {
                        //Operation NAME ... ;
                        addTypeBlock(element, element.getNextSibling().getNextSibling().getNextSibling(), element.getParent().getLastChild(), result);
                    }

                    //Folding for TypeModelDecl
                    //Basically, if there is the keyword Type with the keyword Family after it, it's a TypeModelDecl, and you can fold it.
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.TYPE) && element.getNextSibling().getNextSibling().getNode().getElementType() == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.FAMILY)) {
                        //Type Family NAME...
                        addTypeBlock(element, element.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling(), element.getParent().getLastChild(), result);
                    }

                    //Folding for TypeModelRepr
                    //Basically, if there is the keyword Type without the keyword Family after it, it's a TypeModelRepr, and you can fold it.
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.TYPE) && element.getNextSibling().getNextSibling().getNode().getElementType() != RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.FAMILY)) {
                        //Type NAME...
                        addTypeBlock(element, element.getNextSibling().getNextSibling().getNextSibling(), element.getParent().getLastChild(), result);
                    }

                    //Folding for FacilityDecl
                    // Identify if there is an IS after the id to separate facility Decls from Facility Modules
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.FACILITY) &&
                            element.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNode().getElementType() ==
                                    RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.IS)) {
                        //Facility NAME...
                        addTypeBlock(element, element.getNextSibling().getNextSibling().getNextSibling(), element.getParent().getLastChild(), result);
                    }

                    //Folding for loops
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.WHILE)) {
                        //While...;
                        addTypeBlock(element, element.getNextSibling(), element.getParent().getLastChild().getPrevSibling(), result);
                    }
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.FOR)) {
                        //For...;
                        addTypeBlock(element, element.getNextSibling(), element.getParent().getLastChild().getPrevSibling(), result);
                    }
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.FORALL)) {
                        //ForAll...;
                        addTypeBlock(element, element.getNextSibling(), element.getParent().getLastChild(), result);
                    }
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.DO)) {
                        //While (CONDITION) do...;
                        addTypeBlock(element, element.getNextSibling(), element.getParent().getLastChild(), result);
                    }

                    //Folding for comments
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.BLOCK_COMMENT) && element.getTextRange().getLength() > 2) {
                        result.add(new FoldingDescriptor(element, element.getTextRange()));
                    }
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.DOC_COMMENT) && element.getTextRange().getLength() > 2) {
                        result.add(new FoldingDescriptor(element, element.getTextRange()));
                    }
                    if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.LINE_COMMENT)) {
                        addCommentFolds(element, processedComments, result);
                    }
                    //foldTypes(element, result); // folding for inner types
                    return true;
                }
            });
        }
        return result.toArray(new FoldingDescriptor[result.size()]);
    }


    /*private static void foldTypes(@Nullable PsiElement e, List<FoldingDescriptor> result) {
        if (e instanceof GoStructType) {
            if (((GoStructType)e).getFieldDeclarationList().isEmpty()) return;
            addTypeBlock(e, ((GoStructType)e).getLbrace(), ((GoStructType)e).getRbrace(), result);
        }
        if (e instanceof GoInterfaceType) {
            if (e.getChildren().length == 0) return;
            addTypeBlock(e, ((GoInterfaceType)e).getLbrace(), ((GoInterfaceType)e).getRbrace(), result);
        }
    }*/

    private static void addTypeBlock(@NotNull PsiElement element,
                                     @Nullable PsiElement l,
                                     @Nullable PsiElement r,
                                     @NotNull List<FoldingDescriptor> result) {
        if (l != null && r != null) {
            result.add(new FoldingDescriptor(element, TextRange.create(l.getTextRange().getStartOffset(), r.getTextRange().getEndOffset())));
        }
    }

    // com.intellij.codeInsight.folding.impl.JavaFoldingBuilderBase.addCodeBlockFolds()
    private static void addCommentFolds(@NotNull PsiElement comment,
                                        @NotNull Set<PsiElement> processedComments,
                                        @NotNull List<FoldingDescriptor> foldElements) {
        if (processedComments.contains(comment)) return;

        PsiElement end = null;
        for (PsiElement current = comment.getNextSibling(); current != null; current = current.getNextSibling()) {
            ASTNode node = current.getNode();
            if (node == null) break;
            IElementType elementType = node.getElementType();
            if (elementType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.LINE_COMMENT)) {
                end = current;
                processedComments.add(current);
                continue;
            }
            if (elementType == TokenType.WHITE_SPACE) continue;
            break;
        }

        if (end != null) {
            foldElements.add(new FoldingDescriptor(comment,
                    new TextRange(comment.getTextRange().getStartOffset(),
                            end.getTextRange().getEndOffset())));
        }
    }

    @Nullable @Override public String getPlaceholderText(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
        IElementType type = node.getElementType();
        //if (psi instanceof GoImportDeclaration) return "...";
        if (RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.LINE_COMMENT) == type) return "/.../";
        if (RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.BLOCK_COMMENT) == type) return "/*...*/";
        if (RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.DOC_COMMENT) == type) return "/*...*/";
        return null;
    }

    @Override public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
     //   IElementType type = node.getElementType();
     //   if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.BLOCK_COMMENT) ||
     //           type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.DOC_COMMENT) ||
     //           type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.LINE_COMMENT)) {
     //       return CodeFoldingSettings.getInstance().COLLAPSE_DOC_COMMENTS;
     //   }
//        if (type == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.PROCEDURE) && CodeFoldingSettings.getInstance().COLLAPSE_METHODS) {
//            ASTNode parent = node.getTreeParent();
//            return parent != null && parent.getPsi() instanceof ResolveOperationDeclaration;
//        }
   //     return CodeFoldingSettings.getInstance().COLLAPSE_IMPORTS;// && node.getElementType() == GoTypes.IMPORT_LIST;
    }
}