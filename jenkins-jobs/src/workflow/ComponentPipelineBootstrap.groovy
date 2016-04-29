package workflow

// this build requires the parameters:
// - componentName

// load the workflow script
def flow
node{
  echo "bootstrap function begin"
  git url: 'https://https://github.com/timdelbruegger/playground-jenkins-pipeline.git'
  flow = load "jenkins-workflows/src/components/"+componentName+".groovy"
  echo "bootstrap function end"
}

// start the workflow
flow.build()

