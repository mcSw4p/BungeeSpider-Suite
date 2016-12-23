package net.wynsolutions.bss.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
	public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

	
	public static void scheduleTask(final Runnable run, final int delay, final TimeUnit unit){
		new Thread(new Runnable(){
			@Override public void run() {
				executorService.schedule(run, delay, unit);
			}
		});
	}
}
