package edu.clemson.resolve.jetbrains;

import com.intellij.lang.Language;

/** @author dtwelch */
public class ResolveLanguage extends Language {

  public static final ResolveLanguage INSTANCE = new ResolveLanguage();

  public static ResolveLanguage getInstance() {
    return INSTANCE;
  }

  protected ResolveLanguage() {
    super("Resolve");
  }

  @Override
  public boolean isCaseSensitive() {
    return true;
  }
}
