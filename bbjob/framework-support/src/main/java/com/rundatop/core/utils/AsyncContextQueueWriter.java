package com.rundatop.core.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;

import org.springframework.stereotype.Component;
@Component
public class AsyncContextQueueWriter{
	private static final Queue<AsyncContext> ASYNC_CONTEXT_QUEUE=new ConcurrentLinkedQueue<AsyncContext>();
	private static final BlockingQueue<String> MESSAGE_QUEUE=new LinkedBlockingQueue<String>();
	public void AddAsyncContext(AsyncContext ac){
		ASYNC_CONTEXT_QUEUE.add(ac);
	}
	public void RemoveAsyncContext(AsyncContext ac){
		ASYNC_CONTEXT_QUEUE.remove(ac);
	}
	public void sendMessage(String message) throws IOException{
		try {
			MESSAGE_QUEUE.put(message);
		} catch (InterruptedException e) {
			IOException t=new IOException();
			t.initCause(e);
			throw t;
		}
	}
	private Runnable notifierRunnable=new Runnable() {
		
		public void run() {
			boolean done=false;
			while(!done){
				String message=null;
				try {
					message=MESSAGE_QUEUE.take();
					for(AsyncContext ac:ASYNC_CONTEXT_QUEUE){
						try {
							PrintWriter acWriter=ac.getResponse().getWriter();
							acWriter.println(htmlEscape(message));
							 acWriter.flush();
							 acWriter=null;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	};
	private String htmlEscape(String message){
		String str="<script type='text/javascript'>\nwindow.parent.update("+message.replaceAll("\n", "")
				.replaceAll("\r", "")+");</script>";
		return str;
	}
	public AsyncContextQueueWriter(){
		Thread notifierThread=new Thread(notifierRunnable);
		notifierThread.start();
	}
	
	
	
	

}
