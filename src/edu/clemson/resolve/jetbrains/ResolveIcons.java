package edu.clemson.resolve.jetbrains;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.PlatformIcons;

import javax.swing.*;

public class ResolveIcons {
  private static Icon load(String path) {
    return IconLoader.getIcon(path, ResolveIcons.class);
  }

  public static final Icon RESOLVE_FILE = IconLoader.getIcon("/icons/com/jetbrains/resolve/resolveFile.png");
  public static final Icon RESOLVE = IconLoader.getIcon("/icons/com/jetbrains/resolve/tool_icon.png");

  public static final Icon SYMBOLS = load("/icons/com/jetbrains/resolve/symbols.png");
  public static final Icon VALIDATE = load("/icons/com/jetbrains/resolve/validate.png");
}
