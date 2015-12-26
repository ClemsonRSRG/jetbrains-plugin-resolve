package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RunRESOLVEOnFile extends Task.Modal {

    public static final Logger LOG = Logger.getInstance("RunRESOLVEOnFile");
    public static final String OUTPUT_DIR_NAME = "gen" ;
    public static final String MISSING = "";
    public static final String groupDisplayId = "RESOLVE Code Generation";

    public VirtualFile targetFile;
    public Project project;
    public boolean forceGeneration;

    public RunRESOLVEOnFile(VirtualFile targetFile,
                            @Nullable final Project project,
                            @NotNull final String title,
                            final boolean canBeCancelled,
                            boolean forceGeneration) {
        super(project, title, canBeCancelled); //, inBackground ? new BackgroundFromStartOption() : null);
        this.targetFile = targetFile;
        this.project = project;
        this.forceGeneration = forceGeneration;
    }

    @Override public void run(@NotNull ProgressIndicator indicator) {
        indicator.setIndeterminate(true);
        if (forceGeneration) {
            resolve(targetFile);
        }
    }

    //TODO: can have checkboxes (genvcs, prove, gencode, etc)
    /** Run RESOLVE on file according to preferences in intellij for this file.
     *  Writes set of generated files or empty set if error.
     */
    public void resolve(VirtualFile vfile) {
        if ( vfile==null ) return;
        LOG.info("resolve(\""+vfile.getPath()+"\")");


    }

    public static Map<String, String> getRESOLVEArgs(Project project,
                                                     VirtualFile vfile) {
        Map<String,String> args = new HashMap<String, String>();
        String qualFileName = vfile.getPath();

        // create gen dir at root of project by default, but add in package if any
        VirtualFile contentRoot = ConfigANTLRPerGrammar.getContentRoot(project, vfile);
        String outputDirName = ConfigANTLRPerGrammar.getOutputDirName(project, qualFileName, contentRoot, package_);
        args.put("-o", outputDirName);

        String libDir = ConfigANTLRPerGrammar.getProp(project, qualFileName, ConfigANTLRPerGrammar.PROP_LIB_DIR, sourcePath);
        args.put("-lib", libDir);

        String encoding = ConfigANTLRPerGrammar.getProp(project, qualFileName, ConfigANTLRPerGrammar.PROP_ENCODING, MISSING);
        if ( encoding!=MISSING ) {
            args.put("-encoding", encoding);
        }

        if ( ConfigANTLRPerGrammar.getBooleanProp(project, qualFileName, ConfigANTLRPerGrammar.PROP_GEN_LISTENER, true) ) {
            args.put("-listener", "");
        }
        else {
            args.put("-no-listener", "");
        }
        if ( ConfigANTLRPerGrammar.getBooleanProp(project, qualFileName, ConfigANTLRPerGrammar.PROP_GEN_VISITOR, true) ) {
            args.put("-visitor", "");
        }
        else {
            args.put("-no-visitor", "");
        }

        return args;
    }

}
