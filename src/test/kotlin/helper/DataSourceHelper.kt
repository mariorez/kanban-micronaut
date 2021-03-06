package helper

import com.zaxxer.hikari.HikariDataSource

class DataSourceHelper : HikariDataSource() {

    init {
        val url = "jdbc:h2:mem:UNIT_TEST;" +
                "MODE=PostgreSQL;" +
                "INIT=RUNSCRIPT FROM 'src/main/resources/db/migration/V001__Initial_setup.sql'\\;" +
                "RUNSCRIPT FROM 'classpath:db/migration/R__Data_fixture.sql'\\;"

        jdbcUrl = url
    }
}
