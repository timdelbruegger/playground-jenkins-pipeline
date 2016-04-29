package util


class Util {

	static createComponentJob(dslFactory, String name){
		def componentJob = dslFactory.job(name){
			description('This Jobs builds component: ' + name)
		}     
		
		componentJob.with {
            
        }
		
		return componentJob
	}
}
