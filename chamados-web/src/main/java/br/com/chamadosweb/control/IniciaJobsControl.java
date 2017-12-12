package br.com.chamadosweb.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.chamadosweb.service.JobAtualizaNrAtendimentosChamados;

/**
 * 
 * @author Renan Celso, em 13/09/2017
 *	
 */
@ManagedBean(name = "iniciaJobsControl")
@ApplicationScoped
public class IniciaJobsControl {
	
	@PostConstruct
	public void init() {	
		try {	
			
			SchedulerFactory schedFact = new StdSchedulerFactory();
			Scheduler sched = schedFact.getScheduler();	
			sched.start();
			
			JobDetail job = JobBuilder.newJob(JobAtualizaNrAtendimentosChamados.class).withIdentity("JobAtualizaNrAtendimentosChamados", "grupo1").build();			
			Trigger trigger = TriggerBuilder.newTrigger()
												.withIdentity("triggerJobAtualizaNrAtendimentosChamados", "grupo1")
												.withSchedule(CronScheduleBuilder.cronSchedule("0 0/20 * * * ?"))
												.build();
			
			sched.scheduleJob(job, trigger);			
			
		} catch (SchedulerException e) {
			if(e instanceof ObjectAlreadyExistsException){
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, "SchedulerException", e);
			} else {			
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, "SchedulerException", e);
			}
		}
	}
	
	public String getIniciarJobs() {		
		return "";
	}

}
