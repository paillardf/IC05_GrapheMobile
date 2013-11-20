/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 2011 Gephi Consortium. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 3 only ("GPL") or the Common
Development and Distribution License("CDDL") (collectively, the
"License"). You may not use this file except in compliance with the
License. You can obtain a copy of the License at
http://gephi.org/about/legal/license-notice/
or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
specific language governing permissions and limitations under the
License.  When distributing the software, include this License Header
Notice in each file and include the License files at
/cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
License Header, with the fields enclosed by brackets [] replaced by
your own identifying information:
"Portions Copyrighted [year] [name of copyright owner]"

If you wish your version of this file to be governed by only the CDDL
or only the GPL Version 3, indicate your decision by adding
"[Contributor] elects to include this software in this distribution
under the [CDDL or GPL Version 3] license." If you do not indicate a
single choice of license, a recipient has the option to distribute
your version of this file under either the CDDL, the GPL Version 3 or
to extend the choice of license to its licensees as provided above.
However, if you add GPL Version 3 code and therefore, elected the GPL
Version 3 license, then the option applies only if the new code is
made subject to such option by the copyright holder.

Contributor(s):

Portions Copyrighted 2011 Gephi Consortium.
*/
package org.gephi.io;

import javax.annotation.processing.Processor;

/**
 * A container is created each time data are imported by <b>importers</b>. Its role is to host all data
 * collected by importers during import process. After pushing data in the container, its content can be
 * analysed to verify its validity and then be processed by <b>processors</b>. Thus containers are
 * <b>loaded</b> by importers and <b>unloaded</b> by processors.
 * <p>
 * See {@link ContainerLoader} for how to push graph and attributes data in the container and see
 * {@link  ContainerUnloader} for how to retrieve data in the container.
 *
 * @author Mathieu Bastian
 * @see Importer
 * @see Processor
 */
public interface Container {

    /**
     * Set the source of the data put in the container. Could be a file name.
     * @param source the original source of data.
     * @throws NullPointerException if <code>source</code> is <code>null</code>
     */
    public void setSource(String source);

    /**
     * If exists, returns the source of the data.
     * @return the source of the data, or <code>null</code> if source is not defined.
     */
    public String getSource();

    /**
     * Get containers loading interface. The <b>loader</b> is used by modules which put data in the
     * container, whereas the <b>unloader</b> interface is used by modules which read containers content.
     * @return the containers loading interface
     */
    public ContainerLoader getLoader();

    /**
     * Get containers unloading interface. The <b>unloader</b> interface is used by modules which read
     * containers content, whereas the <b>loader</b> is used for pushing data in the container.
     * @return the container unloading interface
     */
    public ContainerUnloader getUnloader();

    public void setAutoScale(boolean autoscale);

    public boolean isAutoScale();

    public void setAllowSelfLoop(boolean value);

    public void setAllowAutoNode(boolean value);

    public void setAllowParallelEdge(boolean value);

    public void setUndirectedSumDirectedEdgesWeight(boolean value);

   
    /**
     * This method must be called after the loading is complete and before unloading. Its aim is to verify data consistency as a whole.
     * @return <code>true</code> if container data is consistent, <code>false</code> otherwise
     */
    public boolean verify();

    /**
     * Close the current loading and clean content before unloading.
     */
    public void closeLoader();

    public boolean isDynamicGraph();

    public boolean isHierarchicalGraph();
}
