package components


// ATTENTION
// We have to load all used classes statically. Otherwise they will not be picked up during
// "load" command in the bootstrap code. See Util.groovy
// A description of the problem can be found at: http://bfischer.blogspot.de/2015/03/loading-workflow-scripts.html

// load Util.groovy into variable "util"
loadLibraryClass("Util")

def start(){
	echo "Started ExampleA workflow"
	node{
		echo "ExampleA reached first node"
		
		if(this.util == null){
			throw new IllegalStateException("Needed utility class is not present!")
		}
		util.doSomething(env.BUILD_NAME)
	}
	echo "ExampleA workflow finished"
}


def loadLibraryClass(String name){
	def libraryPath = "jenkins-workflows/src/library/"
	def libraryClassFile = libraryPath+name+".groovy"
	def libraryObject = load libraryClassFile
	this.getBinding().setVariable(name.toLowerCase(), libraryObject)
}

return this