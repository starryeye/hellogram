package dev.practice.user.testconfig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@EnableR2dbcAuditing
@TestConfiguration
public class R2dbcAuditingTestConfig {
}
