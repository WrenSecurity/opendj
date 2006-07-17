/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE
 * or https://OpenDS.dev.java.net/OpenDS.LICENSE.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE.  If applicable,
 * add the following below this CDDL HEADER, with the fields enclosed
 * by brackets "[]" replaced with your own identifying * information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Portions Copyright 2006 Sun Microsystems, Inc.
 */
package org.opends.server.integration.quickstart;

import static org.testng.Assert.*;
import org.testng.annotations.Configuration;
import org.testng.annotations.Parameters;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.ExpectedExceptions;
import org.opends.server.tools.*;

/**
 * This class contains the TestNG tests for the QuickstartAdd startup.
 */
@Test
public class QuickstartAddTests extends QuickstartTests
{
/**
 *  Setup for quickstartadd tests
*/
  @Parameters({ "integration_test_home", "logDir" })
  @Test
  public void testQuickstartAdd1(String integration_test_home, String logDir) throws Exception
  {
    System.out.println("*********************************************");
    System.out.println("QuickstartAdd test 1");

    String exec_cmd = integration_test_home + "/quickstart/checklogdir.sh " + logDir;
    Runtime rtime = Runtime.getRuntime();
    Process child = rtime.exec(exec_cmd);
    child.waitFor();

    compareExitCode(0, 0);
  }

  @Parameters({ "hostname", "port", "bindDN", "bindPW", "integration_test_home", "logDir" })
  @Test(alwaysRun=true, dependsOnMethods = { "org.opends.server.integration.quickstart.QuickstartAddTests.testQuickstartAdd1" })
  public void testQuickstartAdd2(String hostname, String port, String bindDN, String bindPW, String integration_test_home, String logDir) throws Exception
  {
    System.out.println("*********************************************");
    System.out.println("QuickstartAdd test 2");

    String datafile = integration_test_home + "/quickstart/data/quickstart.ldif";
    String quickstartadd_args[] = {"-a", "-h", hostname, "-p", port, "-D", bindDN, "-w", bindPW, "-f", datafile};

    ds_output.redirectOutput(logDir, "QuickstartAdd2.txt");
    int retCode = LDAPModify.mainModify(quickstartadd_args);
    ds_output.resetOutput();
    int expCode = 0;

    compareExitCode(retCode, expCode);
  }

}
