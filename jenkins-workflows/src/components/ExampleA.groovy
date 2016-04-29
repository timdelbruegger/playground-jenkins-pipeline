package components

import groovy.transform.Field


// ATTENTION
// We have to load all used classes statically. Otherwise they will not be picked up during
// "load" command in the bootstrap code. See Util.groovy
// A description of the problem can be found at: http://bfischer.blogspot.de/2015/03/loading-workflow-scripts.html

@Field
def pipeline = load "jenkins-workflows/src/library/Pipeline.groovy"

def start(){
	echo "Started ExampleA workflow"
	node{
		echo "ExampleA reached first node"
		pipeline.start(env.BUILD_NAME)
	}
	echo "ExampleA workflow finished"
}

return this