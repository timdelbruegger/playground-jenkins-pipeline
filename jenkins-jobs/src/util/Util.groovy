package util


class Util {

	static createComponentJob(String name){
		def componentJob = job(name)        
		
		componentJob.with {
            description('This Jobs builds component: ' + name)
        }
		
		return componentJob
	}
}
