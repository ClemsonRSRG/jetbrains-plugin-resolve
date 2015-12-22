package edu.clemson.resolve.jetbrains.editor;

import com.intellij.ide.structureView.*;
import com.intellij.ide.util.ActionShortcutProvider;
import com.intellij.ide.util.FileStructureNodeProvider;
import com.intellij.ide.util.treeView.smartTree.*;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Iconable;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.psi.ResNamedElement;
import edu.clemson.resolve.jetbrains.psi.ResType;
import edu.clemson.resolve.jetbrains.psi.ResTypeLikeNodeDecl;
import edu.clemson.resolve.jetbrains.psi.impl.ResPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** Allows users to click the button on the side panel of the editor and view
 *  a hierarchical view of the module they are currently looking at.
 */
public class RESOLVEStructureViewFactory implements PsiStructureViewFactory {

    @Nullable @Override public StructureViewBuilder getStructureViewBuilder(
            @NotNull final PsiFile psiFile) {
        return new TreeBasedStructureViewBuilder() {
            @NotNull @Override public StructureViewModel createStructureViewModel(
                    @Nullable Editor editor) {
                return new Model(psiFile);
            }

            @Override public boolean isRootNodeShown() {
                return false;
            }
        };
    }

    public static class Model extends StructureViewModelBase
            implements
                StructureViewModel.ElementInfoProvider {

        private static final List<NodeProvider> PROVIDERS =
                ContainerUtil.<NodeProvider>newSmartList(
                        new TreeElementFileStructureNodeProvider());

        Model(@NotNull PsiFile file) {
            super(file, new Element(file));
        }

        @NotNull public Sorter[] getSorters() {
            return new Sorter[] {Sorter.ALPHA_SORTER};
        }

        @NotNull @Override public Filter[] getFilters() {
            return new Filter[]{/*new ResPrivateMembersFilter()*/};
        }

        @Override public boolean isAlwaysShowsPlus(
                StructureViewTreeElement structureViewTreeElement) {
            return false;
        }

        @Override public boolean isAlwaysLeaf(
                StructureViewTreeElement structureViewTreeElement) {
            return false;
        }

        @NotNull @Override public Collection<NodeProvider> getNodeProviders() {
            return PROVIDERS;
        }

        private static class TreeElementFileStructureNodeProvider
                implements
                    FileStructureNodeProvider<TreeElement>, ActionShortcutProvider {

            public static final String ID = "Show package structure";

            @NotNull @Override public ActionPresentation getPresentation() {
                return new ActionPresentationData(ID, null, RESOLVEIcons.MODULE);
            }

            @NotNull @Override public String getName() {
                return ID;
            }

            @NotNull @Override public Collection<TreeElement> provideNodes(
                    @NotNull TreeElement node) {
                PsiElement psi = node instanceof Element ? ((Element)node).element : null;
                if (psi instanceof ResFile) {
                    ResFile orig = (ResFile)psi;
                    List<TreeElement> result = ContainerUtil.newSmartList();
                    ContainerUtil.addAll(result, new Element(orig).getChildren());
                    //for (ResFile f : ResPsiImplUtil.getAllPackageFiles(orig)) {
                    //    if (f != orig) {
                           // ContainerUtil.addAll(result, new Element(f).getChildren());
                     //   }
                    //}
                    return result;
                }
                return Collections.emptyList();
            }

            @NotNull @Override public String getCheckBoxText() {
                return ID;
            }

            @NotNull @Override public Shortcut[] getShortcut() {
                throw new IncorrectOperationException("see getActionIdForShortcut()");
            }

            @NotNull @Override public String getActionIdForShortcut() {
                return "FileStructurePopup";
            }
        }
    }

    public static class Element
            implements
                StructureViewTreeElement, ItemPresentation, NavigationItem {

        @NotNull private final PsiElement element;

        public Element(@NotNull PsiElement e) {
            element = e;
        }

        @NotNull @Override public PsiElement getValue() {
            return element;
        }

        @Override public void navigate(boolean requestFocus) {
            ((Navigatable) element).navigate(requestFocus);
        }

        @Override public boolean canNavigate() {
            return ((Navigatable) element).canNavigate();
        }

        @Override public boolean canNavigateToSource() {
            return ((Navigatable) element).canNavigateToSource();
        }

        @Nullable @Override public String getName() {
            if (element instanceof ResNamedElement) {
                return ((ResNamedElement) element).getName();
            }
            return element.getText();
        }

        @NotNull @Override public ItemPresentation getPresentation() {
            return this;
        }

        @NotNull @Override public TreeElement[] getChildren() {
            List<TreeElement> result = ContainerUtil.newArrayList();
            if (element instanceof ResFile) {
                for (ResTypeLikeNodeDecl o : ((ResFile) element).getTypes()) result.add(new Element(o));
            }
            return result.toArray(new TreeElement[result.size()]);
        }

        @Override public String getPresentableText() {
            return getPresentationTextInner().replaceAll("\\(\\n", "(")
                    .replaceAll("\\n\\)", ")");
        }

        @NotNull private String getPresentationTextInner() {
            String separator = ": ";
            if (element instanceof ResFile) {
                return ((ResFile) element).getName();
            }
            else if (element instanceof ResNamedElement) {
                ResType type = ((ResNamedElement) element).getResType(null);
                String typeStr  = type != null ? type.getText() : "";
                return ((ResNamedElement) element).getName() + typeStr;
            }
            throw new AssertionError(element.getClass().getName());
        }

        @Nullable @Override public String getLocationString() {
            return null;
        }

        @Override public Icon getIcon(boolean open) {
            if (!element.isValid()) return null;
            return element.getIcon(Iconable.ICON_FLAG_VISIBILITY);
        }
    }
}