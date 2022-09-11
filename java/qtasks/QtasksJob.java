package qtasks;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import clojure.lang.IFn;
import clojure.lang.Keyword;
import clojure.lang.IPersistentMap;
import clojure.lang.ISeq;
import clojure.java.api.Clojure;

public class QtasksJob implements Job {
    private final IFn f;
    private IPersistentMap scheduler;
    public QtasksJob(Object f, Object scheduler) {
        this.f = (IFn) f;
        this.scheduler = (IPersistentMap) scheduler;
    }

    public void execute(JobExecutionContext context) {
        JobDataMap m = context.getJobDetail().getJobDataMap();
        IFn list = Clojure.var("clojure.core", "list*");
        ISeq args = (ISeq) list.invoke(scheduler.assoc(Keyword.intern("qtasks", "execution-context"), context), m.get("arguments"));
        f.applyTo(args);
    }
}
