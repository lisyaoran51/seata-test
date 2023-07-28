# seata-test
# seata-test
# seata-test

https://blog.csdn.net/qq_31671187/article/details/127865221
```
docker run --name seata-server-test -p 8091:8091 -p 7091:7091 seataio/seata-server:1.7.0
docker cp seata-server-test:/seata-server ./config/docker-data/seata

docker run -p 3310:3306 --platform linux/x86_64/v8 --name mysql5.7-test -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```
## seata server table
https://github.com/seata/seata/blob/develop/script/server/db/mysql.sql

```
CREATE DATABASE seata;
```

```
-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_status_gmt_modified` (`status` , `gmt_modified`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(128),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `status`         TINYINT      NOT NULL DEFAULT '0' COMMENT '0:locked ,1:rollbacking',
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_status` (`status`),
    KEY `idx_branch_id` (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `distributed_lock`
(
    `lock_key`       CHAR(20) NOT NULL,
    `lock_value`     VARCHAR(20) NOT NULL,
    `expire`         BIGINT,
    primary key (`lock_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('AsyncCommitting', ' ', 0);
INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('RetryCommitting', ' ', 0);
INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('RetryRollbacking', ' ', 0);
INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('TxTimeoutCheck', ' ', 0);
```

## each db table

### data
```
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY '123456' WITH GRANT OPTION;

CREATE DATABASE d_order;
CREATE DATABASE d_account;


CREATE TABLE d_order.t_order (
	id BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id BIGINT(11) DEFAULT NULL,
	money DECIMAL(11,0) DEFAULT NULL,
	status INT(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;	

CREATE TABLE d_account.t_account (
	id BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	balance DECIMAL(11,0) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;	


INSERT INTO d_account.t_account(`balance`) VALUES (200000);
INSERT INTO d_account.t_account(`balance`) VALUES (200000);
INSERT INTO d_account.t_account(`balance`) VALUES (200000);
INSERT INTO d_account.t_account(`balance`) VALUES (200000);
INSERT INTO d_account.t_account(`balance`) VALUES (200000);


```

### seata table

https://github.com/seata/seata/blob/develop/script/client/at/db/mysql.sql
```
-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';
ALTER TABLE `undo_log` ADD INDEX `ix_log_created` (`log_created`);
```



## config

https://github.com/seata/seata-samples

```
seata:
  enabled: true
  application-id: order-service
  tx-service-group: my_test_tx_group # 事务群组（可以每个应用独立取名，也可以使用相同的名字）
  client:
    rm-report-success-enable: true
    rm-table-meta-check-enable: false # 自动刷新缓存中的表结构（默认false）
    rm-report-retry-count: 5 # 一阶段结果上报TC重试次数（默认5）
    rm-async-commit-buffer-limit: 10000 # 异步提交缓存队列长度（默认10000）
    rm:
      lock:
        lock-retry-internal: 10 # 校验或占用全局锁重试间隔（默认10ms）
        lock-retry-times: 30 # 校验或占用全局锁重试次数（默认30）
        lock-retry-policy-branch-rollback-on-conflict: true # 分支事务与其它全局回滚事务冲突时锁策略（优先释放本地锁让回滚成功）
    tm-commit-retry-count: 3 # 一阶段全局提交结果上报TC重试次数（默认1次，建议大于1）
    tm-rollback-retry-count: 3 # 一阶段全局回滚结果上报TC重试次数（默认1次，建议大于1）
    undo:
      undo-data-validation: true # 二阶段回滚镜像校验（默认true开启）
      undo-log-serialization: jackson # undo序列化方式（默认jackson）
      undo-log-table: undo_log  # 自定义undo表名（默认undo_log）
    log:
      exceptionRate: 100 # 日志异常输出概率（默认100）
    support:
      spring:
        datasource-autoproxy: true
  service:
    vgroup-mapping:
      my_test_tx_group: default # TC 集群（必须与seata-server保持一致）
    enable-degrade: false # 降级开关
    disable-global-transaction: false # 禁用全局事务（默认false）
    grouplist:
      default: 127.0.0.1:8091
  transport:
    shutdown:
      wait: 3
    thread-factory:
      boss-thread-prefix: NettyBoss
      worker-thread-prefix: NettyServerNIOWorker
      server-executor-thread-prefix: NettyServerBizHandler
      share-boss-worker: false
      client-selector-thread-prefix: NettyClientSelector
      client-selector-thread-size: 1
      client-worker-thread-prefix: NettyClientWorkerThread
    type: TCP
    server: NIO
    heartbeat: true
    serialization: seata
    compressor: none
    enable-client-batch-send-request: true # 客户端事务消息请求是否批量合并发送（默认true）
  registry:
    file:
      name: file
  config:
    file:
      name: file
  store:
    # support: file 、 db 、 redis
    mode: db
    db:
      datasource: druid
      db-type: mysql
      driver-class-name: com.mysql.jdbc.Driver
      url: jdbc:mysql://db:3306/seata?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
      user: root
      password: 123456
      min-conn: 10
      max-conn: 100
      global-table: global_table
      branch-table: branch_table
      lock-table: lock_table
      distributed-lock-table: distributed_lock
      query-limit: 1000
      max-wait: 5000
```

https://developer.aliyun.com/article/742741
docker pull zhusaidong/nacos-server-m1:2.0.3
docker run --name nacos-standalone -e MODE=standalone -e JVM_XMS=512m -e JVM_XMX=512m -e JVM_XMN=256m -p 8848:8848 -d zhusaidong/nacos-server-m1:2.0.3