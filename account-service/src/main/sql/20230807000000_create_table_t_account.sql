
CREATE DATABASE d_account;

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

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `d_account.undo_log`
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
ALTER TABLE `d_account.undo_log` ADD INDEX `ix_log_created` (`log_created`);