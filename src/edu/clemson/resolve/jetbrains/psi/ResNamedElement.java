package edu.clemson.resolve.jetbrains.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents all named, declaration-like constructs in the language.
 * <p>
 * NamedElement FAQ:<p>
 * Q: How do I know if my node should extend this?<br>
 * A: If the element you're creating has a name and is <em>declared</em>
 * (as opposed to referenced) either in a top level module or somewhere within
 * a local operation {@link ResBlock}, then chances are you want to
 * extend this.<p>
 * <p>
 * Q: OK, but then why doesn't {@link ResMathDefinitionDecl} extend this?<br>
 * A: That's a special case, made necessary by the ridiculous number of ways
 * our language has for introducing mathematical operators to scope; for a more
 * detailed explanation, refer to the doc in {@link ResMathDefinitionDecl}.<p>
 * <p>
 * Q: I see that this class extends {@link ResTypeOwner}, but the node I want
 * to extend {@code this} doesn't have a sensible {@link ResType}! Is this OK?<br>
 * A: Sure, right now this is here mainly for convenience. Notice that the
 * method is annotated {@link Nullable}, indicating that it's fine if your
 * node has no real type info. In the future, this class might be adjusted so
 * that it doesn't extend {@link ResTypeOwner} -- pushing that off to more
 * specific extending classes (e.g.: {@link ResVarDef}, etc)<p>
 */
public interface ResNamedElement
        extends
        ResTypeOwner, ResMathMetaTypeExpOwner, ResCompositeElement, PsiNameIdentifierOwner, NavigationItem {

    public boolean isPublic();

    @Nullable
    public PsiElement getIdentifier();

    @NotNull
    public PsiFile getContainingFile();

    @Nullable
    ResType findSiblingType();

    @Nullable
    ResMathExp findSiblingMathMetaType();

}