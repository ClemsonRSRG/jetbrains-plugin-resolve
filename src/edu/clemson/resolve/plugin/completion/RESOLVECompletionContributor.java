package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import edu.clemson.resolve.plugin.psi.ResUsesSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, referenceExpression(),
                new RESOLVEReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExpression() {
        return PlatformPatterns.psiElement()
                .withParent(ResReferenceExpressionBase.class);
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();
        PsiFile file = parameters.getOriginalFile();
        ASTNode node = position.getNode();
        /*if (file instanceof GoFile && position.getParent() instanceof GoPackageClause && node.getElementType() == GoTypes.IDENTIFIER) {
            boolean isTestFile = GoTestFinder.isTestFile(file);
            PsiDirectory directory = file.getParent();
            Collection<String> packagesInDirectory = GoUtil.getAllPackagesInDirectory(directory, true);
            for (String packageName : packagesInDirectory) {
                result.addElement(LookupElementBuilder.create(packageName));
                if (isTestFile) {
                    result.addElement(LookupElementBuilder.create(packageName + GoConstants.TEST_SUFFIX));
                }
            }

            if (packagesInDirectory.isEmpty() && directory != null) {
                String packageFromDirectory = GoPsiImplUtil.getLocalPackageName(directory.getName());
                if (!packageFromDirectory.isEmpty()) {
                    result.addElement(LookupElementBuilder.create(packageFromDirectory));
                }
            }
            result.addElement(LookupElementBuilder.create(GoConstants.MAIN));
        }*/
        super.fillCompletionVariants(parameters, result);
    }
}
