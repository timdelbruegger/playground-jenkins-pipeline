package components


// ATTENTION
// We have to load all used classes statically. Otherwise they will not be picked up during
// "load" command in the bootstrap code. See Util.groovy
// A description of the problem can be found at: http://bfischer.blogspot.de/2015/03/loading-workflow-scripts.html

def start(){
	echo "Started ExampleA workflow"
	node{
		echo "ExampleA reached first node"
		
		if(this.pipeline == null){
			throw new IllegalStateException("Needed utility class is not present!")
		}
//		pipeline.start(env.BUILD_NAME)
	}
	echo "ExampleA workflow finished"
}


def pipeline = load "jenkins-workflows/src/library/Pipeline.groovy"
this.getBinding().setVariable("pipeline", pipeline)

return this