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

import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.data.attributes.api.AttributeValue;
import org.gephi.data.attributes.type.DynamicFloat;
import org.gephi.data.attributes.type.Interval;
import org.gephi.data.properties.PropertiesColumn;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphFactory;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;
import org.gephi.graph.dhns.core.Dhns;
import org.gephi.io.data.DynamicModel;
import org.gephi.io.data.DynamicModel.TimeFormat;
import org.gephi.io.data.EdgeDefault;
import org.gephi.io.data.EdgeDraft;
import org.gephi.io.data.EdgeDraft.EdgeType;
import org.gephi.io.data.EdgeDraftGetter;
import org.gephi.io.data.NodeDraft;
import org.gephi.io.data.NodeDraftGetter;
import org.gephi.io.data.TemporaryAttributeModel;
import org.gephi.utils.Color;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 * @author Mathieu Bastian
 */
public class ImporterGEXF implements FileImporter, LongTask {

	// GEXF
	private static final String GEXF = "gexf";
	private static final String GEXF_VERSION = "version";
	private static final String GRAPH = "graph";
	private static final String GRAPH_DEFAULT_EDGETYPE = "defaultedgetype";
	private static final String GRAPH_TIMEFORMAT = "timeformat";
	private static final String GRAPH_TIMEFORMAT2 = "timetype"; // GEXF 1.1
	private static final String START = "start";
	private static final String END = "end";
	private static final String START_OPEN = "startopen";
	private static final String END_OPEN = "endopen";
	private static final String NODE = "node";
	private static final String NODE_ID = "id";
	private static final String NODE_LABEL = "label";
	private static final String NODE_PID = "pid";
	private static final String NODE_POSITION = "position";
	private static final String NODE_COLOR = "color";
	private static final String NODE_SIZE = "size";
	private static final String NODE_SPELL = "slice"; // GEXF 1.1
	private static final String NODE_SPELL2 = "spell";
	private static final String EDGE = "edge";
	private static final String EDGE_ID = "id";
	private static final String EDGE_SOURCE = "source";
	private static final String EDGE_TARGET = "target";
	private static final String EDGE_LABEL = "label";
	private static final String EDGE_TYPE = "type";
	private static final String EDGE_WEIGHT = "weight";
	private static final String EDGE_COLOR = "color";
	private static final String EDGE_SPELL = "slice"; // GEXF 1.1
	private static final String EDGE_SPELL2 = "spell";
	private static final String ATTRIBUTE = "attribute";
	private static final String ATTRIBUTE_ID = "id";
	private static final String ATTRIBUTE_TITLE = "title";
	private static final String ATTRIBUTE_TYPE = "type";
	private static final String ATTRIBUTE_DEFAULT = "default";
	private static final String ATTRIBUTES = "attributes";
	private static final String ATTRIBUTES_CLASS = "class"; // GEXF 1.0
	private static final String ATTRIBUTES_TYPE = "type";
	private static final String ATTRIBUTES_TYPE2 = "mode";
	private static final String ATTVALUE = "attvalue";
	private static final String ATTVALUE_FOR = "for";
	private static final String ATTVALUE_FOR2 = "id"; // GEXF 1.0
	private static final String ATTVALUE_VALUE = "value";
	// Architecture
	private Reader reader;
	private ContainerLoader container;
	private boolean cancel;
	private ProgressTicket progress;
	private TemporaryAttributeModel attributeModel;
	private boolean slices;

	public boolean execute(ContainerLoader container) {
		this.container = container;
		try {

			/*
			 * SAXParserFactory inputFactory = SAXParserFactory.newInstance();
			 * //if
			 * (inputFactory.isPropertySupported("javax.xml.stream.isValidating"
			 * )) { // inputFactory.setProperty("javax.xml.stream.isValidating",
			 * Boolean.FALSE); //} // inputFactory.setXMLReporter(new
			 * XMLReporter() { // // @Override // public void report(String
			 * message, String errorType, Object relatedInformation, Location
			 * location) throws XMLStreamException { //
			 * System.out.println("Error:" + errorType + ", message : " +
			 * message); // } // });
			 * 
			 * inputFactory.setValidating(false); xmlReader =
			 * inputFactory.newSAXParser().pgetXMLReader();
			 * xmlReader.parse(reader.) //xmlReader =
			 * inputFactory.createXMLStreamReader(reader);
			 * 
			 * xmlReader.parse(reader);
			 */

			DocumentBuilderFactory inputFactory = DocumentBuilderFactory
					.newInstance();

			inputFactory.setValidating(false);
			DocumentBuilder doc = inputFactory.newDocumentBuilder();
			Document xmlReader = doc.parse(new InputSource(reader));
			parseDocument(xmlReader);
			reader.close();

		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException(e);
		}
		return !cancel;
	}

	private void parseDocument(org.w3c.dom.Node n2) throws Exception {
		NodeList nodes = n2.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node n = nodes.item(i);
			String name = n.getNodeName();
			if(name.equals("#text"))
				continue;
			if (GEXF.equalsIgnoreCase(name)) {
				readGexf(n);
				parseDocument(n);
			} else if (GRAPH.equalsIgnoreCase(name)) {
				readGraph(n);
				parseDocument(n);
			} else if (NODE.equalsIgnoreCase(name)) {
				readNode(n, null);
			} else if (EDGE.equalsIgnoreCase(name)) {
				readEdge(n);
			} else if (ATTRIBUTES.equalsIgnoreCase(name)) {
				readAttributes(n);
			}else{
				parseDocument(n);
			}
			
		}
		// while (nodes.hasNext()) {
		//
		// Integer eventType = xmlReader.next();
		// if (eventType.equals(XMLEvent.START_ELEMENT)) {
		//
		// } else if (eventType.equals(XMLStreamReader.END_ELEMENT)) {
		// String name = xmlReader.getNodeName();
		// if (NODE.equalsIgnoreCase(name)) {
		// }
		// }
		// }
	}

	private void readGexf(org.w3c.dom.Node n) throws Exception {
		String version = "";

		// Attributes
		for (int i = 0; i < n.getAttributes().getLength(); i++) {
			String attName = n.getAttributes().item(i).getNodeName();
			if (GEXF_VERSION.equalsIgnoreCase(attName)) {
				version = n.getAttributes().item(i).getNodeValue();
			}
		}

		/*
		 * if (!version.isEmpty() && version.equals("1.0")) {
		 * 
		 * //report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
		 * "importerGEXF_log_version10"), Issue.Level.INFO)); } else if
		 * (!version.isEmpty() && version.equals("1.1")) { //report.logIssue(new
		 * Issue(NbBundle.getMessage(ImporterGEXF.class,
		 * "importerGEXF_log_version11"), Issue.Level.INFO)); } else if
		 * (!version.isEmpty() && version.equals("1.2")) { //report.logIssue(new
		 * Issue(NbBundle.getMessage(ImporterGEXF.class,
		 * "importerGEXF_log_version12"), Issue.Level.INFO)); } else if
		 * (!version.isEmpty() && version.equals("1.3")) { //report.logIssue(new
		 * Issue(NbBundle.getMessage(ImporterGEXF.class,
		 * "importerGEXF_log_version13"), Issue.Level.INFO)); } else {
		 * //report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
		 * "importerGEXF_log_version_undef"), Issue.Level.WARNING)); }
		 */
	}

	private void readGraph(org.w3c.dom.Node n) throws Exception {
		String mode = "";
		String defaultEdgeType = "";
		String start = "";
		String end = "";
		String timeFormat = "";

		// Attributes
		for (int i = 0; i < n.getAttributes().getLength(); i++) {
			String attName = n.getAttributes().item(i).getNodeName();
			if (GRAPH_DEFAULT_EDGETYPE.equalsIgnoreCase(attName)) {
				defaultEdgeType = n.getAttributes().item(i).getNodeValue();
			} else if (ATTRIBUTES_TYPE2.equalsIgnoreCase(attName)) {
				mode = n.getAttributes().item(i).getNodeValue();
			} else if (START.equalsIgnoreCase(attName)) {
				start = n.getAttributes().item(i).getNodeValue();
			} else if (END.equalsIgnoreCase(attName)) {
				end = n.getAttributes().item(i).getNodeValue();
			} else if (GRAPH_TIMEFORMAT.equalsIgnoreCase(attName)
					|| GRAPH_TIMEFORMAT2.equalsIgnoreCase(attName)) {
				timeFormat = n.getAttributes().item(i).getNodeValue();
			}
		}

		// Edge Type
		if (!defaultEdgeType.isEmpty()) {
			if (defaultEdgeType.equalsIgnoreCase("undirected")) {
				container.setEdgeDefault(EdgeDefault.UNDIRECTED);
			} else if (defaultEdgeType.equalsIgnoreCase("directed")) {
				container.setEdgeDefault(EdgeDefault.DIRECTED);
			} else if (defaultEdgeType.equalsIgnoreCase("mutual")) {
				// //report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_edgedouble"), Issue.Level.WARNING));
			} else {
				// //report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_defaultedgetype", defaultEdgeType),
				// Issue.Level.SEVERE));
			}
		}
		// TimeFormat
		if (!timeFormat.isEmpty()) {
			if ("double".equalsIgnoreCase(timeFormat)
					|| "float".equalsIgnoreCase(timeFormat)) {
				container.setTimeFormat(TimeFormat.DOUBLE);
			} else if ("date".equalsIgnoreCase(timeFormat)) {
				container.setTimeFormat(TimeFormat.DATE);
			} else if ("datetime".equalsIgnoreCase(timeFormat)) {
				container.setTimeFormat(TimeFormat.DATETIME);
			} else if ("timestamp".equalsIgnoreCase(timeFormat)) {
				container.setTimeFormat(TimeFormat.TIMESTAMP);
			}
		} else if (mode.equalsIgnoreCase("dynamic")) {
			container.setTimeFormat(TimeFormat.DOUBLE);
		}

		// Start & End
		if (!start.isEmpty()) {
			container.setTimeIntervalMin(start);
		}
		if (!end.isEmpty()) {
			container.setTimeIntervalMax(end);
		}
	}

	public HierarchicalGraph process() {
		Dhns graphModel = new Dhns();

		HierarchicalGraph graph = null;
		switch (((ContainerUnloader) container).getEdgeDefault()) {
		case DIRECTED:
			graph = graphModel.getHierarchicalDirectedGraph();
			break;
		case UNDIRECTED:
			graph = graphModel.getHierarchicalUndirectedGraph();
			break;
		case MIXED:
			graph = graphModel.getHierarchicalMixedGraph();
			break;
		default:
			graph = graphModel.getHierarchicalMixedGraph();
			break;
		}
		GraphFactory factory = graphModel.factory();

		attributeModel = new TemporaryAttributeModel();
		// TemporaryAttributeModel
		// Attributes - Creates columns for properties
		// attributeModel =
		// Lookup.getDefault().lookup(AttributeController.class).getModel();

		attributeModel.mergeModel(container.getAttributeModel());

		// Dynamic
		/*
		 * if (((ContainerUnloader)container).getTimeFormat() != null) {
		 * 
		 * DynamicController dynamicController = new
		 * DynamicLookup.getDefault().lookup(DynamicController.class); if
		 * (dynamicController != null) {
		 * dynamicController.setTimeFormat(container.getTimeFormat()); } }
		 */
		int nodeCount = 0;
		// Create all nodes
		for (NodeDraftGetter draftNode : ((ContainerUnloader) container)
				.getNodes()) {
			Node n = factory.newNode(draftNode.isAutoId() ? null : draftNode
					.getId());
			flushToNode(draftNode, n);
			draftNode.setNode(n);
			nodeCount++;
		}

		// Push nodes in data structure
		for (NodeDraftGetter draftNode : ((ContainerUnloader) container)
				.getNodes()) {
			Node n = draftNode.getNode();
			NodeDraftGetter[] parents = draftNode.getParents();
			if (parents != null) {
				for (int i = 0; i < parents.length; i++) {
					Node parent = parents[i].getNode();
					graph.addNode(n, parent);
				}
			} else {
				graph.addNode(n);
			}
		}

		// Create all edges and push to data structure
		int edgeCount = 0;
		for (EdgeDraftGetter edge : ((ContainerUnloader) container).getEdges()) {
			Node source = edge.getSource().getNode();
			Node target = edge.getTarget().getNode();
			Edge e = null;
			switch (((ContainerUnloader) container).getEdgeDefault()) {
			case DIRECTED:
				e = factory.newEdge(edge.isAutoId() ? null : edge.getId(),
						source, target, edge.getWeight(), true);
				break;
			case UNDIRECTED:
				e = factory.newEdge(edge.isAutoId() ? null : edge.getId(),
						source, target, edge.getWeight(), false);
				break;
			case MIXED:
				e = factory.newEdge(edge.isAutoId() ? null : edge.getId(),
						source, target, edge.getWeight(), edge.getType()
								.equals(EdgeType.UNDIRECTED) ? false : true);
				break;
			}

			flushToEdge(edge, e);
			edgeCount++;
			graph.addEdge(e);
		}
		return graph;
	}

	protected void flushToNode(NodeDraftGetter nodeDraft, Node node) {

		if (nodeDraft.getColor() != null) {
			node.getNodeData().setR(nodeDraft.getColor().getRed() / 255f);
			node.getNodeData().setG(nodeDraft.getColor().getGreen() / 255f);
			node.getNodeData().setB(nodeDraft.getColor().getBlue() / 255f);
		}

		if (nodeDraft.getLabel() != null) {
			node.getNodeData().setLabel(nodeDraft.getLabel());
		}

		if (node.getNodeData().getTextData() != null) {
			node.getNodeData().getTextData()
					.setVisible(nodeDraft.isLabelVisible());
		}

		if (nodeDraft.getLabelColor() != null
				&& node.getNodeData().getTextData() != null) {
			Color labelColor = nodeDraft.getLabelColor();
			node.getNodeData()
					.getTextData()
					.setColor(labelColor.getRed() / 255f,
							labelColor.getGreen() / 255f,
							labelColor.getBlue() / 255f,
							labelColor.getAlpha() / 255f);
		}

		if (nodeDraft.getLabelSize() != -1f
				&& node.getNodeData().getTextData() != null) {
			node.getNodeData().getTextData().setSize(nodeDraft.getLabelSize());
		}

		node.getNodeData().setX(nodeDraft.getX());
		node.getNodeData().setY(nodeDraft.getY());
		node.getNodeData().setZ(nodeDraft.getZ());

		if (nodeDraft.getSize() != 0 && !Float.isNaN(nodeDraft.getSize())) {
			node.getNodeData().setSize(nodeDraft.getSize());
		} else {
			node.getNodeData().setSize(10f);
		}

		if (nodeDraft.getTimeInterval() != null) {
			AttributeColumn col = attributeModel.getNodeTable().getColumn(
					DynamicModel.TIMEINTERVAL_COLUMN);
			if (col == null) {
				col = attributeModel.getNodeTable().addColumn(
						DynamicModel.TIMEINTERVAL_COLUMN, "Time Interval",
						AttributeType.TIME_INTERVAL, AttributeOrigin.PROPERTY,
						null);
			}
			node.getNodeData().getAttributes()
					.setValue(col.getIndex(), nodeDraft.getTimeInterval());
		}

		// Attributes
		flushToNodeAttributes(nodeDraft, node);
	}

	protected void flushToNodeAttributes(NodeDraftGetter nodeDraft, Node node) {
		if (node.getNodeData().getAttributes() != null) {
			AttributeRow row = (AttributeRow) node.getNodeData()
					.getAttributes();
			for (AttributeValue val : nodeDraft.getAttributeRow().getValues()) {
				if (!val.getColumn().getOrigin()
						.equals(AttributeOrigin.PROPERTY)
						&& val.getValue() != null) {
					row.setValue(val.getColumn(), val.getValue());
				}
			}
		}
	}

	protected void flushToEdge(EdgeDraftGetter edgeDraft, Edge edge) {
		if (edgeDraft.getColor() != null) {
			edge.getEdgeData().setR(edgeDraft.getColor().getRed() / 255f);
			edge.getEdgeData().setG(edgeDraft.getColor().getGreen() / 255f);
			edge.getEdgeData().setB(edgeDraft.getColor().getBlue() / 255f);
		} else {
			edge.getEdgeData().setR(-1f);
			edge.getEdgeData().setG(-1f);
			edge.getEdgeData().setB(-1f);
		}

		if (edgeDraft.getLabel() != null) {
			edge.getEdgeData().setLabel(edgeDraft.getLabel());
		}

		if (edge.getEdgeData().getTextData() != null) {
			edge.getEdgeData().getTextData()
					.setVisible(edgeDraft.isLabelVisible());
		}

		if (edgeDraft.getLabelSize() != -1f
				&& edge.getEdgeData().getTextData() != null) {
			edge.getEdgeData().getTextData().setSize(edgeDraft.getLabelSize());
		}

		if (edgeDraft.getLabelColor() != null
				&& edge.getEdgeData().getTextData() != null) {
			Color labelColor = edgeDraft.getLabelColor();
			edge.getEdgeData()
					.getTextData()
					.setColor(labelColor.getRed() / 255f,
							labelColor.getGreen() / 255f,
							labelColor.getBlue() / 255f,
							labelColor.getAlpha() / 255f);
		}

		if (edgeDraft.getTimeInterval() != null) {
			AttributeColumn col = attributeModel.getEdgeTable().getColumn(
					DynamicModel.TIMEINTERVAL_COLUMN);
			if (col == null) {
				col = attributeModel.getEdgeTable().addColumn(
						DynamicModel.TIMEINTERVAL_COLUMN, "Time Interval",
						AttributeType.TIME_INTERVAL, AttributeOrigin.PROPERTY,
						null);
			}
			edge.getEdgeData().getAttributes()
					.setValue(col.getIndex(), edgeDraft.getTimeInterval());
		}

		// Attributes
		flushToEdgeAttributes(edgeDraft, edge);
	}

	protected void flushToEdgeAttributes(EdgeDraftGetter edgeDraft, Edge edge) {
		if (edge.getEdgeData().getAttributes() != null) {
			AttributeRow row = (AttributeRow) edge.getEdgeData()
					.getAttributes();
			for (AttributeValue val : edgeDraft.getAttributeRow().getValues()) {
				if (!val.getColumn().getOrigin()
						.equals(AttributeOrigin.PROPERTY)
						&& val.getValue() != null) {
					row.setValue(val.getColumn(), val.getValue());
				}
			}
		}

		// Dynamic Weight
		AttributeColumn dynamicWeightCol = container
				.getAttributeModel()
				.getEdgeTable()
				.getColumn(PropertiesColumn.EDGE_WEIGHT.getTitle(),
						AttributeType.DYNAMIC_FLOAT);
		if (dynamicWeightCol != null) {
			DynamicFloat weight = (DynamicFloat) edgeDraft.getAttributeRow()
					.getValue(dynamicWeightCol.getIndex());
			AttributeRow row = (AttributeRow) edge.getEdgeData()
					.getAttributes();
			if (weight == null) {
				row.setValue(
						PropertiesColumn.EDGE_WEIGHT.getIndex(),
						new DynamicFloat(
								new Interval<Float>(Double.NEGATIVE_INFINITY,
										Double.POSITIVE_INFINITY, edgeDraft
												.getWeight())));
			} else {
				row.setValue(PropertiesColumn.EDGE_WEIGHT.getIndex(), weight);
			}
		}
	}

	private void readNode(org.w3c.dom.Node n, NodeDraft parent)
			throws Exception {
		String id = "";
		String label = "";
		String startDate = "";
		String endDate = "";
		String pid = "";
		boolean startOpen = false;
		boolean endOpen = false;

		// Attributes
		for (int i = 0; i < n.getAttributes().getLength(); i++) {
			String attName = n.getAttributes().item(i).getNodeName();
			if (NODE_ID.equalsIgnoreCase(attName)) {
				id = n.getAttributes().item(i).getNodeValue();
			} else if (NODE_LABEL.equalsIgnoreCase(attName)) {
				label = n.getAttributes().item(i).getNodeValue();
			} else if (START.equalsIgnoreCase(attName)) {
				startDate = n.getAttributes().item(i).getNodeValue();
			} else if (START_OPEN.equalsIgnoreCase(attName)) {
				startDate = n.getAttributes().item(i).getNodeValue();
				startOpen = true;
			} else if (END.equalsIgnoreCase(attName)) {
				endDate = n.getAttributes().item(i).getNodeValue();
			} else if (END_OPEN.equalsIgnoreCase(attName)) {
				endDate = n.getAttributes().item(i).getNodeValue();
				endOpen = true;
			} else if (NODE_PID.equalsIgnoreCase(attName)) {
				pid = n.getAttributes().item(i).getNodeValue();
			}
		}

		if (id.isEmpty()) {
			// //report.logIssue(new
			// Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_nodeid"), Issue.Level.SEVERE));
			return;
		}

		NodeDraft node = null;
		if (container.nodeExists(id)) {
			node = container.getNode(id);
		} else {
			node = container.factory().newNodeDraft();
		}
		node.setId(id);
		node.setLabel(label);

		// Parent
		if (parent != null) {
			node.setParent(parent);
		} else if (!pid.isEmpty()) {
			NodeDraft parentNode = container.getNode(pid);
			if (parentNode == null) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_pid_notfound", pid, id),
				// Issue.Level.SEVERE));
			} else {
				node.setParent(parentNode);
			}
		}

		if (!container.nodeExists(id)) {
			container.addNode(node);
		}

		boolean end = false;
		slices = false;

		readNodeChild(n, node);

		// Dynamic
		if (!slices && (!startDate.isEmpty() || !endDate.isEmpty())) {
			try {
				node.addTimeInterval(startDate, endDate, startOpen, endOpen);
			} catch (IllegalArgumentException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_node_timeinterval_parseerror", id),
				// Issue.Level.SEVERE));
			}
		}
	}
	
	private void readNodeChild(org.w3c.dom.Node n, NodeDraft node) throws Exception{
		NodeList nodeList = n.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			org.w3c.dom.Node c = nodeList.item(i);
			String name = c.getNodeName().substring(c.getNodeName().indexOf(":")+1);
			if (ATTVALUE.equalsIgnoreCase(c.getNodeName())) {
				readNodeAttValue(c, node);
			} else if (NODE_POSITION.equalsIgnoreCase(name)) {
				readNodePosition(c, node);
			} else if (NODE_COLOR.equalsIgnoreCase(name)) {
				readNodeColor(c, node);
			} else if (NODE_SIZE.equalsIgnoreCase(name)) {
				readNodeSize(c, node);
			} else if (NODE_SPELL.equalsIgnoreCase(name)
					|| NODE_SPELL2.equalsIgnoreCase(name)) {
				readNodeSpell(c, node);
				slices = true;
			} else if (NODE.equalsIgnoreCase(name)) {
				readNode(c, node);
			}else if(!name.endsWith("#text")){
				readNodeChild(c, node);	
			}
			

		}
	
	}
	

	private void readNodeAttValue(org.w3c.dom.Node c, NodeDraft node) {
		String fore = "";
		String value = "";
		String startDate = "";
		String endDate = "";
		boolean startOpen = false;
		boolean endOpen = false;

		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if (ATTVALUE_FOR.equalsIgnoreCase(attName)
					|| ATTVALUE_FOR2.equalsIgnoreCase(attName)) {
				fore = c.getAttributes().item(i).getNodeValue();
			} else if (ATTVALUE_VALUE.equalsIgnoreCase(attName)) {
				value = c.getAttributes().item(i).getNodeValue();
			} else if (START.equalsIgnoreCase(attName)) {
				startDate = c.getAttributes().item(i).getNodeValue();
			} else if (START_OPEN.equalsIgnoreCase(attName)) {
				startDate = c.getAttributes().item(i).getNodeValue();
				startOpen = true;
			} else if (END.equalsIgnoreCase(attName)) {
				endDate = c.getAttributes().item(i).getNodeValue();
			} else if (END_OPEN.equalsIgnoreCase(attName)) {
				endDate = c.getAttributes().item(i).getNodeValue();
				endOpen = true;
			}
		}

		if (fore.isEmpty()) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_datakey", node), Issue.Level.SEVERE));
			return;
		}

		if (!value.isEmpty()) {
			// Data attribute value
			AttributeColumn column = container.getAttributeModel()
					.getNodeTable().getColumn(fore);
			if (column != null) {
				if (!startDate.isEmpty() || !endDate.isEmpty()) {
					// Dynamic
					try {
						node.addAttributeValue(column, value, startDate,
								endDate, startOpen, endOpen);
					} catch (IllegalArgumentException e) {
						// report.logIssue(new
						// Issue(NbBundle.getMessage(ImporterGEXF.class,
						// "importerGEXF_error_nodeattribute_timeinterval_parseerror",
						// node), Issue.Level.SEVERE));
					} catch (Exception e) {
						// report.logIssue(new
						// Issue(NbBundle.getMessage(ImporterGEXF.class,
						// "importerGEXF_error_datavalue", fore, node,
						// column.getTitle()), Issue.Level.SEVERE));
					}
				} else {
					if (column.getType().isDynamicType()) {
						node.addAttributeValue(column, value);
					} else {
						try {
							Object val = column.getType().parse(value);
							node.addAttributeValue(column, val);
						} catch (Exception e) {
							// report.logIssue(new
							// Issue(NbBundle.getMessage(ImporterGEXF.class,
							// "importerGEXF_error_datavalue", fore, node,
							// column.getTitle()), Issue.Level.SEVERE));
						}
					}
				}
			}
		}
	}

	private void readNodeColor(org.w3c.dom.Node c, NodeDraft node)
			throws Exception {
		String rStr = "";
		String gStr = "";
		String bStr = "";
		String aStr = "";

		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if ("r".equalsIgnoreCase(attName)) {
				rStr = c.getAttributes().item(i).getNodeValue();
			} else if ("g".equalsIgnoreCase(attName)) {
				gStr = c.getAttributes().item(i).getNodeValue();
			} else if ("b".equalsIgnoreCase(attName)) {
				bStr = c.getAttributes().item(i).getNodeValue();
			} else if ("a".equalsIgnoreCase(attName)) {
				aStr = c.getAttributes().item(i).getNodeValue();
			}
		}

		int r = (rStr.isEmpty()) ? 0 : Integer.parseInt(rStr);
		int g = (gStr.isEmpty()) ? 0 : Integer.parseInt(gStr);
		int b = (bStr.isEmpty()) ? 0 : Integer.parseInt(bStr);
		float a = (aStr.isEmpty()) ? 0 : Float.parseFloat(aStr); // not used
		if (r < 0 || r > 255) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_nodecolorvalue", rStr, node, "r"),
			// Issue.Level.WARNING));
		}
		if (g < 0 || g > 255) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_nodecolorvalue", gStr, node, "g"),
			// Issue.Level.WARNING));
		}
		if (b < 0 || b > 255) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_nodecolorvalue", bStr, node, "b"),
			// Issue.Level.WARNING));
		}
		if (a < 0f || a > 1f) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_nodeopacityvalue", aStr, node),
			// Issue.Level.WARNING));
		}

		node.setColor(new Color(r, g, b));
	}

	private void readNodePosition(org.w3c.dom.Node c, NodeDraft node)
			throws Exception {
		String xStr = "";
		String yStr = "";
		String zStr = "";

		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if ("x".equalsIgnoreCase(attName)) {
				xStr = c.getAttributes().item(i).getNodeValue();
			} else if ("y".equalsIgnoreCase(attName)) {
				yStr = c.getAttributes().item(i).getNodeValue();
			} else if ("z".equalsIgnoreCase(attName)) {
				zStr = c.getAttributes().item(i).getNodeValue();
			}
		}

		if (!xStr.isEmpty()) {
			try {
				float x = Float.parseFloat(xStr);
				node.setX(x);
			} catch (NumberFormatException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_nodeposition", node, "X"),
				// Issue.Level.WARNING));
			}
		}
		if (!yStr.isEmpty()) {
			try {
				float y = Float.parseFloat(yStr);
				node.setY(y);
			} catch (NumberFormatException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_nodeposition", node, "Y"),
				// Issue.Level.WARNING));
			}
		}
		if (!zStr.isEmpty()) {
			try {
				float z = Float.parseFloat(zStr);
				node.setZ(z);
			} catch (NumberFormatException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_nodeposition", node, "Z"),
				// Issue.Level.WARNING));
			}
		}
	}

	private void readNodeSize(org.w3c.dom.Node c, NodeDraft node)
			throws Exception {
		String attName = c.getAttributes().item(0).getNodeName();
		if ("value".equalsIgnoreCase(attName)) {
			String sizeStr = c.getAttributes().item(0).getNodeValue();
			if (!sizeStr.isEmpty()) {
				try {
					float size = Float.parseFloat(sizeStr);
					node.setSize(size);
				} catch (NumberFormatException e) {
					// report.logIssue(new
					// Issue(NbBundle.getMessage(ImporterGEXF.class,
					// "importerGEXF_error_nodesize", node),
					// Issue.Level.WARNING));
				}
			}
		}
	}

	private void readNodeSpell(org.w3c.dom.Node c, NodeDraft node)
			throws Exception {
		String start = "";
		String end = "";
		boolean startOpen = false;
		boolean endOpen = false;

		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if (START.equalsIgnoreCase(attName)) {
				start = c.getAttributes().item(i).getNodeValue();
			} else if (END.equalsIgnoreCase(attName)) {
				end = c.getAttributes().item(i).getNodeValue();
			} else if (START_OPEN.equalsIgnoreCase(attName)) {
				start = c.getAttributes().item(i).getNodeValue();
				startOpen = true;
			} else if (END_OPEN.equalsIgnoreCase(attName)) {
				end = c.getAttributes().item(i).getNodeValue();
				endOpen = true;
			}
		}

		if (!start.isEmpty() || !end.isEmpty()) {
			try {
				node.addTimeInterval(start, end, startOpen, endOpen);
			} catch (IllegalArgumentException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_node_timeinterval_parseerror", node),
				// Issue.Level.SEVERE));
			}
		}
	}

	private void readEdge(org.w3c.dom.Node n) throws Exception {
		String id = "";
		String label = "";
		String source = "";
		String target = "";
		String weight = "";
		String edgeType = "";
		String startDate = "";
		String endDate = "";
		boolean startOpen = false;
		boolean endOpen = false;

		// Attributes
		for (int i = 0; i < n.getAttributes().getLength(); i++) {
			String attName = n.getAttributes().item(i).getNodeName();
			if (EDGE_SOURCE.equalsIgnoreCase(attName)) {
				source = n.getAttributes().item(i).getNodeValue();
			} else if (EDGE_TARGET.equalsIgnoreCase(attName)) {
				target = n.getAttributes().item(i).getNodeValue();
			} else if (EDGE_WEIGHT.equalsIgnoreCase(attName)) {
				weight = n.getAttributes().item(i).getNodeValue();
			} else if (EDGE_ID.equalsIgnoreCase(attName)) {
				id = n.getAttributes().item(i).getNodeValue();
			} else if (EDGE_TYPE.equalsIgnoreCase(attName)) {
				edgeType = n.getAttributes().item(i).getNodeValue();
			} else if (EDGE_LABEL.equalsIgnoreCase(attName)) {
				label = n.getAttributes().item(i).getNodeValue();
			} else if (START.equalsIgnoreCase(attName)) {
				startDate = n.getAttributes().item(i).getNodeValue();
			} else if (END.equalsIgnoreCase(attName)) {
				endDate = n.getAttributes().item(i).getNodeValue();
			} else if (START_OPEN.equalsIgnoreCase(attName)) {
				startDate = n.getAttributes().item(i).getNodeValue();
				startOpen = true;
			} else if (END_OPEN.equalsIgnoreCase(attName)) {
				endDate = n.getAttributes().item(i).getNodeValue();
				endOpen = true;
			}
		}

		EdgeDraft edge = container.factory().newEdgeDraft();

		try {
			NodeDraft nodeSource = container.getNode(source);
			NodeDraft nodeTarget = container.getNode(target);
			edge.setSource(nodeSource);
			edge.setTarget(nodeTarget);
		} catch (Exception e) {
			if (source.isEmpty()) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_edgesource"), Issue.Level.SEVERE));
			} else if (target.isEmpty()) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_edgetarget"), Issue.Level.SEVERE));
			} else {
				// report.logIssue(new Issue(e.getMessage(),
				// Issue.Level.SEVERE));
			}
			return;
		}

		// Type
		if (!edgeType.isEmpty()) {
			if (edgeType.equalsIgnoreCase("undirected")) {
				edge.setType(EdgeDraft.EdgeType.UNDIRECTED);
			} else if (edgeType.equalsIgnoreCase("directed")) {
				edge.setType(EdgeDraft.EdgeType.DIRECTED);
			} else if (edgeType.equalsIgnoreCase("mutual")) {
				edge.setType(EdgeDraft.EdgeType.MUTUAL);
			} else {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_edgetype", edgeType, edge),
				// Issue.Level.SEVERE));
			}
		}

		// Id
		if (!id.isEmpty()) {
			edge.setId(id);
		}

		// Weight
		if (!weight.isEmpty()) {
			try {
				float weightNumber = Float.parseFloat(weight);
				edge.setWeight(weightNumber);
			} catch (NumberFormatException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_edgeweight", edge),
				// Issue.Level.WARNING));
			}
		}

		// Label
		if (!label.isEmpty()) {
			edge.setLabel(label);
		}

		container.addEdge(edge);

		boolean end = false;
		boolean spells = false;

		NodeList nodes = n.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node c = nodes.item(i);

			if (ATTVALUE.equalsIgnoreCase(c.getNodeName())) {
				readEdgeAttValue(c, edge);
			} else if (EDGE_COLOR.equalsIgnoreCase(c.getNodeName())) {
				readEdgeColor(c, edge);
			} else if (EDGE_SPELL.equalsIgnoreCase(c.getNodeName())
					|| EDGE_SPELL2.equalsIgnoreCase(c.getNodeName())) {
				readEdgeSpell(c, edge);
				spells = true;
			}

		}

		// Dynamic
		if (!spells && (!startDate.isEmpty() || !endDate.isEmpty())) {
			try {
				edge.addTimeInterval(startDate, endDate, startOpen, endOpen);
			} catch (IllegalArgumentException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_edge_timeinterval_parseerror", edge),
				// Issue.Level.SEVERE));
			}
		}
	}

	private void readEdgeAttValue(org.w3c.dom.Node c, EdgeDraft edge) {
		String fore = "";
		String value = "";
		String startDate = "";
		String endDate = "";
		boolean startOpen = false;
		boolean endOpen = false;

		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if (ATTVALUE_FOR.equalsIgnoreCase(attName)
					|| ATTVALUE_FOR2.equalsIgnoreCase(attName)) {
				fore = c.getAttributes().item(i).getNodeValue();
			} else if (ATTVALUE_VALUE.equalsIgnoreCase(attName)) {
				value = c.getAttributes().item(i).getNodeValue();
			} else if (START.equalsIgnoreCase(attName)) {
				startDate = c.getAttributes().item(i).getNodeValue();
			} else if (END.equalsIgnoreCase(attName)) {
				endDate = c.getAttributes().item(i).getNodeValue();
			} else if (START_OPEN.equalsIgnoreCase(attName)) {
				startDate = c.getAttributes().item(i).getNodeValue();
				startOpen = true;
			} else if (END_OPEN.equalsIgnoreCase(attName)) {
				endDate = c.getAttributes().item(i).getNodeValue();
				endOpen = true;
			}
		}

		if (fore.isEmpty()) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_datakey", edge), Issue.Level.SEVERE));
			return;
		}

		if (!value.isEmpty()) {
			// Data attribute value
			AttributeColumn column = container.getAttributeModel()
					.getEdgeTable().getColumn(fore);
			if (column != null) {
				if (!startDate.isEmpty() || !endDate.isEmpty()) {
					// Dynamic
					try {
						edge.addAttributeValue(column, value, startDate,
								endDate, startOpen, endOpen);
					} catch (IllegalArgumentException e) {
						// report.logIssue(new
						// Issue(NbBundle.getMessage(ImporterGEXF.class,
						// "importerGEXF_error_edgeattribute_timeinterval_parseerror",
						// edge), Issue.Level.SEVERE));
					} catch (Exception e) {
						// report.logIssue(new
						// Issue(NbBundle.getMessage(ImporterGEXF.class,
						// "importerGEXF_error_datavalue", fore, edge,
						// column.getTitle()), Issue.Level.SEVERE));
					}
				} else {
					if (column.getType().isDynamicType()) {
						edge.addAttributeValue(column, value);
					} else {
						try {
							Object val = column.getType().parse(value);
							edge.addAttributeValue(column, val);
						} catch (Exception e) {
							// report.logIssue(new
							// Issue(NbBundle.getMessage(ImporterGEXF.class,
							// "importerGEXF_error_datavalue", fore, edge,
							// column.getTitle()), Issue.Level.SEVERE));
						}
					}
				}
			}
		}
	}

	private void readEdgeColor(org.w3c.dom.Node c, EdgeDraft edge)
			throws Exception {
		String rStr = "";
		String gStr = "";
		String bStr = "";
		String aStr = "";

		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if ("r".equalsIgnoreCase(attName)) {
				rStr = c.getAttributes().item(i).getNodeValue();
			} else if ("g".equalsIgnoreCase(attName)) {
				gStr = c.getAttributes().item(i).getNodeValue();
			} else if ("b".equalsIgnoreCase(attName)) {
				bStr = c.getAttributes().item(i).getNodeValue();
			} else if ("a".equalsIgnoreCase(attName)) {
				aStr = c.getAttributes().item(i).getNodeValue();
			}
		}

		int r = (rStr.isEmpty()) ? 0 : Integer.parseInt(rStr);
		int g = (gStr.isEmpty()) ? 0 : Integer.parseInt(gStr);
		int b = (bStr.isEmpty()) ? 0 : Integer.parseInt(bStr);
		float a = (aStr.isEmpty()) ? 0 : Float.parseFloat(aStr); // not used
		if (r < 0 || r > 255) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_edgecolorvalue", rStr, edge, "r"),
			// Issue.Level.WARNING));
		}
		if (g < 0 || g > 255) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_edgecolorvalue", gStr, edge, "g"),
			// Issue.Level.WARNING));
		}
		if (b < 0 || b > 255) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_edgecolorvalue", bStr, edge, "b"),
			// Issue.Level.WARNING));
		}
		if (a < 0f || a > 1f) {
			// report.logIssue(new Issue(NbBundle.getMessage(ImporterGEXF.class,
			// "importerGEXF_error_edgeopacityvalue", aStr, edge),
			// Issue.Level.WARNING));
		}

		edge.setColor(new Color(r, g, b));
	}

	private void readEdgeSpell(org.w3c.dom.Node c, EdgeDraft edge)
			throws Exception {
		String start = "";
		String end = "";
		boolean startOpen = false;
		boolean endOpen = false;

		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if (START.equalsIgnoreCase(attName)) {
				start = c.getAttributes().item(i).getNodeValue();
			} else if (END.equalsIgnoreCase(attName)) {
				end = c.getAttributes().item(i).getNodeValue();
			} else if (START_OPEN.equalsIgnoreCase(attName)) {
				start = c.getAttributes().item(i).getNodeValue();
				startOpen = true;
			} else if (END_OPEN.equalsIgnoreCase(attName)) {
				end = c.getAttributes().item(i).getNodeValue();
				endOpen = true;
			}
		}

		if (!start.isEmpty() || !end.isEmpty()) {
			try {
				edge.addTimeInterval(start, end, startOpen, endOpen);
			} catch (IllegalArgumentException e) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_edge_timeinterval_parseerror", edge),
				// Issue.Level.SEVERE));
			}
		}
	}

	private void readAttributes(org.w3c.dom.Node n) throws Exception {
		String classAtt = "";
		String typeAtt = "";
		for (int i = 0; i < n.getAttributes().getLength(); i++) {
			String attName = n.getAttributes().item(i).getNodeName();
			if (ATTRIBUTES_CLASS.equalsIgnoreCase(attName)) {
				classAtt = n.getAttributes().item(i).getNodeValue();
			} else if (ATTRIBUTES_TYPE.equalsIgnoreCase(attName)
					|| ATTRIBUTES_TYPE2.equalsIgnoreCase(attName)) {
				typeAtt = n.getAttributes().item(i).getNodeValue();
			}
		}

		boolean end = false;

		NodeList nodes = n.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node c = nodes.item(i);

			if (ATTRIBUTE.equalsIgnoreCase(c.getNodeName())) {
				readAttribute(c, classAtt, typeAtt);
			}

		}
	}

	private void readAttribute(org.w3c.dom.Node c, String classAtt,
			String typeAtt) throws Exception {
		String id = "";
		String type = "";
		String title = "";
		String defaultStr = "";
		for (int i = 0; i < c.getAttributes().getLength(); i++) {
			String attName = c.getAttributes().item(i).getNodeName();
			if (ATTRIBUTE_ID.equalsIgnoreCase(attName)) {
				id = c.getAttributes().item(i).getNodeValue();
			} else if (ATTRIBUTE_TYPE.equalsIgnoreCase(attName)) {
				type = c.getAttributes().item(i).getNodeValue();
			} else if (ATTRIBUTE_TITLE.equalsIgnoreCase(attName)) {
				title = c.getAttributes().item(i).getNodeValue();
			}
		}

		if (title.isEmpty()) {
			title = id;
		}

		if (!id.isEmpty() && !type.isEmpty()) {
			// Class type
			if (classAtt.isEmpty()
					|| !(classAtt.equalsIgnoreCase("node") || classAtt
							.equalsIgnoreCase("edge"))) {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_attributeclass", title),
				// Issue.Level.SEVERE));
			}

			// Default?
			boolean end = false;
			boolean defaultFlag = false;

			NodeList nodes = c.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				org.w3c.dom.Node n = nodes.item(i);

				if (ATTRIBUTE_DEFAULT.equalsIgnoreCase(n.getNodeName())) {
					defaultFlag = true;
				}

				if (defaultFlag && n.getTextContent().length() > 0
						&& !n.getTextContent().equals(" ")) {
					defaultStr = n.getTextContent();
				}

			}

			// Dynamic?
			boolean dynamic = typeAtt.equalsIgnoreCase("dynamic");

			// Type
			AttributeType attributeType = AttributeType.STRING;
			if (type.equalsIgnoreCase("boolean")
					|| type.equalsIgnoreCase("bool")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_BOOLEAN
						: AttributeType.BOOLEAN;
			} else if (type.equalsIgnoreCase("integer")
					|| type.equalsIgnoreCase("int")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_INT
						: AttributeType.INT;
			} else if (type.equalsIgnoreCase("long")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_LONG
						: AttributeType.LONG;
			} else if (type.equalsIgnoreCase("float")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_FLOAT
						: AttributeType.FLOAT;
			} else if (type.equalsIgnoreCase("double")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_DOUBLE
						: AttributeType.DOUBLE;
			} else if (type.equalsIgnoreCase("string")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_STRING
						: AttributeType.STRING;
			} else if (type.equalsIgnoreCase("bigdecimal")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_BIGDECIMAL
						: AttributeType.BIGDECIMAL;
			} else if (type.equalsIgnoreCase("biginteger")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_BIGINTEGER
						: AttributeType.BIGINTEGER;
			} else if (type.equalsIgnoreCase("byte")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_BYTE
						: AttributeType.BYTE;
			} else if (type.equalsIgnoreCase("char")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_CHAR
						: AttributeType.CHAR;
			} else if (type.equalsIgnoreCase("short")) {
				attributeType = dynamic ? AttributeType.DYNAMIC_SHORT
						: AttributeType.SHORT;
			} else if (type.equalsIgnoreCase("listboolean")) {
				attributeType = AttributeType.LIST_BOOLEAN;
			} else if (type.equalsIgnoreCase("listint")) {
				attributeType = AttributeType.LIST_INTEGER;
			} else if (type.equalsIgnoreCase("listlong")) {
				attributeType = AttributeType.LIST_LONG;
			} else if (type.equalsIgnoreCase("listfloat")) {
				attributeType = AttributeType.LIST_FLOAT;
			} else if (type.equalsIgnoreCase("listdouble")) {
				attributeType = AttributeType.LIST_DOUBLE;
			} else if (type.equalsIgnoreCase("liststring")) {
				attributeType = AttributeType.LIST_STRING;
			} else if (type.equalsIgnoreCase("listbigdecimal")) {
				attributeType = AttributeType.LIST_BIGDECIMAL;
			} else if (type.equalsIgnoreCase("listbiginteger")) {
				attributeType = AttributeType.LIST_BIGINTEGER;
			} else if (type.equalsIgnoreCase("listbyte")) {
				attributeType = AttributeType.LIST_BYTE;
			} else if (type.equalsIgnoreCase("listchar")) {
				attributeType = AttributeType.LIST_CHARACTER;
			} else if (type.equalsIgnoreCase("listshort")) {
				attributeType = AttributeType.LIST_SHORT;
			} else {
				// report.logIssue(new
				// Issue(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_error_attributetype2", type),
				// Issue.Level.SEVERE));
				return;
			}

			// Default Object
			Object defaultValue = null;
			if (!defaultStr.isEmpty()) {
				try {
					defaultValue = attributeType.parse(defaultStr);
					// report.log(NbBundle.getMessage(ImporterGEXF.class,
					// "importerGEXF_log_default", defaultStr, title));
				} catch (Exception e) {
					// report.logIssue(new
					// Issue(NbBundle.getMessage(ImporterGEXF.class,
					// "importerGEXF_error_attributedefault", title,
					// attributeType.getTypeString()), Issue.Level.SEVERE));
				}
			}

			// Add to model
			if ("node".equalsIgnoreCase(classAtt) || classAtt.isEmpty()) {
				if (container.getAttributeModel().getNodeTable().hasColumn(id)
						|| container.getAttributeModel().getNodeTable()
								.hasColumn(title)) {
					// report.log(NbBundle.getMessage(ImporterGEXF.class,
					// "importerGEXF_error_attributecolumn_exist", id));
					return;
				}
				container
						.getAttributeModel()
						.getNodeTable()
						.addColumn(id, title, attributeType,
								AttributeOrigin.DATA, defaultValue);
				// report.log(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_log_nodeattribute", title,
				// attributeType.getTypeString()));
			} else if ("edge".equalsIgnoreCase(classAtt) || classAtt.isEmpty()) {
				if ((id.equalsIgnoreCase("weight") || title
						.equalsIgnoreCase("weight"))
						&& dynamic
						&& attributeType.equals(AttributeType.DYNAMIC_FLOAT)) {
					// Dynamic weight
					// report.log(NbBundle.getMessage(ImporterGEXF.class,
					// "importerGEXF_log_dynamic_weight", id));
					container
							.getAttributeModel()
							.getEdgeTable()
							.removeColumn(
									container
											.getAttributeModel()
											.getEdgeTable()
											.getColumn(
													PropertiesColumn.EDGE_WEIGHT
															.getIndex()));
					container
							.getAttributeModel()
							.getEdgeTable()
							.addColumn(id,
									PropertiesColumn.EDGE_WEIGHT.getTitle(),
									attributeType, AttributeOrigin.PROPERTY,
									defaultValue);
					return;
				} else if (container.getAttributeModel().getEdgeTable()
						.hasColumn(id)
						|| container.getAttributeModel().getEdgeTable()
								.hasColumn(title)) {
					// report.log(NbBundle.getMessage(ImporterGEXF.class,
					// "importerGEXF_error_attributecolumn_exist", id));
					return;
				}
				container
						.getAttributeModel()
						.getEdgeTable()
						.addColumn(id, title, attributeType,
								AttributeOrigin.DATA, defaultValue);
				// report.log(NbBundle.getMessage(ImporterGEXF.class,
				// "importerGEXF_log_edgeattribute", title,
				// attributeType.getTypeString()));
			}
		}
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public ContainerLoader getContainer() {
		return container;
	}

	public boolean cancel() {
		cancel = true;
		return true;
	}

	public void setProgressTicket(ProgressTicket progressTicket) {
		this.progress = progressTicket;
	}
}
