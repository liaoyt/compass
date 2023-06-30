#!/bin/bash

# dolphinscheduler or airflow or custom
export SCHEDULER="dolphinscheduler"
export SPRING_PROFILES_ACTIVE="hadoop,${SCHEDULER}"

# Scheduler MySQL
export SCHEDULER_MYSQL_ADDRESS="localhost:33066"
export SCHEDULER_MYSQL_DB="dolphinscheduler"
export SCHEDULER_DATASOURCE_URL="jdbc:mysql://${SCHEDULER_MYSQL_ADDRESS}/${SCHEDULER_MYSQL_DB}?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai"
export SCHEDULER_DATASOURCE_USERNAME=""
export SCHEDULER_DATASOURCE_PASSWORD=""

# Compass MySQL
export COMPASS_MYSQL_ADDRESS="localhost:33066"
export COMPASS_MYSQL_DB="compass"
export SPRING_DATASOURCE_URL="jdbc:mysql://${COMPASS_MYSQL_ADDRESS}/${COMPASS_MYSQL_DB}?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai"
export SPRING_DATASOURCE_USERNAME=""
export SPRING_DATASOURCE_PASSWORD=""

# Kafka
export SPRING_KAFKA_BOOTSTRAPSERVERS="host1:port,host2:port"

# Redis
export SPRING_REDIS_CLUSTER_NODES="localhost:6379"

# Zookeeper
export SPRING_ZOOKEEPER_NODES="localhost:2181"

# Elasticsearch
export SPRING_ELASTICSEARCH_NODES="localhost:19527"

# prom
export FLINK_PROMETHEUS_HOST="http://localhost:9090"
export FLINK_PROMETHEUS_TOKEN=""
export FLINK_PROMETHEUS_DATABASE=""

# task-canal
export CANAL_INSTANCE_MASTER_ADDRESS=${SCHEDULER_MYSQL_ADDRESS}
export CANAL_INSTANCE_DBUSERNAME=${SCHEDULER_DATASOURCE_USERNAME}
export CANAL_INSTANCE_DBPASSWORD=${SCHEDULER_DATASOURCE_PASSWORD}
if [ ${SCHEDULER} == "dolphinscheduler" ]; then
  export CANAL_INSTANCE_FILTER_REGEX="${SCHEDULER_MYSQL_DB}.t_ds_user,${SCHEDULER_MYSQL_DB}.t_ds_project,${SCHEDULER_MYSQL_DB}.t_ds_task_definition,${SCHEDULER_MYSQL_DB}.t_ds_task_instance,${SCHEDULER_MYSQL_DB}.t_ds_process_definition,${SCHEDULER_MYSQL_DB}.t_ds_process_instance,${SCHEDULER_MYSQL_DB}.t_ds_process_task_relation"
elif [ ${SCHEDULER} == "airflow" ]; then
  export CANAL_INSTANCE_FILTER_REGEX="${SCHEDULER_MYSQL_DB}.dag,${SCHEDULER_MYSQL_DB}.serialized_dag,${SCHEDULER_MYSQL_DB}.ab_user,${SCHEDULER_MYSQL_DB}.dag_run,${SCHEDULER_MYSQL_DB}.task_instance"
else
  export CANAL_INSTANCE_FILTER_REGEX=".*\\..*"
fi

export CANAL_ZKSERVERS=${SPRING_ZOOKEEPER_NODES}
export KAFKA_BOOTSTRAPSERVERS=${SPRING_KAFKA_BOOTSTRAPSERVERS}
export CANAL_MQ_TOPIC="mysqldata"
export CANAL_SERVERMODE="kafka"

# task-canal-adapter
export CANAL_ADAPTER_KAFKA_BOOTSTRAP_SERVERS=${SPRING_KAFKA_BOOTSTRAPSERVERS}
# source mysql
export CANAL_ADAPTER_SOURCE_MYSQL_URL=${SCHEDULER_DATASOURCE_URL}
export CANAL_ADAPTER_SOURCE_MYSQL_USERNAME=${SCHEDULER_DATASOURCE_USERNAME}
export CANAL_ADAPTER_SOURCE_MYSQL_PASSWORD=${SCHEDULER_DATASOURCE_PASSWORD}
# destination mysql
export CANAL_ADAPTER_DESTINATION_MYSQL_URL=${SPRING_DATASOURCE_URL}
export CANAL_ADAPTER_DESTINATION_MYSQL_USERNAME=${SPRING_DATASOURCE_USERNAME}
export CANAL_ADAPTER_DESTINATION_MYSQL_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

# task-syncer
# source mysql
export SPRING_DATASOURCE_DYNAMIC_DATASOURCE_SOURCE_URL=${SCHEDULER_DATASOURCE_URL}
export SPRING_DATASOURCE_DYNAMIC_DATASOURCE_SOURCE_USERNAME=${SCHEDULER_DATASOURCE_USERNAME}
export SPRING_DATASOURCE_DYNAMIC_DATASOURCE_SOURCE_PASSWORD=${SCHEDULER_DATASOURCE_PASSWORD}
# destination mysql
export SPRING_DATASOURCE_DYNAMIC_DATASOURCE_DIAGNOSE_URL=${SPRING_DATASOURCE_URL}
export SPRING_DATASOURCE_DYNAMIC_DATASOURCE_DIAGNOSE_USERNAME=${SPRING_DATASOURCE_USERNAME}
export SPRING_DATASOURCE_DYNAMIC_DATASOURCE_DIAGNOSE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

