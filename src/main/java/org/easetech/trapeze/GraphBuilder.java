/**
 * 
 */
package org.easetech.trapeze;

import java.io.File;
import java.io.Reader;
import java.nio.file.Path;

/**
 * @author anuj
 *
 */
public class GraphBuilder<Component> {
	
	private Reader fileReader;
	
	private Graph<Component> graph;

	public GraphBuilder(Path path) {
		
	}

	public GraphBuilder(File file) {
		
	}
	
	public GraphBuilder(String file) {
		
	}
	
	public void build() {
		
	}

	public Graph<Component> getGraph() {
		return graph;
	}

	public void setGraph(Graph<Component> graph) {
		this.graph = graph;
	}
	
	public Reader getFileReader() {
		return fileReader;
	}

	public void setFileReader(Reader fileReader) {
		this.fileReader = fileReader;
	}
}
