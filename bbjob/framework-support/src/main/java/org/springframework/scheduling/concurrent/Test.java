package org.springframework.scheduling.concurrent;

public class Test {
	public static void main(String args[]){
		InfoThreadPoolTaskExecutor pool=new InfoThreadPoolTaskExecutor();
		pool.setCorePoolSize(2);
		pool.setQueueCapacity(2000);
		pool.initialize();
		for(int i=0;i<1000;i++){
			Runnable r=new TestRunable();
			pool.execute(r);
		}
		for(;;){
			System.out.println("正在运行："+pool.getActiveCount());
			System.out.println("队伍中的："+pool.getQueueSize());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
class TestRunable implements Runnable{
	public TestRunable(){
		System.out.println("创建线程:"+Thread.currentThread().getName());
	}

	public void run() {
		for(int i=0;i<3;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}