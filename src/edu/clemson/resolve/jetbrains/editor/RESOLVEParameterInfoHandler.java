package edu.clemson.resolve.jetbrains.editor;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.parameterInfo.*;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.jetbrains.psi.ResArgumentList;
import edu.clemson.resolve.jetbrains.psi.ResExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * This is resposible for providing information about an operation being called, as the user is typing the call exp.
 * It should provide (and highlight) both the name of the formal parameter we're supplying an actual for + its type
 * and parameter mode. Furthermore, this will also display the precondition and postcondition (advanced functionality
 * could even highlight the parameters within these mathematical clauses as typed)..
 *
 * @author dtwelch
 */
public class RESOLVEParameterInfoHandler implements ParameterInfoHandlerWithTabActionSupport<ResArgumentList, Object, ResExp> {
    @NotNull
    @Override
    public ResExp[] getActualParameters(@NotNull ResArgumentList o) {
        return new ResExp[0];
    }

    @NotNull
    @Override
    public IElementType getActualParameterDelimiterType() {
        return null;
    }

    @NotNull
    @Override
    public IElementType getActualParametersRBraceType() {
        return null;
    }

    @NotNull
    @Override
    public Set<Class> getArgumentListAllowedParentClasses() {
        return null;
    }

    @NotNull
    @Override
    public Set<? extends Class> getArgListStopSearchClasses() {
        return null;
    }

    @NotNull
    @Override
    public Class<ResArgumentList> getArgumentListClass() {
        return null;
    }

    @Override
    public boolean couldShowInLookup() {
        return false;
    }

    @Nullable
    @Override
    public Object[] getParametersForLookup(LookupElement item, ParameterInfoContext context) {
        return new Object[0];
    }

    @Nullable
    @Override
    public Object[] getParametersForDocumentation(Object p, ParameterInfoContext context) {
        return new Object[0];
    }

    @Nullable
    @Override
    public ResArgumentList findElementForParameterInfo(@NotNull CreateParameterInfoContext context) {
        return null;
    }

    @Override
    public void showParameterInfo(@NotNull ResArgumentList element, @NotNull CreateParameterInfoContext context) {

    }

    @Nullable
    @Override
    public ResArgumentList findElementForUpdatingParameterInfo(@NotNull UpdateParameterInfoContext context) {
        return null;
    }

    @Override
    public void updateParameterInfo(@NotNull ResArgumentList resArgumentList, @NotNull UpdateParameterInfoContext context) {

    }

    @Nullable
    @Override
    public String getParameterCloseChars() {
        return null;
    }

    @Override
    public boolean tracksParameterIndex() {
        return false;
    }

    @Override
    public void updateUI(Object p, @NotNull ParameterInfoUIContext context) {

    }
}
