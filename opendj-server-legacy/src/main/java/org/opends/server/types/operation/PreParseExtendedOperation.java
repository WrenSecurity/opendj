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
 * Portions Copyright 2014-2015 ForgeRock AS.
 */
package org.opends.server.types.operation;



import org.forgerock.opendj.ldap.ByteString;


/**
 * This class defines a set of methods that are available for use by
 * pre-parse plugins for extended operations.  Note that this
 * interface is intended only to define an API for use by plugins and
 * is not intended to be implemented by any custom classes.
 */
@org.opends.server.types.PublicAPI(
     stability=org.opends.server.types.StabilityLevel.UNCOMMITTED,
     mayInstantiate=false,
     mayExtend=false,
     mayInvoke=true)
public interface PreParseExtendedOperation
       extends PreParseOperation
{
  /**
   * Retrieves the OID for the request associated with this extended
   * operation.
   *
   * @return  The OID for the request associated with this extended
   *          operation.
   */
  String getRequestOID();



  /**
   * Specifies the OID for the request associated with this extended
   * operation.
   *
   * @param  requestOID  The OID for the request associated with this
   *                     extended operation.
   */
  void setRequestOID(String requestOID);



  /**
   * Retrieves the value for the request associated with this extended
   * operation.
   *
   * @return  The value for the request associated with this extended
   *          operation.
   */
  ByteString getRequestValue();



  /**
   * Specifies the value for the request associated with this extended
   * operation.
   *
   * @param  requestValue  The value for the request associated with
   *                       this extended operation.
   */
  void setRequestValue(ByteString requestValue);



  /**
   * Specifies the OID to include in the response to the client.
   *
   * @param  responseOID  The OID to include in the response to the
   *                      client.
   */
  void setResponseOID(String responseOID);



  /**
   * Specifies the value to include in the response to the client.
   *
   * @param  responseValue  The value to include in the response to
   *                        the client.
   */
  void setResponseValue(ByteString responseValue);
}

