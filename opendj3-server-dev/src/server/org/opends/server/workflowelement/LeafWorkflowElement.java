/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at legal-notices/CDDLv1_0.txt
 * or http://forgerock.org/license/CDDLv1.0.html.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at legal-notices/CDDLv1_0.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Copyright 2008-2009 Sun Microsystems, Inc.
 */
package org.opends.server.workflowelement;

import java.util.ArrayList;
import org.opends.server.admin.std.server.WorkflowElementCfg;
import java.util.List;


/**
 * This class defines the super class for all the workflow elements
 * used to wrap physical repositories. A physical repository contains
 * data (for example, a local backend, a remote LDAP servers or an
 * LDIF flat file). Such workflow element is a leaf in the sense that
 * the workflow element can be used by another workflow element but
 * cannot use an other workflow element.
 *
 * @param  <T>  The type of configuration handled by this workflow elelemnt.
 */
public abstract class LeafWorkflowElement <T extends WorkflowElementCfg>
  extends WorkflowElement<WorkflowElementCfg>
{
  /**
   * {@inheritDoc}
   */
  public List<WorkflowElement<?>> getChildWorkflowElements() {
    // A leaf workflow element has no child
    return new ArrayList<WorkflowElement<?>>();
  }

}