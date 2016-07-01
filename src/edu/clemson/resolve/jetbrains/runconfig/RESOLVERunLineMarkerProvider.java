package edu.clemson.resolve.jetbrains.runconfig;

import com.intellij.execution.Executor;
import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.Function;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.ResOperationLikeNode;
import org.jetbrains.annotations.Nullable;

public class RESOLVERunLineMarkerProvider extends RunLineMarkerContributor {

    private static final Function<PsiElement, String> TOOLTIP_PROVIDER = new Function<PsiElement, String>() {
        @Override
        public String fun(PsiElement element) {
            return "Run Program";
        }
    };

    @Nullable
    @Override
    public Info getInfo(PsiElement e) {
        if (e != null && e.getNode().getElementType() == ResTypes.IDENTIFIER) {
            PsiElement parent = e.getParent();
            PsiFile file = e.getContainingFile();
            if (RESOLVERunUtil.isMainRESOLVEFile(file) && parent instanceof ResOperationLikeNode) {
                String name = ((ResOperationLikeNode)parent).getName();
                if (name!= null && name.equalsIgnoreCase("main")) {
                    return new Info(AllIcons.RunConfigurations.TestState.Run, TOOLTIP_PROVIDER,
                            ExecutorAction.getActions(0)[0]);
                }
            }
        }
        return null;
    }
}
