package edu.clemson.resolve.jetbrains;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * The {@link LanguageFileType} for RESOLVE files.
 *
 * @author dtwelch
 */
public class ResolveFileType extends LanguageFileType {

  public static ResolveFileType INSTANCE = new ResolveFileType();

  public ResolveFileType() {
    this(ResolveLanguage.INSTANCE);
  }

  public ResolveFileType(Language language) {
    super(language);
  }

  @NotNull
  @Override
  public String getName() {
    return "Resolve";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Resolve files";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "resolve";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return ResolveIcons.RESOLVE_FILE;
  }
}
