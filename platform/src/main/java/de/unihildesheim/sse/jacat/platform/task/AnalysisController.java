package de.unihildesheim.sse.jacat.platform.task;

import de.unihildesheim.sse.jacat.api.addon.task.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v0/tools/{slug}/analysis")
public class AnalysisController {

    private AnalysisCapabilities analysisCapabilities;
    private AnalysisTaskRepository analysisTaskRepository;
    private JobLauncher jobLauncher;
    private Job asyncAnalysisTask;

    public AnalysisController(AnalysisCapabilities analysisCapabilities,
                              AnalysisTaskRepository analysisTaskRepository,
                              JobLauncher jobLauncher,
                              @Qualifier("asyncAnalysisTask") Job asyncAnalysisTask) {
        this.analysisCapabilities = analysisCapabilities;
        this.analysisTaskRepository = analysisTaskRepository;
        this.jobLauncher = jobLauncher;
        this.asyncAnalysisTask = asyncAnalysisTask;
    }

    @PostMapping()
    public AnalysisTaskResult startAnalysis(@PathVariable("slug") String slug,
                                    @RequestParam("language") String language,
                                    @RequestBody Map<String, Object> request) {
        AbstractAnalysisCapability capability = this.analysisCapabilities.getCapability(slug, language);

        if (capability != null) {
            AnalysisRequest analysisRequest = new AnalysisRequest(language.toLowerCase(), request);

            AnalysisTask analysisTask = new AnalysisTask();
            analysisTask.setStatus(TaskJobStatus.STARTED);
            analysisTask.setRequest(analysisRequest.getRequest());

            if (capability instanceof SyncAnalysisTask) {
                SyncAnalysisTask syncCapability = (SyncAnalysisTask) capability;
                TaskResult result = syncCapability.startAnalysis(analysisRequest);
                analysisTask.setResponse(result.getResult());
                analysisTask.setStatus(TaskJobStatus.SUCCESSFUL);
            }

            analysisTask = this.analysisTaskRepository.save(analysisTask);
//            JobParameter parameter = new JobParameter(analysisTask.getId());
//            Map<String, JobParameter> parameterMap = new HashMap<>();
//            parameterMap.put("id", parameter);
//            JobParameters jobParameters = new JobParameters(parameterMap);
//            jobLauncher.run(asyncAnalysisTask, jobParameters);

            return new AnalysisTaskResult(analysisTask);

        }
        return null;
    }

}
