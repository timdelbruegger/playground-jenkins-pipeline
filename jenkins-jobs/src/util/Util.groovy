package util


class Util {

	// For a description of the pattern with DslFactory, see
	// https://github.com/jenkinsci/job-dsl-plugin/wiki/Job-DSL-Commands#dsl-factory

	/**
	 * Creates a Workflow Job that builds the given component
	 * @param dslFactory Should be <this> of a JobDsl groovy script
	 * @param name The name of the workflow Job to create
	 * @return the Job that was created
	 */
	static createComponentJob(dslFactory, String name){

		// TODO: check that such a file exists
		def workflowScriptFilename = 'jenkins-jobs/src/workflow/Component'+name + '.groovy'
		def workflowScript = generateWorkflowScript(name)
		//		def workflowScriptFile = new File(workflowScriptFilename)

		//		if( !workflowScriptFile.exists()){
		//			throw new IllegalArgumentException("Cannot create a Job with name " + name + " because there is no workflow script for it: " + workflowScriptFilename)
		//		}

		def componentJob = dslFactory.workflowJob(name){
			concurrentBuild()
			description("This Job builds and tests component: " + name)
			jdk("Java 8")
			definition {
				// TODO: trigger when the target SCM or the workflow SCM changes
				//				triggers {
				//					scm('# triggered by post-commit hook')
				//				}

				// see https://jenkinsci.github.io/job-dsl-plugin/#method/javaposse.jobdsl.dsl.DslFactory.workflowJob
				cps{
					script(workflowScript)
					sandbox()
				}
			}
		}

		return componentJob
	}

	/**
	 * This script will be part of the Job definition of every component build job. It loads the pipeline script that belongs to the component to build and launches the build process.
	 * <br><br>
	 * Some may ask: why do we need this? The problem is that the workflowScriptFromScm step cannot load multiple files. The "load" command, on the other hand, can initialize more than one file, if the other files are referenced in the static initialization of the file we load.
	 * @param componentName The name of the component to build. There must be a groovy script in "jenkins-workflows/src/components/" with this name
	 * @return the generated workflow / pipeline plugin script (groovy) as String.
	 */
	def static generateWorkflowScript(String componentName){
		return '''
			// load the workflow script
			def pipeline
			node{
			  echo "bootstrap function begins for component ${env.JOB_NAME}"
			  git url: 'https://github.com/timdelbruegger/playground-jenkins-pipeline.git'
			  pipeline = load "jenkins-workflows/src/components/${env.JOB_NAME}.groovy"
			  echo "bootstrap function end"
			}
			
			echo "starting the workflow"
			pipeline.start()
		'''
	}
}
