/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2006-2008 Sun Microsystems, Inc.
 * Portions Copyright 2012-2016 ForgeRock AS.
 */
package org.opends.quicksetup;

import static org.opends.messages.QuickSetupMessages.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.forgerock.i18n.LocalizableMessage;
import org.forgerock.i18n.LocalizableMessageBuilder;
import org.forgerock.i18n.slf4j.LocalizedLogger;
import org.opends.quicksetup.util.Utils;

/**
 * This class is used to know which is the status of the install.
 * It is required to do an installation after the user has unzipped the zip file.
 * The main goal of the class is to help identifying whether there is already
 * something installed or not.
 *
 * This class assumes that we are running in the case of an offline install.
 */
public class CurrentInstallStatus
{
  private static final LocalizedLogger logger = LocalizedLogger.getLoggerForThisClass();

  private boolean isInstalled;
  private boolean canOverwriteCurrentInstall;
  private LocalizableMessage installationMsg;

  /** The constructor of a CurrentInstallStatus object. */
  public CurrentInstallStatus()
  {

    Installation installation = Installation.getLocal();
    List<LocalizableMessage> msgs = new ArrayList<>();

    if (installation.getStatus().isServerRunning())
    {
      msgs.add(INFO_INSTALLSTATUS_SERVERRUNNING.get(getPort()));
    }

    if (dbFilesExist())
    {
      canOverwriteCurrentInstall = true;
      msgs.add(INFO_INSTALLSTATUS_DBFILEEXIST.get());
    }

    if (configExists())
    {
      canOverwriteCurrentInstall = false;
      isInstalled = true;
      msgs.add(INFO_INSTALLSTATUS_CONFIGFILEMODIFIED.get());
    }

    if (canOverwriteCurrentInstall)
    {
      installationMsg = !Utils.isCli()?
        INFO_INSTALLSTATUS_CANOVERWRITECURRENTINSTALL_MSG.get() :
        INFO_INSTALLSTATUS_CANOVERWRITECURRENTINSTALL_MSG_CLI.get();
    }
    else if (isInstalled)
    {
      LocalizableMessageBuilder buf = new LocalizableMessageBuilder();
      if (Utils.isCli())
      {
        buf = new LocalizableMessageBuilder();
        for (LocalizableMessage msg : msgs)
        {
          buf.append(Constants.LINE_SEPARATOR);
          buf.append("- ").append(msg);
        }
        String cmd = Installation.getSetupFileName();
        installationMsg = INFO_INSTALLSTATUS_INSTALLED_CLI.get(cmd, buf);
      }
      else
      {
        buf.append("<ul>");
        for (LocalizableMessage msg : msgs)
        {
          buf.append("\n<li>");
          buf.append(msg);
          buf.append("</li>");
        }
        buf.append("</ul>");
        installationMsg = INFO_INSTALLSTATUS_INSTALLED.get(buf);
      }
    }
    if (!isInstalled)
    {
      installationMsg = INFO_INSTALLSTATUS_NOT_INSTALLED.get();
    }
  }

  /**
   * Indicates whether there is something installed or not.
   *
   * @return <CODE>true</CODE> if there is something installed under the
   *         binaries that we are running, or <CODE>false</CODE> if not.
   */
  public boolean isInstalled()
  {
    return isInstalled;
  }

  /**
   * Indicates can overwrite current install.
   *
   * @return <CODE>true</CODE> if there is something installed under the
   *         binaries that we are running and we can overwrite it and
   *         <CODE>false</CODE> if not.
   */
  public boolean canOverwriteCurrentInstall()
  {
    return canOverwriteCurrentInstall;
  }

  /**
   * Provides a localized message to be displayed to the user in HTML format
   * informing of the installation status.
   *
   * @return an String in HTML format describing the status of the installation.
   */
  public LocalizableMessage getInstallationMsg()
  {
    return installationMsg;
  }

  private int getPort()
  {
    try {
      return Installation.getLocal().getCurrentConfiguration().getPort();
    } catch (IOException ioe) {
      logger.info(LocalizableMessage.raw("Failed to get port", ioe));
      return -1;
    }
  }

  /**
   * Indicates whether there are database files under this installation.
   *
   * @return <CODE>true</CODE> if there are database files, or
   *         <CODE>false</CODE> if not.
   */
  private boolean dbFilesExist()
  {
    File dbDir = Installation.getLocal().getDatabasesDirectory();
    File[] children = dbDir.listFiles();
    return children != null && children.length > 0;
  }

  /**
   * Indicates whether there are config files under this installation.
   *
   * @return <CODE>true</CODE> if there are configuration files, or
   *         <CODE>false</CODE> if not.
   */
  private boolean configExists()
  {
    File configDir = Installation.getLocal().getConfigurationDirectory();
    File[] children = configDir.listFiles();
    return children != null && children.length > 0;
  }
}
