/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package sample.pojo.rpcclient;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class BankRPCClient {

    public static void main(String[] args1) throws AxisFault {

        RPCServiceClient serviceClient = new RPCServiceClient();

        Options options = serviceClient.getOptions();

        EndpointReference targetEPR = new EndpointReference("http://localhost:8080/services/SayHello");

        options.setTo(targetEPR);
        
        // acceptRequestList
        QName opAcceptRequestList = new QName("http://service.pojo.sample", "acceptRequestList");
 
        Object[] opAcceptRequestListArgs = new Object[] { "1"};
        Class[] opAcceptRequestListReturnTypes = new Class[] { String.class };
        
        
        Object[] opAcceptRequestListResponse = serviceClient.invokeBlocking(opAcceptRequestList,
        		opAcceptRequestListArgs, opAcceptRequestListReturnTypes);
        
        String opAcceptRequestListResult = (String) opAcceptRequestListResponse[0];
        
        if (opAcceptRequestListResult == null) {
            System.out.println("opAcceptRequestList didn't initialize!");
            return;
        }
        
        // Displaying the result
        System.out.println(opAcceptRequestListResult);
    }
}
