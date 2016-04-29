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
		def workflowScript = dslFactory.readFileFromWorkspace(workflowScriptFilename)
//		def workflowScriptFile = new File(workflowScriptFilename)
		
//		if( !workflowScriptFile.exists()){
//			throw new IllegalArgumentException("Cannot create a Job with name " + name + " because there is no workflow script for it: " + workflowScriptFilename)
//		}
		
		def componentJob = dslFactory.workflowJob(name){
			concurrentBuild()
			description("This Job builds and tests component: " + name)
			jdk("Java 8")
			definition {
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
}
