package com.tiger.crm.repository.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MailExecutorService
{
	static ExecutorService 		executorService 	= null;
	static ThreadPoolExecutor 	threadPoolExecutor 	= null;

	public static void addRunable(Runnable runnable)
	{
		if(executorService == null)
		{
			executorService 	= Executors.newFixedThreadPool(Integer.valueOf(30));
			threadPoolExecutor	= (ThreadPoolExecutor) executorService;

		}
		executorService.submit(runnable);
	}

	public static int iActiveCountCount()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getActiveCount();
	}

	public static int iCorePoolSize()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getCorePoolSize();
	}

	public static int iMaximumPoolSize()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getMaximumPoolSize();
	}

	public static int iPoolSize()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getPoolSize();
	}

	public static long iTaskCount()
	{
		if(threadPoolExecutor == null) return 0;
		return threadPoolExecutor.getTaskCount();
	}
}
