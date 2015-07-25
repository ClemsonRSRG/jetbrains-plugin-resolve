package edu.clemson.resolve.plugin;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.psi.impl.AbstractNamedElementRefNode;
import edu.clemson.resolve.plugin.psi.impl.NamedPsiElementRef;
import edu.clemson.resolve.plugin.psi.impl.TypeRefNode;
import org.jetbrains.annotations.NotNull;

public class RESOLVEReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        
    }

  /*  @Override public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        PsiReferenceProvider provider = new PsiReferenceProvider() {
            @NotNull @Override public PsiReference[] getReferencesByElement(
                    @NotNull PsiElement element, @NotNull ProcessingContext context) {
                TypeRefNode ruleRef = (TypeRefNode)element;
                NamedPsiElementRef ref = new NamedPsiElementRef((AbstractNamedElementRefNode)element, ruleRef.getText());
                return new PsiReference[]{ref};
            }
        };
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(TypeRefNode.class),
                provider);

    }*/
}
