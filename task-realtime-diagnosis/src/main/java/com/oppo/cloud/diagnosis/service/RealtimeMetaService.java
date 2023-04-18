package com.oppo.cloud.diagnosis.service;

import com.oppo.cloud.common.domain.cluster.yarn.YarnApp;
import com.oppo.cloud.common.domain.flink.JobManagerConfigItem;
import com.oppo.cloud.model.RealtimeTaskApp;
import com.oppo.cloud.model.TaskInstance;

import java.util.List;

public interface RealtimeMetaService {
    /**
     * 存储实时作业元数据
     */
    void saveRealtimeMetaOnYarn(TaskInstance taskInstance, String applicationId);
    YarnApp requestYarnApp(String appId);
    void fillFlinkMetaWithFlinkConfigOnYarn(RealtimeTaskApp realtimeTaskApp, List<JobManagerConfigItem> configItems,String jobId);
    List<JobManagerConfigItem> reqFlinkConfig(YarnApp yarnApp);
    String getJobId(YarnApp yarnApp);
    String getJobId(String trackingUrl);
    List<String> getTmIds(YarnApp yarnApp);
}
