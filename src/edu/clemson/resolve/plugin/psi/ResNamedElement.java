package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents all named, declaration-like constructs in the language.
 *
 * NameElement FAQ:<p>
 * Q: How do I implement code completion?<br>
 * A: Define a completion.contributor extension of type {@link CompletionContributor}.
 * Or, if the place you want to complete in contains a {@link PsiReference}, just return the variants
 * you want to suggest from its {@link PsiReference#getVariants()} method as {@link String}s,
 * {@link com.intellij.psi.PsiElement}s, or better {@link LookupElement}s.<p>
 */
public interface ResNamedElement
        extends
            ResTypeOwner, ResCompositeElement, PsiNameIdentifierOwner, NavigationItem {

    public boolean isPublic();

    @Nullable public PsiElement getIdentifier();

    @NotNull public PsiFile getContainingFile();

    @Nullable ResType findSiblingType();

}