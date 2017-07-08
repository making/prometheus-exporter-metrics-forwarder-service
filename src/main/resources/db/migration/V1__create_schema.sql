CREATE TABLE metric (
  `name`                       VARCHAR(255) NOT NULL,
  `timestamp`                  TIMESTAMP    NOT NULL,
  `type`                       VARCHAR(100) NOT NULL,
  `unit`                       VARCHAR(100) NOT NULL,
  `value`                      FLOAT        NOT NULL,
  `application_id`             VARCHAR(36)  NOT NULL,
  `application_instance_index` VARCHAR(5)   NOT NULL,
  `application_instance_id`    VARCHAR(36)  NOT NULL,
  `space_id`                   VARCHAR(36)  NOT NULL,
  `created_at`                 TIMESTAMP    NOT NULL DEFAULT now(),
  `updated_at`                 TIMESTAMP    NOT NULL DEFAULT now(),
  PRIMARY KEY (`name`, `application_id`, `application_instance_index`),
  INDEX (`space_id`)
)
  ENGINE InnoDB;

CREATE TABLE service_instance (
  `service_instance_id` VARCHAR(36) NOT NULL,
  `space_id`            VARCHAR(36) NOT NULL,
  `created_at`          TIMESTAMP   NOT NULL DEFAULT now(),
  `updated_at`          TIMESTAMP   NOT NULL DEFAULT now(),
  PRIMARY KEY (`service_instance_id`)
)
  ENGINE InnoDB;

CREATE TABLE token (
  `token`      VARCHAR(36) NOT NULL,
  `space_id`   VARCHAR(36) NOT NULL,
  `binding_id` VARCHAR(36) NOT NULL,
  `created_at` TIMESTAMP   NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP   NOT NULL DEFAULT now(),
  PRIMARY KEY (`token`),
  INDEX (`binding_id`)
)
  ENGINE InnoDB;


