package de.unihildesheim.sse.jacat.platform.task.batch;

import de.unihildesheim.sse.jacat.platform.task.AnalysisCapabilities;
import de.unihildesheim.sse.jacat.platform.task.AnalysisTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableBatchProcessing
public class BatchJobConfiguration {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final AnalysisCapabilities capabilities;

    public BatchJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MongoTemplate mongoTemplate, AnalysisCapabilities capabilities) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
        this.capabilities = capabilities;
    }

    @Bean
    public MongoItemReader<AnalysisTask> reader() {
        MongoItemReader<AnalysisTask> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setSort(new HashMap<>() {{
            put("_id", Sort.Direction.DESC);
        }});
        reader.setTargetType(AnalysisTask.class);
        reader.setQuery("{ status: 'STARTED' }");
        return reader;
    }

    @Bean
    public MongoItemWriter<AnalysisTask> writer() {
        MongoItemWriter<AnalysisTask> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("analysisTask");
        writer.setDelete(false);
        return writer;
    }

    @Bean
    public AsyncItemWriter<AnalysisTask> asyncWriter() {
        AsyncItemWriter<AnalysisTask> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(writer());
        return asyncItemWriter;
    }

    @Bean
    public AsyncItemProcessor<AnalysisTask, AnalysisTask> processor(AnalysisCapabilities capabilities) {
        AsyncItemProcessor<AnalysisTask, AnalysisTask> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(new AnalysisTaskItemProcessor(capabilities));
        asyncItemProcessor.setTaskExecutor(taskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(64);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(64);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        return executor;
    }

    @Bean(name = "asyncAnalysisTask")
    public Job startAsyncAnalysisTask(Step step1) {
        return jobBuilderFactory.get("startAsyncAnalysisTask")
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<AnalysisTask, Future<AnalysisTask>> chunk(1)
                .reader(reader())
                .processor(processor(this.capabilities))
                .writer(asyncWriter())
                .build();
    }

}
