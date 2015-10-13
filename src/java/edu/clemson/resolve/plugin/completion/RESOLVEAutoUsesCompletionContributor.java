package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.PsiImplUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.ProcessingContext;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.AbstractResTypeImpl;
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeDecl;
import edu.clemson.resolve.plugin.psi.impl.ResPsiImplUtil;
import edu.clemson.resolve.plugin.psi.impl.ResTypeReference;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static edu.clemson.resolve.plugin.completion.RESOLVECompletionUtil.createPrefixMatcher;
import static edu.clemson.resolve.plugin.psi.impl.ResPsiImplUtil.isPrevColonColon;

public class RESOLVEAutoUsesCompletionContributor
        extends
            CompletionContributor {

    public RESOLVEAutoUsesCompletionContributor() {
        extend(CompletionType.BASIC, inRESOLVEFile(),
                new CompletionProvider<CompletionParameters>() {
            @Override protected void addCompletions(
                    @NotNull CompletionParameters parameters,
                    ProcessingContext context,
                    @NotNull CompletionResultSet result) {
                PsiElement position = parameters.getPosition();
                PsiElement parent = position.getParent();
                if(isPrevColonColon(parent)) return;
                PsiFile file = parameters.getOriginalFile();
                if (!(file instanceof ResFile)) return;
                if (!(parent instanceof ResReferenceExpressionBase)) return;
                result = adjustMatcher(parameters, result, parent);
                PrefixMatcher matcher = result.getPrefixMatcher();
                if (parameters.getInvocationCount() < 2 &&
                        matcher.getPrefix().isEmpty()) {
                    result.restartCompletionOnPrefixChange(StandardPatterns
                            .string().longerThan(0));
                    return;
                }

                ResReferenceExpressionBase qualifier =
                        ((ResReferenceExpressionBase)parent).getQualifier();
                if (qualifier != null && qualifier.getReference() != null &&
                        qualifier.getReference().resolve() != null) return;

                final ArrayList<ElementProcessor> processors =
                        ContainerUtil.newArrayList();
                if (parent instanceof ResReferenceExpression) {
                    //processors.add(new OperationsProcessor());
                    //processors.add(new VariablesAndConstantsProcessor());
                }
                else if (parent instanceof ResTypeReferenceExpression) {
                    //processors.add(new TypesProcessor(parent));
                }
                if (processors.isEmpty()) return;
                NamedElementProcessor processor =
                        new NamedElementProcessor(processors,
                                ((ResFile)file).getUsesItemMapping(), result);
                Project project = position.getProject();
                GlobalSearchScope scope = RESOLVEUtil.moduleScope(file);
                VirtualFile containingDirectory = file.getVirtualFile().getParent();

                Set<String> sortedKeys = sortMatching(matcher, StubIndex.getInstance().getAllKeys(ALL_PUBLIC_NAMES, project), file);
                for (String name : sortedKeys) {
                    processor.setName(name);
                    StubIndex.getInstance().processElements(ALL_PUBLIC_NAMES, name, project, scope, GoNamedElement.class, processor);
                }
            }

            private CompletionResultSet adjustMatcher(
                    @NotNull CompletionParameters parameters,
                    @NotNull CompletionResultSet result,
                    @NotNull PsiElement parent) {
                int startOffset = parent.getTextRange().getStartOffset();
                String newPrefix = parameters.getEditor().getDocument()
                        .getText(TextRange.create(startOffset,
                                parameters.getOffset()));
                return result.withPrefixMatcher(createPrefixMatcher(newPrefix));
            }
        });
    }

    private static PsiElementPattern.Capture<PsiElement> inRESOLVEFile() {
        return psiElement().inFile(psiElement(ResFile.class));
    }

    private static class TypesProcessor implements ElementProcessor {
        private final PsiElement parent;

        public TypesProcessor(@Nullable PsiElement parent) {
            this.parent = parent;
        }

        @Override
        public boolean process(@NotNull String name,
                               @NotNull ResNamedElement element,
                               @NotNull ExistingUsesData usesData,
                               @NotNull CompletionResultSet result) {
            ResAbstractTypeDecl spec = ((ResAbstractTypeDecl)element);

            String lookupString = "DanDurp";
            result.addElement(RESOLVECompletionUtil.createTypeLookupElement(
                    spec, lookupString, RESOLVEAutoUsesInsertHandler.SIMPLE_INSERT_HANDLER,
                    usesData.usesName, RESOLVECompletionUtil.TYPE_PRIORITY));
            return true;
        }

        @Override public boolean isMine(@NotNull String name,
                                        @NotNull ResNamedElement element) {
            if (parent != null && element instanceof AbstractResTypeImpl) {
                PsiReference reference = parent.getReference();
                return !(reference instanceof ResTypeReference); //||
                        //((ResTypeReference)reference).allowed((ResAbstractTypeDecl)element);
            }
            return false;
        }
    }

    private static class NamedElementProcessor implements Processor<ResNamedElement> {
        @NotNull private final Collection<ElementProcessor> processors;
        @NotNull private final CompletionResultSet result;
        @NotNull private String name = "";
        @NotNull private final Map<String, ResUsesItem> usesItems;

        public NamedElementProcessor(
                @NotNull Collection<ElementProcessor> processors,
                @NotNull Map<String, ResUsesItem> uses,
                @NotNull CompletionResultSet result) {
            this.processors = processors;
            this.usesItems = uses;
            this.result = result;
        }

        public void setName(@NotNull String name) {
            this.name = name;
        }

        @Override public boolean process(ResNamedElement element) {
            ProgressManager.checkCanceled();
            Boolean allowed = null;
            ExistingUsesData usesData = null;
            for (ElementProcessor processor : processors) {
                if (processor.isMine(name, element)) {
                    usesData = cachedImportData(element, usesData);
                    allowed = cachedAllowed(element, allowed);
                    if (allowed == Boolean.FALSE) break;
                    if (!processor.process(name, element, usesData, result)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private static Boolean cachedAllowed(@NotNull ResNamedElement element,
                                             @Nullable Boolean existingValue) {
            if (existingValue != null) return existingValue;
            return true;
        }

        private ExistingUsesData cachedImportData(
                @NotNull ResNamedElement element,
                @Nullable ExistingUsesData existingValue) {
            if (existingValue != null) return existingValue;

            ResFile declarationFile = (ResFile) element.getContainingFile();
            String usesPath = declarationFile.getVirtualFile().getPath();
            ResUsesItem existingUses = usesItems.get(usesPath);

            boolean exists = existingUses != null;
            return new ExistingUsesData(exists, existingUses.getText(), usesPath);
        }
    }

    private interface ElementProcessor {
        boolean process(@NotNull String name,
                        @NotNull ResNamedElement element,
                        @NotNull ExistingUsesData importData,
                        @NotNull CompletionResultSet result);
        boolean isMine(@NotNull String name, @NotNull ResNamedElement element);
    }

    private static class ExistingUsesData {
        public final boolean exists;
        public final String usesPath, usesName;

        private ExistingUsesData(boolean exists,
                                 String usesName, String usesPath) {
            this.exists = exists;
            this.usesPath = usesPath;
            this.usesName = usesName;
        }
    }
}
