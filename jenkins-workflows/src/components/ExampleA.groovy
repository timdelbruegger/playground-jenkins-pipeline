package components

import library.Pipeline

// ATTENTION
// We have to load all used classes statically. Otherwise they will not be picked up during
// "load" command in the bootstrap code. See Util.groovy
// A description of the problem can be found at: http://bfischer.blogspot.de/2015/03/loading-workflow-scripts.html

def pipeline = new Pipeline(env.JOB_NAME)

def start(){
	echo "Started ExampleA workflow"
	node{
		echo "ExampleA reached first node"
	}
	echo "ExampleA workflow finished"
}

return this